package com.stayrascal.api.util.autoconfig;

import com.stayrascal.api.util.rxjava.ApiContextAction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import rx.plugins.RxJavaHooks;

@Configuration
@ConditionalOnClass(RxJavaHooks.class)
public class RxJavaAutoConfiguration {

    public void setupRxJava() {
        RxJavaHooks.setOnScheduleAction(action -> new ApiContextAction(action));
    }
}
