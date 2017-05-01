package com.stayrascal.api.util.autoconfig;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.stayrascal.api.util.hystrix.ApiContextHystrixConcurrencyStrategy;
import com.stayrascal.api.util.hystrix.HystrixPluginRegistrationBean;
import com.stayrascal.api.util.hystrix.HystrixProperties;
import com.stayrascal.api.util.hystrix.HystrixRequestContextFilter;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static java.lang.String.valueOf;

@Configuration
@ConditionalOnClass(HystrixPlugins.class)
@AutoConfigureAfter(ApiAutoConfigure.class)
public class HystrixApiAutoConfiguration {
    static final int DEFAULT_TIMEOUT = 30000;
    static final int DEFAULT_ROLLING_STATS_WINDOWS_SIZE = 60000;
    public static final String HYSTRIX_DEFAULT_PROPERTY_PREFIX = "hystrix.command.default";

    @Autowired
    private HystrixProperties hystrixProperties;

    @Autowired
    private ApiProperties apiProperties;

    @Bean
    @ConditionalOnProperty(prefix = "api.hystrix.request.filter", name = "enabled")
    public FilterRegistrationBean hystrixRequestFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new HystrixRequestContextFilter());
        filterRegistrationBean.setUrlPatterns(hystrixProperties.getRequestFilter().getPatterns());
        return filterRegistrationBean;
    }

    @Bean
    public HystrixPluginRegistrationBean hystrixPluginRegistrationBean() {
        HystrixPluginRegistrationBean hystrixPluginRegistrationBean = new HystrixPluginRegistrationBean();
        hystrixPluginRegistrationBean.setHystrixConcurrencyStrategy(new ApiContextHystrixConcurrencyStrategy());
        return hystrixPluginRegistrationBean;
    }

    @Bean
    public HystrixProperties hystrixProperties() {
        HystrixProperties hystrixProperties = new HystrixProperties();
        hystrixProperties.getDefaults().put("execution.isolation.thread.timeoutInMilliseconds", valueOf(DEFAULT_TIMEOUT));
        hystrixProperties.getDefaults().put("metrics.rollingStats.timeInMilliseconds", valueOf(DEFAULT_ROLLING_STATS_WINDOWS_SIZE));
        return hystrixProperties;
    }

    @PostConstruct
    public void setDefaultHystrixProperties() {
        AbstractConfiguration configInstance = ConfigurationManager.getConfigInstance();
        hystrixProperties.getDefaults().entrySet().forEach(entry -> configInstance.setProperty(String.format(HYSTRIX_DEFAULT_PROPERTY_PREFIX + "%s", entry.getKey()), entry.getValue()));
    }

    @PostConstruct
    public void init() {
        if (hystrixProperties.getRequestFilter().getPatterns().isEmpty()) {
            hystrixProperties.getRequestFilter().getPatterns().addAll(apiProperties.getFilter().getPatterns());
        }
    }
}
