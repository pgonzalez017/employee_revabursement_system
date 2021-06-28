package com.revature.ers.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.function.Function;

public enum Authority {
    EMPLOYEE("EMPLOYEE"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN"),
    LOCKED("LOCKED");

    public final String authorityName;

    Authority(String authorityName){ this.authorityName = authorityName; }

    public static Function<Authority, SimpleGrantedAuthority> toAuthorities() {
        return authority -> new SimpleGrantedAuthority(authority.authorityName);
    }
}
