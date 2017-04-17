package com.stayrascal.services;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(basePackages = {"com.stayrascal.services"})
@PropertySources(value = {
        @PropertySource("classpath:applicaiton.yml"),
        @PropertySource("classpath:test-applicaiton.yml")
})
@EnableAutoConfiguration
public class IntegrationTestConfiguration {
}
