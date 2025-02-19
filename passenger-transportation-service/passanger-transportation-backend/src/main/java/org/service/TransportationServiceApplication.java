package org.service;

import org.service.core.TransportationServiceCore;
import org.service.entity.BookingEntity;
import org.service.exception.ProblemDetailsException;
import org.service.ouptput_port.jpa.*;
import org.service.output_port.LruIdCache;
import org.service.output_port.TransportationServiceOutputPortAggregate;
import org.service.output_port.TransportationServiceOutputPortAggregateImpl;
import org.service.output_port.jdbc.adapter.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TransportationServiceApplication {

    @Value("${cache.lru.size}")
    public Integer cacheSize;

    public static void main(String[] args) {
        SpringApplication.run(TransportationServiceApplication.class, args);
    }


    @Bean("jdbcAggregate")
    public TransportationServiceOutputPortAggregate transportationServiceJdbcOutputPortAggregateImpl(DataSource dataSource) throws SQLException {
        return new TransportationServiceOutputPortAggregateImpl(
                new TransportationJdbcCreateBookingAdapter(dataSource, lruIdCache()),
                new TransportationJdbcRevokeBookingAdapter(dataSource, lruIdCache()),
                new TransportationJdbcFindByParamsAdapter(dataSource, new LruIdCache<>(cacheSize)),
                new TransportationJdbcFindAllAdapter(dataSource),
                new TransportationJdbcFindByPhoneAdapter(dataSource, lruIdCache())
        );
    }

    @Bean("jpaAggregate")
    public TransportationServiceOutputPortAggregate transportationServiceJpaOutputPortAggregateImpl(
            TransportationJpaFindByPhoneAdapter findByPhoneAdapter,
            TransportationJpaFindByParamAdapter findByParamAdapter,
            TransportationJpaCreateBookingAdapter createBookingAdapter,
            TransportationJpaRevokeBookingAdapter revokeBookingAdapter,
            TransportationJpaFindAllAdapter findAllAdapter
    ) throws SQLException {
        return new TransportationServiceOutputPortAggregateImpl(
                createBookingAdapter,
                revokeBookingAdapter,
                findByParamAdapter,
                findAllAdapter,
                findByPhoneAdapter
        );
    }

    @Bean
    public TransportationServiceCore transportationServiceCore(@Qualifier("jpaAggregate") TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregate) {
        return new TransportationServiceCore(transportationServiceOutputPortAggregate);
    }

    @Bean
    public Map<Class<? extends ProblemDetailsException>, ProblemDetailsException> exceptionMap(List<? extends ProblemDetailsException> exceptions) {
        return new HashMap<>();
    }

    @Bean
    public LruIdCache<String, List<BookingEntity>> lruIdCache() {
        return new LruIdCache<>(cacheSize);
    }
}
