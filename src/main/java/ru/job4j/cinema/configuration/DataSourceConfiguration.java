package ru.job4j.cinema.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties") // указывает откуда читаем настройки
public class DataSourceConfiguration {

    @Bean
    public DataSource loadPool(@Value("${jdbc.driver}") String driver, // читаем настройки указанные в ${}
                               @Value("${jdbc.url}") String url,
                               @Value("${jdbc.username}") String username,
                               @Value("${jdbc.password}") String password) {
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(driver);
        pool.setUrl(url);
        pool.setUsername(username);
        pool.setPassword(password);
        return pool;
    }

}