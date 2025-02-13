package org.service;

import org.service.core.TransportationServiceCore;
import org.service.output_port.CreateBookingTransportationServiceOutputPort;
import org.service.output_port.TransportationServiceOutputPort;
import org.service.output_port.TransportationServiceOutputPortAggregate;
import org.service.output_port.TransportationServiceOutputPortAggregateImpl;
import org.service.output_port.jdbc.TransportationJdbcCreateBookingMapper;
import org.service.output_port.jdbc.TransportationJdbcFindAllMapper;
import org.service.output_port.jdbc.TransportationJdbcFindByTimeAndTypeMapper;
import org.service.output_port.jdbc.TransportationJdbcRevokeBookingMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TransportationServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(TransportationServiceApplication.class, args);
    }


    @Bean
    public TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregateImpl(DataSource dataSource) {
        return new TransportationServiceOutputPortAggregateImpl(
                new TransportationJdbcCreateBookingMapper(dataSource),
                new TransportationJdbcRevokeBookingMapper(dataSource),
                new TransportationJdbcFindByTimeAndTypeMapper(dataSource),
                new TransportationJdbcFindAllMapper(dataSource)
        );
    }

    @Bean
    public TransportationServiceCore transportationServiceCore(TransportationServiceOutputPortAggregate transportationServiceOutputPortAggregate) {
        return new TransportationServiceCore(transportationServiceOutputPortAggregate);
    }
}
