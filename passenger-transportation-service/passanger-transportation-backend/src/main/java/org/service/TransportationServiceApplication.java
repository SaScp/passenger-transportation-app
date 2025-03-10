package org.service;

import org.service.core.TransportationServiceCore;


import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregateImpl;
import org.service.output_purt.jpa.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import java.sql.SQLException;

@EnableAsync
@EnableCaching
@SpringBootApplication
public class TransportationServiceApplication {

    @Value("${cache.lru.size}")
    public Integer cacheSize;

    public static void main(String[] args) {
        SpringApplication.run(TransportationServiceApplication.class, args);
    }


    /**
     * @param dataSource
     * Бин jdbc агрегатора
     * @return TransportationServiceOutputPortAggregateImpl
     * **/
  /*  @Profile("dev")
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
    }*/

    /**
     * Бин jpa агрегатора
     * @return TransportationServiceOutputPortAggregateImpl
     * **/
    @Profile({"prod", "dev"})
    @Bean("jpaAggregate")
    public TransportationServiceOutputPortAggregate transportationServiceJpaOutputPortAggregateImpl(
            TransportationJpaFindByPhoneAdapter findByPhoneAdapter,
            TransportationJpaFindByParamAdapter findByParamAdapter,
            TransportationJpaCreateBookingAdapter createBookingAdapter,
            TransportationJpaRevokeBookingAdapter revokeBookingAdapter,
            TransportationJpaFindAllRouteStepAdapter findAllRouteStepAdapter,
            TransportationJpaFindTypesAdapter findTypesAdapter,
            TransportationJpaFindAllAdapter findAllAdapter,
            TransportationFindByRouteStepsIdsAdapter findByRouteStepsIdsAdapter,
            TransportationFindByDepartureCityIdAdapter findByDepartureCityIdAdapter
    ) throws SQLException {
        return new TransportationServiceOutputPortAggregateImpl(
                createBookingAdapter,
                revokeBookingAdapter,
                findByParamAdapter,
                findAllAdapter,
                findByPhoneAdapter,
                findTypesAdapter,
                findAllRouteStepAdapter,
                findByRouteStepsIdsAdapter,
                findByDepartureCityIdAdapter
        );
    }



    @Bean
    public TransportationServiceCore transportationServiceCore(@Qualifier("jpaAggregate") TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregate) {
        return new TransportationServiceCore(transportationServiceOutputPortAggregate);
    }

/*    @Bean("booking_jdbc_cache")
    public JdbcLruIdCache<String, List<BookingEntity>> bookingjdbcCache() {
        return new JdbcLruIdCache<>(cacheSize);
    }

    @Bean("route_jdbc_cache")
    public JdbcLruIdCache<Result, List<RoutesEntity>> routeJdbcCache() {
        return new JdbcLruIdCache<>(cacheSize);
    }*/
}
