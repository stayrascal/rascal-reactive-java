package com.stayrascal.services;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(basePackages = {"com.stayrascal.services"})
@PropertySources(value = {
        @PropertySource("classpath:application.yml"),
        @PropertySource("classpath:application-test.yml")
})
@EnableAutoConfiguration
public class IntegrationTestConfiguration {
}
