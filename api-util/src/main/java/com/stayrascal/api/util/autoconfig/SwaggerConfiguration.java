package com.stayrascal.api.util.autoconfig;

import com.stayrascal.api.util.swagger.SpringOperationNotesReader;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public static PropertyPlaceholderConfigurer swaggerProperties() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setOrder(Ordered.LOWEST_PRECEDENCE);
        configurer.setPlaceholderPrefix("@@{");
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

    @Bean
    public SpringOperationNotesReader springOperationNotesReader() {
        return new SpringOperationNotesReader();
    }
}
