package com.stayrascal.services.util.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@ConfigurationProperties(prefix = "api.security")
public class ApiSecurityProperties {
    private static final String DEFAULT_REALM = "Stay Rascal API";
    private String[] paths = new String[]{"/**"};
    private String[] roles = new String[]{"API_SERVICE", "API_USER", "API_ADMIN"};
    private String realm = DEFAULT_REALM;
    private Map<String, String> roleMapping = new HashMap<>();
    private Properties users = new Properties();
    private CredibleHeader credibleHeader = new CredibleHeader();
    private LDAP ldap = new LDAP();

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public Map<String, String> getRoleMapping() {
        return roleMapping;
    }

    public void setRoleMapping(Map<String, String> roleMapping) {
        this.roleMapping = roleMapping;
    }

    public Properties getUsers() {
        return users;
    }

    public void setUsers(Properties users) {
        this.users = users;
    }

    public CredibleHeader getCredibleHeader() {
        return credibleHeader;
    }

    public void setCredibleHeader(CredibleHeader credibleHeader) {
        this.credibleHeader = credibleHeader;
    }

    public LDAP getLdap() {
        return ldap;
    }

    public void setLdap(LDAP ldap) {
        this.ldap = ldap;
    }

    public static class LDAP {
        private String url;
        private String domain;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }

    public static class CredibleHeader {
        private String principal = "SSO_REMOTE_USER";
        private String groupKey = "HTTPS_groups";
        private String groupSeparator = ":";

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getGroupKey() {
            return groupKey;
        }

        public void setGroupKey(String groupKey) {
            this.groupKey = groupKey;
        }

        public String getGroupSeparator() {
            return groupSeparator;
        }

        public void setGroupSeparator(String groupSeparator) {
            this.groupSeparator = groupSeparator;
        }
    }
}
