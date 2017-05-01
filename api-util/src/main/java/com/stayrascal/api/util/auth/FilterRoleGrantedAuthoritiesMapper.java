package com.stayrascal.api.util.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class FilterRoleGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterRoleGrantedAuthoritiesMapper.class);

    private Map<String, String> roles;

    public FilterRoleGrantedAuthoritiesMapper(Map<String, String> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        if (doNotMapAuthorities()) {
            LOGGER.warn("Role mappings haven't specified in roles.yml. All riles will get generated tokens");
            return authorities;
        }

        /*return authorities.stream()
                .filter(authority -> {
                    LOGGER.debug("Authority key: {}", key);
                    return roles.containsKey(authority.getAuthority());
                })
                .map(authority -> {
                    LOGGER.info(format("Mapping Authority key %s to Role %s", key, roles.get(key)));
                    return new SimpleGrantedAuthority(authority.getAuthority());
                })
                .collect(Collectors.toList());*/
        List<GrantedAuthority> mapped = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            String key = authority.getAuthority();
            LOGGER.debug("Authority key: {}", key);
            if (roles.containsKey(key)) {
                LOGGER.info(format("Mapping Authority key %s to Role %s", key, roles.get(key)));
                mapped.add(new SimpleGrantedAuthority(roles.get(key)));
            }
        }
        return mapped;
    }

    private boolean doNotMapAuthorities() {
        return roles == null || roles.size() < 1;
    }
}
