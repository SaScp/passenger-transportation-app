import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.service.entity.ParamsEntity;
import org.service.entity.Result;
import org.service.exception.ProblemDetailsException;
import org.service.output_port.filter_handler.HandlerExecutor;
import org.service.output_port.filter_handler.SQLConstant;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HandlerExecutorTest {

    private HandlerExecutor handlerExecutor;

    @BeforeEach
    void setUp() {
        handlerExecutor = new HandlerExecutor();
    }

    @Test
    void execute_withValidParamsEntity_shouldReturnExpectedResult() {
        ParamsEntity entity = new ParamsEntity();
        entity.setRouteId("1");
        entity.setFrom("CityA");

        Result result = handlerExecutor.execute(entity);

        assertTrue(result.params().contains("CityA"));
        assertTrue(result.params().contains("1"));
        assertTrue(result.query().contains("t_routes.id = ?"));
        assertTrue(result.query().contains("departure_city = ?"));
    }

    @Test
    void execute_withEmptyParamsEntity_shouldReturnDefaultQuery() {
        ParamsEntity entity = new ParamsEntity();

        Result result = handlerExecutor.execute(entity);

        assertTrue(result.params().isEmpty());
        assertTrue(result.query().contains(SQLConstant.START_SELECT_BY_PARAMS_QUERY));
        assertTrue(result.query().contains(SQLConstant.END_SELECT_BY_PARAMS_QUERY));
    }

    @Test
    void execute_withNullParamsEntity_shouldThrowException() {
        assertThrows(ProblemDetailsException.class, () -> handlerExecutor.execute(null));
    }

    @Test
    void execute_withOnlyRouteId_shouldReturnCorrectQuery() {
        ParamsEntity entity = new ParamsEntity("10");

        Result result = handlerExecutor.execute(entity);

        assertEquals(1, result.params().size());
        assertEquals("10", result.params().get(0));
        assertTrue(result.query().contains("t_routes.id = ?"));
    }

    @Test
    void execute_withTimeParam_shouldReturnCorrectQuery() {
        ParamsEntity entity = new ParamsEntity();
        entity.setTime(LocalDateTime.of(2023, 5, 10, 12, 30));

        Result result = handlerExecutor.execute(entity);

        assertEquals(1, result.params().size());
        assertTrue(result.query().contains("departure_time >= ?"));
    }

    @Test
    void execute_withMultipleParams_shouldReturnCorrectQuery() {
        ParamsEntity entity = new ParamsEntity(LocalDateTime.of(2023, 6, 15, 10, 0), "express", "CityA", "CityB");

        Result result = handlerExecutor.execute(entity);

        assertEquals(4, result.params().size());
        assertTrue(result.query().contains("departure_time >= ?"));
        assertTrue(result.query().contains("type_name = ?"));
        assertTrue(result.query().contains("departure_city = ?"));
        assertTrue(result.query().contains("arrival_city = ?"));
    }

    @Test
    void execute_withUnknownParameter_shouldIgnoreIt() {
        ParamsEntity entity = new ParamsEntity();
        entity.setRouteId("20");
        entity.setType("UnknownType");

        Result result = handlerExecutor.execute(entity);

        assertEquals(2, result.params().size());
        assertTrue(result.query().contains("t_routes.id = ?"));
        assertTrue(result.query().contains("type_name = ?"));
    }

    @Test
    void execute_withNoParams_shouldReturnDefaultQuery() {
        ParamsEntity entity = new ParamsEntity();

        Result result = handlerExecutor.execute(entity);

        assertTrue(result.params().isEmpty());
        assertTrue(result.query().contains(SQLConstant.START_SELECT_BY_PARAMS_QUERY));
    }

    @Test
    void execute_withOnlyDestinationCity_shouldReturnCorrectQuery() {
        ParamsEntity entity = new ParamsEntity();
        entity.setTo("CityB");

        Result result = handlerExecutor.execute(entity);

        assertEquals(1, result.params().size());
        assertTrue(result.query().contains("arrival_city = ?"));
    }

    @Test
    void execute_withAllParams_shouldReturnCorrectQuery() {
        ParamsEntity entity = new ParamsEntity(LocalDateTime.of(2024, 7, 20, 15, 45), "regional", "CityX", "CityY");

        Result result = handlerExecutor.execute(entity);

        assertEquals(4, result.params().size());
        assertTrue(result.query().contains("departure_time >= ?"));
        assertTrue(result.query().contains("type_name = ?"));
        assertTrue(result.query().contains("departure_city = ?"));
        assertTrue(result.query().contains("arrival_city = ?"));
    }
}
