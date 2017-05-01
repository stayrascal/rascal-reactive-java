package com.stayrascal.api.util.autoconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stayrascal.api.util.web.CorrelationIdClientHttpRequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class CommonRestTemplateConfiguration {

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    @ConditionalOnMissingBean(MappingJackson2HttpMessageConverter.class)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper mapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
        converter.setDefaultCharset(null);
        return converter;
    }

    @Bean
    public ClientHttpRequestInterceptor correlationIdClientHttpRequestInterceptor() {
        return new CorrelationIdClientHttpRequestInterceptor();
    }
}
