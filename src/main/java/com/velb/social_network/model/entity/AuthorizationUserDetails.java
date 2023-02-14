package com.velb.social_network.model.entity;

import com.velb.social_network.model.auxiliary.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("authorization_user_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationUserDetails {

    @PrimaryKeyColumn(name = "mail", type = PrimaryKeyType.PARTITIONED)
    private String mail;

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.CLUSTERED)
    private UUID id;

    @Column("password")
    private String encryptedPassword;

    @Column("role")
    private Role role;

    @Column("last_name")
    private String lastName;

    @Column("first_name")
    private String firstName;

}
