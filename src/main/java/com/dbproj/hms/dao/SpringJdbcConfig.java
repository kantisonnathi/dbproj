/*
package com.dbproj.hms.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.dbproj.hms")
public class SpringJdbcConfig {
    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.driver");
        dataSource.setUrl("jdbc:mysql://localhost/dbproj");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return dataSource;
    }
}
*/
