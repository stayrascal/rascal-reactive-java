package com.stayrascal.api.util.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springframework.beans.factory.InitializingBean;

public class HystrixPluginRegistrationBean implements InitializingBean {
    private HystrixCommandExecutionHook hystrixCommandExecutionHook;
    private HystrixPropertiesStrategy hystrixPropertiesStrategy;
    private HystrixMetricsPublisher hystrixMetricsPublisher;
    private HystrixConcurrencyStrategy hystrixConcurrencyStrategy;
    private HystrixEventNotifier hystrixEventNotifier;


    @Override
    public void afterPropertiesSet() throws Exception {
        HystrixPlugins hystrixPlugins = HystrixPlugins.getInstance();
        registerPropertiesStrategy(hystrixPlugins);
        registerMetricsPublisher(hystrixPlugins);
        registerConcurrencyStrategy(hystrixPlugins);
        registerCommandExecutionHook(hystrixPlugins);
        registerEventNotifier(hystrixPlugins);

    }

    private void registerPropertiesStrategy(HystrixPlugins hystrixPlugins) {
        if (hystrixPropertiesStrategy != null) {
            hystrixPlugins.registerPropertiesStrategy(hystrixPropertiesStrategy);
        }
    }

    private void registerMetricsPublisher(HystrixPlugins hystrixPlugins) {
        if (hystrixMetricsPublisher != null) {
            hystrixPlugins.registerMetricsPublisher(hystrixMetricsPublisher);
        }
    }

    private void registerConcurrencyStrategy(HystrixPlugins hystrixPlugins) {
        if (hystrixConcurrencyStrategy != null) {
            hystrixPlugins.registerConcurrencyStrategy(hystrixConcurrencyStrategy);
        }
    }

    private void registerCommandExecutionHook(HystrixPlugins hystrixPlugins) {
        if (hystrixCommandExecutionHook != null) {
            hystrixPlugins.registerCommandExecutionHook(hystrixCommandExecutionHook);
        }
    }

    private void registerEventNotifier(HystrixPlugins hystrixPlugins) {
        if (hystrixEventNotifier != null) {
            hystrixPlugins.registerEventNotifier(hystrixEventNotifier);
        }
    }

    public void setHystrixCommandExecutionHook(HystrixCommandExecutionHook hystrixCommandExecutionHook) {
        this.hystrixCommandExecutionHook = hystrixCommandExecutionHook;
    }

    public void setHystrixPropertiesStrategy(HystrixPropertiesStrategy hystrixPropertiesStrategy) {
        this.hystrixPropertiesStrategy = hystrixPropertiesStrategy;
    }

    public void setHystrixMetricsPublisher(HystrixMetricsPublisher hystrixMetricsPublisher) {
        this.hystrixMetricsPublisher = hystrixMetricsPublisher;
    }

    public void setHystrixConcurrencyStrategy(HystrixConcurrencyStrategy hystrixConcurrencyStrategy) {
        this.hystrixConcurrencyStrategy = hystrixConcurrencyStrategy;
    }

    public void setHystrixEventNotifier(HystrixEventNotifier hystrixEventNotifier) {
        this.hystrixEventNotifier = hystrixEventNotifier;
    }
}
