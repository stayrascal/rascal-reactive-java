package com.stayrascal.api.util.hystrix;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "api.hystrix")
public class HystrixProperties {

    private Map<String, String> defaults = new HashMap<>();
    private RequestFilter requestFilter = new RequestFilter();

    public Map<String, String> getDefaults() {
        return defaults;
    }

    public RequestFilter getRequestFilter() {
        return requestFilter;
    }

    public void setRequestFilter(RequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }

    public static class RequestFilter {
        private boolean enabled = false;
        private List<String> patterns = new ArrayList<>();

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getPatterns() {
            return patterns;
        }

        public void setPatterns(List<String> patterns) {
            this.patterns = patterns;
        }
    }
}
