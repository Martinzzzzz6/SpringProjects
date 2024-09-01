package com.personal.project.pgotosClone.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {
    private static final HikariConfig config = new HikariConfig();

    private static final HikariDataSource db;

    static {
        config.setJdbcUrl("jdbc:h2:file:/Users/martinmenchev/development/JavaWork/springbootDB;AUTO_SERVER=TRUE");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(20000);
        config.setMaxLifetime(1800000);
        config.setPoolName("HikariPool");
        db = new HikariDataSource(config);
    }

    @Bean
    public static Connection getConnection() throws SQLException {
        return db.getConnection();
    }
}
