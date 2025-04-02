package org.service.ouptput_port;

import jakarta.persistence.EntityManager;

import org.service.output_port.jpa.TransportationJpaCreateBookingAdapter;
import org.service.output_port.repository.BookingRepository;
import org.service.output_port.repository.RouteRepository;
import org.service.output_port.util.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@ComponentScans(
        value = {@ComponentScan("org.service.output_port.jpa"),
                @ComponentScan("org.service.output_port.repository"),
                @ComponentScan("org.service.output_port.util"),
                @ComponentScan("org.service.ouptput_port.jpa")})
@SpringBootConfiguration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "org.service.output_port.repository")
@EntityScan(basePackages = "org.service.output_port.model")
public class JpaTestConfiguration {



    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
    }

    @Bean
    public CacheManager cacheManager() {
        return new SimpleCacheManager();
    }


    @Bean
    public TransportationJpaCreateBookingAdapter transportationJpaCreateBookingAdapter(EntityManager testEntityManager, BookingRepository bookingRepository) {
        return new TransportationJpaCreateBookingAdapter(bookingRepository, testEntityManager, new CacheUtils(cacheManager()));
    }
}
