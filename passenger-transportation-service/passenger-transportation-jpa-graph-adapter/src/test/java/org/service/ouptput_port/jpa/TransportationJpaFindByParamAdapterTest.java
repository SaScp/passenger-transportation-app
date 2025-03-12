package org.service.ouptput_port.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.service.entity.PageEntity;
import org.service.entity.ParamsEntity;
import org.service.entity.RoutesEntity;
import org.service.output_port.jpa.TransportationJpaFindByParamAdapter;
import org.service.output_port.model.LocalDateTimeConverter;
import org.service.output_port.model.Location;
import org.service.output_port.model.Route;
import org.service.output_port.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class TransportationJpaFindByParamAdapterTest {
    @Autowired
    private TransportationJpaFindByParamAdapter adapter;

    @Autowired
    private TestEntityManager testEntityManager;

    private LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();



    @BeforeEach
    void setUp() {
        String dbTimeStr = "2023-06-01 07:00:00";
        LocalDateTime departureTime = localDateTimeConverter.convertToEntityAttribute(dbTimeStr);

        createAndPersistRoute(departureTime);
    }

    private Route createAndPersistRoute(LocalDateTime departureTime) {
        Location cityA = testEntityManager.persistAndFlush(new Location("loc1" + UUID.randomUUID(), "CityA"));
        Location cityB = testEntityManager.persistAndFlush(new Location("loc2" + UUID.randomUUID(), "CityB"));
        Route route = new Route(
                UUID.randomUUID().toString(),
                cityA,
                cityB,
                departureTime,
                departureTime.plusHours(1),
                new ArrayList<>()
        );
        return testEntityManager.persistAndFlush(route);
    }

    @Test
    void testFindBy_withFormattedTime_shouldReturnFilteredRoutes() {

        String dbTimeStr = "2023-06-01 07:00:00";
        LocalDateTime parsedTime = localDateTimeConverter.convertToEntityAttribute(dbTimeStr);
        ParamsEntity paramsEntity = new ParamsEntity();
        paramsEntity.setTime(parsedTime);

        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = adapter.findBy(paramsEntity, pageEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dbTimeStr, result.get(0).departureTime());
    }

    @Test
    void testFindBy_withMultipleRoutes_shouldReturnFilteredRoutes() {

        String dbTimeStr1 = "2023-06-01 07:00:00";
        String dbTimeStr2 = "2023-06-01 10:00:00";
        LocalDateTime parsedTime1 = localDateTimeConverter.convertToEntityAttribute(dbTimeStr1);
        LocalDateTime parsedTime2 = localDateTimeConverter.convertToEntityAttribute(dbTimeStr2);

        createAndPersistRoute(parsedTime1);
        createAndPersistRoute(parsedTime2);

        ParamsEntity paramsEntity = new ParamsEntity();
        paramsEntity.setTime(parsedTime1);


        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = adapter.findBy(paramsEntity, pageEntity);


        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(dbTimeStr1, result.get(0).departureTime());
    }

    @Test
    void testFindBy_withLocationFilter_shouldReturnFilteredRoutes() {
        String dbTimeStr = "2023-06-01 07:00:00";
        LocalDateTime parsedTime = localDateTimeConverter.convertToEntityAttribute(dbTimeStr);
        ParamsEntity paramsEntity = new ParamsEntity();
        paramsEntity.setTime(parsedTime);
        paramsEntity.setFrom("CityA");
        paramsEntity.setTo("CityB");


        PageEntity pageEntity = new PageEntity(0, 10);
        createAndPersistRoute(parsedTime);
        List<RoutesEntity> result = adapter.findBy(paramsEntity, pageEntity);



        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dbTimeStr, result.get(0).departureTime());
        assertEquals("CityA", result.get(0).departureCity().label());
        assertEquals("CityB", result.get(0).arrivalCity().label());
    }

    @Test
    void testFindBy_withNoRoutesFound_shouldReturnEmptyList() {

        String dbTimeStr = "2023-06-01 07:00:00";
        LocalDateTime parsedTime = localDateTimeConverter.convertToEntityAttribute(dbTimeStr);
        ParamsEntity paramsEntity = new ParamsEntity();
        paramsEntity.setTime(parsedTime);
        paramsEntity.setFrom("NonExistingCity");

        PageEntity pageEntity = new PageEntity(0, 10);
        List<RoutesEntity> result = adapter.findBy(paramsEntity, pageEntity);


        assertNotNull(result);

    }
}
