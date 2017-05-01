package com.stayrascal.api.util.autoconfig;

import com.stayrascal.api.util.jsonapi.HystrixJsonApiControllerAdvice;
import com.stayrascal.api.util.jsonapi.JsonApiControllerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;

@Configuration
public class SpringMvcAutoConfiguration {


    @Bean
    @ConditionalOnClass(name = "com.netflix.hystrix.exception.HystrixRuntimeException")
    public HystrixJsonApiControllerAdvice hystrixJsonApiControllerAdvice() {
        return new HystrixJsonApiControllerAdvice();
    }

    @Bean
    public JsonApiControllerAdvice jsonApiControllerAdvice() {
        return new JsonApiControllerAdvice();
    }


    @Bean
    @ConditionalOnMissingBean(MethodValidationPostProcessor.class)
    public MethodValidationPostProcessor methodValidationPostProcessor(Validator validator) {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator);
        return methodValidationPostProcessor;
    }


    @Bean
    public LocalValidatorFactoryBean validator() {
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        return localValidatorFactoryBean;
    }
}
