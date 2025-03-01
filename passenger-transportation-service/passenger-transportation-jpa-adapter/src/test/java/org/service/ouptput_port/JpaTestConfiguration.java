package org.service.ouptput_port;

import org.service.ouptput_port.jpa.TransportationJpaFindAllAdapter;
import org.service.ouptput_port.jpa.TransportationJpaRevokeBookingAdapter;
import org.service.ouptput_port.repository.RouteRepository;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;


@ComponentScans(value = {@ComponentScan("org.service.ouptput_port.jpa"), @ComponentScan("org.service.ouptput_port.repository")})
@SpringBootConfiguration
@EnableAutoConfiguration
public class JpaTestConfiguration {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:schema.sql").addScript("classpath:data.sql").build();
    }

    @Bean
    public CacheManager cacheManager() {
        return new SimpleCacheManager();
    }
}
