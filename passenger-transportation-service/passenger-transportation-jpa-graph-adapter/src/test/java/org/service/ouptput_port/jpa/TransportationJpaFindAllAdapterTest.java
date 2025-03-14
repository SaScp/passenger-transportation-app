package org.service.ouptput_port.jpa;

import org.junit.jupiter.api.Test;
import org.service.entity.PageEntity;

import org.service.entity.RoutesEntity;

import org.service.ouptput_port.LocalDateTimeConverter;
import org.service.output_port.jpa.TransportationJpaFindAllAdapter;
import org.service.output_port.model.Location;
import org.service.output_port.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransportationJpaFindAllAdapterTest {
    @Autowired
    private TransportationJpaFindAllAdapter  transportationJpaFindAllAdapter;

    @Autowired
    private TestEntityManager testEntityManager;

    private LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();


    private Route createAndPersistRoute(LocalDateTime departureTime) {

        Location cityA = testEntityManager.persistAndFlush(new Location("loc1" + UUID.randomUUID(), "CityA"));
        Location cityB = testEntityManager.persistAndFlush(new Location("loc2" + UUID.randomUUID(), "CityB"));
        Route route = new Route(
                cityA,
                cityB,
                departureTime,
                departureTime.plusHours(1),
                new ArrayList<>()
        );
        return testEntityManager.persistAndFlush(route);
    }

    @Test
    void testFindAllEmptyResult() {
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void testFindAllSingleResult() {
        LocalDateTime time = LocalDateTime.of(2025, 3, 1, 12, 0);
        createAndPersistRoute(time);
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(1, result.size());
        assertEquals(time, localDateTimeConverter.convertToEntityAttribute(result.get(0).departureTime()));
    }

    @Test
    void testPagination() {
        LocalDateTime baseTime = LocalDateTime.now();
        for (int i = 0; i < 5; i++) {
            createAndPersistRoute(baseTime.plusMinutes(i));
        }
        PageEntity pageEntity = new PageEntity(1, 2);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(2, result.size());
    }


    @Test
    void testSortingOrder() {
        LocalDateTime time1 = LocalDateTime.of(2025, 3, 1, 15, 0);
        LocalDateTime time2 = LocalDateTime.of(2025, 3, 1, 12, 0);
        createAndPersistRoute(time1);
        createAndPersistRoute(time2);
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(2, result.size());
        assertTrue(localDateTimeConverter.convertToEntityAttribute(result.get(0).departureTime()).isBefore(localDateTimeConverter.convertToEntityAttribute(result.get(1).departureTime()))
                || result.get(0).departureTime().equals(result.get(1).departureTime()));
    }

    @Test
    void testNegativePageNumberThrowsException() {
        PageEntity pageEntity = new PageEntity(-1, 10);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                transportationJpaFindAllAdapter.findAll(pageEntity));
        assertTrue(exception.getMessage().contains("Page index must not be less than zero"));
    }


    @Test
    void testNegativePageSizeThrowsException() {
        PageEntity pageEntity = new PageEntity(0, -5);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                transportationJpaFindAllAdapter.findAll(pageEntity));
        assertTrue(exception.getMessage().contains("Page size must not be less than one"));
    }

    @Test
    void testCacheableAnnotationPresent() throws NoSuchMethodException {
        Method method = TransportationJpaFindAllAdapter.class.getMethod("findAll", PageEntity.class);
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        assertNotNull(cacheable);
        assertEquals("#pageEntity.hashCode()", cacheable.key());
        assertArrayEquals(new String[] {"TransportationJpaFindAllAdapter::findAll"}, cacheable.value());
    }

    @Test
    void testCustomPageEntityValues() {
        LocalDateTime time = LocalDateTime.of(2025, 3, 1, 12, 0);
        createAndPersistRoute(time);
        createAndPersistRoute(time.plusMinutes(1));
        createAndPersistRoute(time.plusMinutes(2));
        PageEntity pageEntity = new PageEntity(0, 1);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(1, result.size());
    }

    @Test
    void testMultipleCallsReturnSameResult() {
        LocalDateTime time = LocalDateTime.of(2025, 3, 1, 12, 0);
        createAndPersistRoute(time);
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result1 = transportationJpaFindAllAdapter.findAll(pageEntity);
        List<RoutesEntity> result2 = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(result1, result2);
    }

    @Test
    void testResultTypeIsList() {
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertTrue(result instanceof List);
    }

    @Test
    void testBoundaryPageNumberTooHigh() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 3; i++) {
            createAndPersistRoute(now.plusMinutes(i));
        }
        PageEntity pageEntity = new PageEntity(5, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFullPageReturnsAll() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 4; i++) {
            createAndPersistRoute(now.plusMinutes(i));
        }
        PageEntity pageEntity = new PageEntity(0, 4);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(4, result.size());
    }


    @Test
    void testSortingByDepartureTimeField() {
        LocalDateTime t1 = LocalDateTime.of(2025, 3, 1, 14, 0);
        LocalDateTime t2 = LocalDateTime.of(2025, 3, 1, 10, 0);
        LocalDateTime t3 = LocalDateTime.of(2025, 3, 1, 18, 0);
        createAndPersistRoute(t1);
        createAndPersistRoute(t2);
        createAndPersistRoute(t3);
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        // Сортировка по LocalDateTime даст корректный хронологический порядок
        List<RoutesEntity> sorted = result.stream()
                .sorted(Comparator.comparing(t -> localDateTimeConverter.convertToEntityAttribute(t.departureTime() )))
                .toList();
        assertEquals(sorted, result);
    }

    @Test
    void testFindAllNonNullResult() {
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertNotNull(result);
    }

    @Test
    void testMappingIdentity() {
        LocalDateTime time = LocalDateTime.of(2025, 3, 1, 12, 0);
        Route persisted = createAndPersistRoute(time);
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertFalse(result.isEmpty());
        assertEquals(persisted.getDepartureTime(), localDateTimeConverter.convertToEntityAttribute(result.get(0).departureTime()));
    }

    @Test
    void testPagingFirstPage() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 4; i++) {
            createAndPersistRoute(now.plusMinutes(i));
        }
        PageEntity pageEntity = new PageEntity(0, 2);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(2, result.size());
    }

    @Test
    void testPagingSecondPage() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 5; i++) {
            createAndPersistRoute(now.plusMinutes(i));
        }
        PageEntity pageEntity = new PageEntity(1, 2);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(2, result.size());
    }

    @Test
    void testRepositorySortOrder() {
        LocalDateTime early = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime late = LocalDateTime.of(2025, 3, 1, 20, 0);
        createAndPersistRoute(late);
        createAndPersistRoute(early);
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertFalse(result.isEmpty());
        assertEquals(early, localDateTimeConverter.convertToEntityAttribute(result.get(0).departureTime()));
    }

    @Test
    void testRepositoryWithSameDepartureTime() {
        LocalDateTime sameTime = LocalDateTime.of(2025, 3, 1, 12, 0);
        createAndPersistRoute(sameTime);
        createAndPersistRoute(sameTime);
        createAndPersistRoute(sameTime);
        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(3, result.size());
    }

    @Test
    void testLargePageSize() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 3; i++) {
            createAndPersistRoute(now.plusMinutes(i));
        }
        PageEntity pageEntity = new PageEntity(0, 100);
        List<RoutesEntity> result = transportationJpaFindAllAdapter.findAll(pageEntity);
        assertEquals(3, result.size());
    }
}