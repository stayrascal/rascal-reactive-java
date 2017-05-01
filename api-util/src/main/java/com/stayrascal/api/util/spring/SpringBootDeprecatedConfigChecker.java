package com.stayrascal.api.util.spring;

import com.google.common.collect.ImmutableList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;

public class SpringBootDeprecatedConfigChecker implements EnvironmentPostProcessor {

    private List<String> deprecatedConfigProperties;

    public SpringBootDeprecatedConfigChecker() {
        this.deprecatedConfigProperties = ImmutableList.<String>builder()
                .add("spring.datasource.maxActive").build();
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment);
        deprecatedConfigProperties.stream()
                .filter(relaxedPropertyResolver::containsProperty)
                .findAny()
                .ifPresent(prop -> {
                    throw new IllegalStateException("SpringBootDeprecatedConfigChecker - Deprecated config property found" + prop);
                });


    }
}
