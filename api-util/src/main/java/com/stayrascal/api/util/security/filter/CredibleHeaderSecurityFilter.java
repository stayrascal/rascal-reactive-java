package com.stayrascal.api.util.security.filter;

import com.stayrascal.api.util.security.mapper.ApplicationCentricAuthoritiesMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class CredibleHeaderSecurityFilter extends GenericFilterBean {
    public static final Logger LOGGER = LoggerFactory.getLogger(CredibleHeaderSecurityFilter.class);
    private Map<String, String> authorityMappings;
    private String groupHeaderKey = "HTTPS_GROUPS";
    private String principalHeaderKey = "SSO_REMOTE_USER";
    private ApplicationCentricAuthoritiesMapper mapper;
    private boolean passThroughFilter;
    private boolean exceptionIfHeaderMissing;

    public CredibleHeaderSecurityFilter() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if (this.isPassThroughFilter() && this.isAuthenticationRequired()) {
            this.validateHeaders(httpRequest);
            String principal = httpRequest.getHeader(this.getPrincipalHeaderKey());
            List authorityGroups = this.extractGroupFromHeader(httpRequest);
            this.logDebugInfo(LOGGER, principal, httpRequest.getHeader(this.getGroupHeaderKey()));
            this.setAuthenticationIfPrincipalNotBlank(principal, authorityGroups);
        }
    }

    protected void logDebugInfo(Logger logger, String principal, String authorityGroups) {
        if (logger.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer("principal: ");
            sb.append(principal);
            sb.append(", groups: ");
            sb.append(isNotEmpty(authorityGroups) ? authorityGroups : "none");
            logger.debug(sb.toString());
        }
    }

    protected boolean isAuthenticationRequired() {
        return SecurityContextHolder.getContext().getAuthentication() == null || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    protected void validateHeaders(HttpServletRequest request) {
        boolean principalHeaderPresent = request.getHeader(this.getPrincipalHeaderKey()) != null;
        if (!principalHeaderPresent && this.isExceptionIfHeaderMissing()) {
            String exceptionMsg = String.format("%s header not found in request.", new Object[]{this.getPrincipalHeaderKey()});
            throw new PreAuthenticatedCredentialsNotFoundException(exceptionMsg);
        }

    }

    protected List extractGroupFromHeader(HttpServletRequest request) {
        List<GrantedAuthority> authorityGroups = null;
        String remoteGroups = request.getHeader(this.getGroupHeaderKey());
        if (this.getMapper() != null && org.springframework.util.StringUtils.hasText(remoteGroups) && this.getAuthorityMappings() != null) {
            authorityGroups = this.getMapper().mapAuthorities(remoteGroups, this.getAuthorityMappings());
        }
        return authorityGroups;
    }

    private void setAuthenticationIfPrincipalNotBlank(String principal, List<GrantedAuthority> authorityGroups) {
        if (StringUtils.isNoneBlank(principal)) {
            PreAuthenticatedAuthenticationToken auth = new PreAuthenticatedAuthenticationToken(principal, "n/a", authorityGroups);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    public Map<String, String> getAuthorityMappings() {
        return authorityMappings;
    }

    public void setAuthorityMappings(Map<String, String> authorityMappings) {
        this.authorityMappings = authorityMappings;
    }

    public String getGroupHeaderKey() {
        return groupHeaderKey;
    }

    public void setGroupHeaderKey(String groupHeaderKey) {
        this.groupHeaderKey = groupHeaderKey;
    }

    public String getPrincipalHeaderKey() {
        return principalHeaderKey;
    }

    public void setPrincipalHeaderKey(String principalHeaderKey) {
        this.principalHeaderKey = principalHeaderKey;
    }

    public ApplicationCentricAuthoritiesMapper getMapper() {
        return mapper;
    }

    public void setMapper(ApplicationCentricAuthoritiesMapper mapper) {
        this.mapper = mapper;
    }

    public boolean isPassThroughFilter() {
        return passThroughFilter;
    }

    public void setPassThroughFilter(boolean passThroughFilter) {
        this.passThroughFilter = passThroughFilter;
    }

    public boolean isExceptionIfHeaderMissing() {
        return exceptionIfHeaderMissing;
    }

    public void setExceptionIfHeaderMissing(boolean exceptionIfHeaderMissing) {
        this.exceptionIfHeaderMissing = exceptionIfHeaderMissing;
    }
}
