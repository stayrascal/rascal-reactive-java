package com.stayrascal.api.util.hystrix;

import com.stayrascal.api.util.web.ApiContextUtil;
import com.stayrascal.api.util.web.ApiContext;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

import static org.slf4j.MDC.getCopyOfContextMap;

public class ApiContextCallable<T> implements Callable<T> {
    private final Callable<T> callable;
    private final ApiContext apiContext;
    private final Map parentMdc;

    public ApiContextCallable(Callable<T> callable) {
        this.callable = callable;
        this.apiContext = ApiContextUtil.fetchContext();
        this.parentMdc = getCopyOfContextMap();
    }

    @Override
    public T call() throws Exception {
        Map<String, String> childMdc = getCopyOfContextMap();
        ApiContext originalContext = ApiContextUtil.fetchContext();
        try {
            ApiContextUtil.setContext(apiContext);
            if (parentMdc != null) {
                MDC.setContextMap(parentMdc);
            }
            return callable.call();
        } finally {
            if (childMdc != null) {
                MDC.setContextMap(childMdc);
            }
            ApiContextUtil.setContext(originalContext);
        }
    }
}
