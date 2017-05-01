package com.stayrascal.api.util.rxjava;

import com.stayrascal.api.util.web.ApiContext;
import com.stayrascal.api.util.web.ApiContextUtil;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import rx.functions.Action0;

import java.util.Map;

import static org.slf4j.MDC.getCopyOfContextMap;

public class ApiContextAction implements Action0 {
    private final Action0 action;
    private final ApiContext apiContext;
    private final Map<String, String> parentMdc;
    private final RequestAttributes requestAttributes;

    public ApiContextAction(Action0 action) {
        this.action = action;
        this.apiContext = ApiContextUtil.fetchContext();
        this.parentMdc = getCopyOfContextMap();
        requestAttributes = RequestContextHolder.getRequestAttributes();
    }

    @Override
    public void call() {
        Map<String, String> childMDC = getCopyOfContextMap();
        ApiContext originalContext = ApiContextUtil.fetchContext();
        RequestAttributes originalRequestAttributes = RequestContextHolder.getRequestAttributes();
        try {
            if (apiContext != null) {
                ApiContextUtil.setContext(apiContext);
            }
            if (parentMdc != null) {
                MDC.setContextMap(parentMdc);
            }
            RequestContextHolder.setRequestAttributes(requestAttributes);
            action.call();
        } finally {
            if (childMDC != null) {
                MDC.setContextMap(childMDC);
            }
            ApiContextUtil.setContext(originalContext);
            RequestContextHolder.setRequestAttributes(originalRequestAttributes);
        }
    }
}
