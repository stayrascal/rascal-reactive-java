package com.stayrascal.api.util.web.security;

import com.stayrascal.api.util.autoconfig.ApiSecurityProperties;
import com.stayrascal.api.util.security.filter.CredibleHeaderSecurityFilter;
import com.stayrascal.api.util.security.mapper.DefaultApplicationCentricAuthoritiesMapper;
import com.stayrascal.api.util.web.ApiAuthenticationEntryPoint;
import com.stayrascal.api.util.security.mapper.ApplicationCentricAuthoritiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ConditionalOnWebApplication
public class ApiWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(ApiWebSecurityConfiguration.class);

    @Autowired
    protected ApiSecurityProperties apiSecurityProperties;

    @Autowired
    protected SecurityProperties securityProperties;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("\n\n====The security path is {}", apiSecurityProperties.getPaths()[0]);
        LOGGER.info("\n\n====The security role is {}", apiSecurityProperties.getRoles()[0]);
        LOGGER.info("\n\n====The security realm is {}", apiSecurityProperties.getRealm());
        SpringBootWebSecurityConfiguration.configureHeaders(http.headers(), securityProperties.getHeaders());
        http.sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(apiSecurityProperties.getPaths()).hasAnyRole(apiSecurityProperties.getRoles())
                .and()
                .httpBasic()
                .authenticationEntryPoint(new ApiAuthenticationEntryPoint(apiSecurityProperties.getRealm()))
                .and()
                .addFilterAfter(preAuthenticationFilter(), BasicAuthenticationFilter.class)
                .csrf()
                .disable();
    }

    private Filter preAuthenticationFilter() {
        CredibleHeaderSecurityFilter filter = new CredibleHeaderSecurityFilter();
        filter.setPrincipalHeaderKey(apiSecurityProperties.getCredibleHeader().getPrincipal());
        filter.setGroupHeaderKey(apiSecurityProperties.getCredibleHeader().getGroupKey());
        filter.setPassThroughFilter(false);
        filter.setMapper(authorityMapper());
        filter.setExceptionIfHeaderMissing(false);
        filter.setAuthorityMappings(apiSecurityProperties.getRoleMapping());
        return filter;
    }

    private ApplicationCentricAuthoritiesMapper authorityMapper() {
        DefaultApplicationCentricAuthoritiesMapper mapper = new DefaultApplicationCentricAuthoritiesMapper();
        mapper.setGroupSeparator(apiSecurityProperties.getCredibleHeader().getGroupSeparator());
        return mapper;
    }
}
