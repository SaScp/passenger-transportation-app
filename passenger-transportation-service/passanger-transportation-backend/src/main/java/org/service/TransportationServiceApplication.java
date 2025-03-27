package org.service;

import org.service.core.TransportationServiceCore;


import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregateImpl;
import org.service.output_port.jpa.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableAsync
@EnableCaching
@SpringBootApplication
public class TransportationServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(TransportationServiceApplication.class, args);
    }


    /**
     * Бин jpa агрегатора
     * @return TransportationServiceOutputPortAggregateImpl
     * **/
    @Profile({"prod", "dev"})
    @Bean("jpaAggregate")
    public TransportationServiceOutputPortAggregate transportationServiceJpaOutputPortAggregateImpl(List<TransportationServiceOutputPort> transportationServiceOutputPorts)  {

        Map<Class<? >, TransportationServiceOutputPort> transportationServiceOutputPortMap = new HashMap<>();

        for (var i : transportationServiceOutputPorts) {
            transportationServiceOutputPortMap.put(i.getOutputPortType(), i);
        }

        return new TransportationServiceOutputPortAggregateImpl(
                transportationServiceOutputPortMap
        );
    }

    @Bean
    public TransportationServiceCore transportationServiceCore(@Qualifier("jpaAggregate") TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregate) {
        return new TransportationServiceCore(transportationServiceOutputPortAggregate);
    }


}
