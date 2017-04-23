package com.stayrascal.services.util.security;

import com.stayrascal.services.util.security.auth.FilterRoleGrantedAuthoritiesMapper;
import com.stayrascal.services.util.security.condition.ConditionalOnExternalRegion;
import com.stayrascal.services.util.security.condition.ConditionalOnInternalRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

@Configuration
@ConditionalOnProperty(prefix = "api.security", name = "enabled", matchIfMissing = true)
@ConditionalOnMissingBean({AuthenticationManager.class})
public class ApiSecurityAutoConfiguration {

    @Configuration
    @ConditionalOnInternalRegion
    public static class LocalUserSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
        public static final int STRINGTH = 6;

        @Autowired
        private ApiSecurityProperties apiSecurityProperties;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(new InMemoryUserDetailsManager(userProperties())).passwordEncoder(new BCryptPasswordEncoder(STRINGTH));
        }

        private Properties userProperties() {
            return apiSecurityProperties.getUsers();
        }
    }

    @Configuration
    @ConditionalOnExternalRegion
    public static class ActiveDirectoryUsersSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private ApiSecurityProperties apiSecurityProperties;

        @Bean
        public AuthenticationProvider ldapAuthenticationProvider() {
            ApiSecurityProperties.LDAP ldap = apiSecurityProperties.getLdap();
            ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(ldap.getDomain(), ldap.getUrl());
            provider.setAuthoritiesMapper(grantedAuthoritiesMapper());
            return provider;
        }

        @Bean
        public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
            return new FilterRoleGrantedAuthoritiesMapper(apiSecurityProperties.getRoleMapping());
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(ldapAuthenticationProvider());
        }

        public void setApiSecurityProperties(ApiSecurityProperties apiSecurityProperties) {
            this.apiSecurityProperties = apiSecurityProperties;
        }
    }


    @Bean
    public ApiSecurityProperties apiSecurityProperties() {
        return new ApiSecurityProperties();
    }
}
