package org.service;

import org.service.core.BookingTransportationServiceCore;
import org.service.core.GraphTransportationServiceCore;
import org.service.core.RouteTransportationServiceCore;


import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregate;
import org.service.output_port.aggregate.TransportationServiceOutputPortAggregateImpl;
import org.service.output_port.cache.CacheTransportationServiceOutputPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAsync
@EnableCaching
@EnableScheduling
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
    public TransportationServiceOutputPortAggregate transportationServiceJpaOutputPortAggregateImpl(List<CacheTransportationServiceOutputPort> transportationServiceOutputPorts)  {

        Map<Class<? >, TransportationServiceOutputPort> transportationServiceOutputPortMap = new HashMap<>();

        for (var i : transportationServiceOutputPorts) {
            transportationServiceOutputPortMap.put(i.getOutputPortType(), i);
        }

        return new TransportationServiceOutputPortAggregateImpl(
                transportationServiceOutputPortMap
        );
    }

    @Bean
    public GraphTransportationServiceCore graphTransportationServiceCore(@Qualifier("jpaAggregate") TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregate) {
        return new GraphTransportationServiceCore(transportationServiceOutputPortAggregate, taskExecutorService());
    }

    @Bean
    public RouteTransportationServiceCore routeTransportationServiceCore(@Qualifier("jpaAggregate") TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregate) {
        return new RouteTransportationServiceCore(transportationServiceOutputPortAggregate, taskExecutorService());
    }

    @Bean
    public BookingTransportationServiceCore bookingTransportationServiceCore(@Qualifier("jpaAggregate") TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregate) {
        return new BookingTransportationServiceCore(transportationServiceOutputPortAggregate, taskExecutorService());
    }


    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setVirtualThreads(true);
        executor.setMaxPoolSize(800);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setThreadNamePrefix("AsyncThread::");
        executor.initialize();
        return executor;
    }

    @Bean(name = "taskExecutorService")
    public ExecutorService taskExecutorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
