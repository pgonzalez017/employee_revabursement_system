package com.revature.ers.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private int userId;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;

    @ColumnDefault("true")
    private Boolean isActive = true;

    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault("1")
    @JoinColumn(name = "authority_id", referencedColumnName = "authority_id")
    @NotNull
    private Authority authority = Authority.EMPLOYEE;

    @CreationTimestamp
    private Timestamp registrationTime;

    public User(int userId, String username, String email, String firstName,
                String lastName, boolean active, Authority authority, Timestamp registrationTime) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = active;
        this.authority = authority;
        this.registrationTime = registrationTime;
    }

    public User(String username, String password, String email, String firstName, String lastName, Authority authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(Authority.toAuthorities().apply(authority));
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonLocked();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
