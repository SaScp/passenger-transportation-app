package org.service;

import org.service.core.TransportationServiceCore;
import org.service.entity.BookingEntity;
import org.service.entity.Result;
import org.service.entity.RoutesEntity;
import org.service.entity.TypeEntity;
import org.service.ouptput_port.jpa.*;
import org.service.output_port.JdbcLruIdCache;
import org.service.output_port.TransportationServiceOutputPortAggregate;
import org.service.output_port.TransportationServiceOutputPortAggregateImpl;
import org.service.output_port.jdbc.adapter.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@EnableAsync
@EnableCaching
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
                new TransportationJdbcCreateBookingAdapter(dataSource, bookingjdbcCache()),
                new TransportationJdbcRevokeBookingAdapter(dataSource, bookingjdbcCache()),
                new TransportationJdbcFindByParamsAdapter(dataSource, routeJdbcCache()),
                new TransportationJdbcFindAllAdapter(dataSource),
                new TransportationJdbcFindByPhoneAdapter(dataSource, bookingjdbcCache()),
                new TransportationJdbcFindTypesAdapter(dataSource)
        );
    }

    @Bean("jpaAggregate")
    public TransportationServiceOutputPortAggregate transportationServiceJpaOutputPortAggregateImpl(
            TransportationJpaFindByPhoneAdapter findByPhoneAdapter,
            TransportationJpaFindByParamAdapter findByParamAdapter,
            TransportationJpaCreateBookingAdapter createBookingAdapter,
            TransportationJpaRevokeBookingAdapter revokeBookingAdapter,
            TransportationJpaFindAllAdapter findAllAdapter,
            TransportationJpaFindTypesAdapter findTypesAdapter
    ) throws SQLException {
        return new TransportationServiceOutputPortAggregateImpl(
                createBookingAdapter,
                revokeBookingAdapter,
                findByParamAdapter,
                findAllAdapter,
                findByPhoneAdapter,
                findTypesAdapter
        );
    }

    @Bean
    public TransportationServiceCore transportationServiceCore(@Qualifier("jpaAggregate") TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregate) {
        return new TransportationServiceCore(transportationServiceOutputPortAggregate);
    }

    @Bean("booking_jdbc_cache")
    public JdbcLruIdCache<String, List<BookingEntity>> bookingjdbcCache() {
        return new JdbcLruIdCache<>(cacheSize);
    }

    @Bean("route_jdbc_cache")
    public JdbcLruIdCache<Result, List<RoutesEntity>> routeJdbcCache() {
        return new JdbcLruIdCache<>(cacheSize);
    }
}
