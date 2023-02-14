package com.velb.social_network.model.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;
import java.util.UUID;

@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final String lastName;
    private final String firstName;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             UUID id, String lastName, String firstName) {
        super(username, password, authorities);
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

}
