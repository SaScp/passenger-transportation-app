package org.service;

import org.service.output_port.jdbc.TransportationJdbcAdapter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TransportationServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(TransportationServiceApplication.class, args);
    }


    @Bean
    public Map<Class<? extends TransportationJdbcAdapter>, ? extends TransportationJdbcAdapter> tansportationMap(List<TransportationJdbcAdapter> transportationJdbcAdapters) {
        Map<Class<? extends TransportationJdbcAdapter>, TransportationJdbcAdapter> resultMap = new HashMap<>();
        for (var i : transportationJdbcAdapters) {
            resultMap.put(i.getClass(), i);
        }
        return resultMap;
    }
}
