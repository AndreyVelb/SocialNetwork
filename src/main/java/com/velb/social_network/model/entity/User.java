package com.velb.social_network.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @PrimaryKeyColumn(name = "last_name", type = PrimaryKeyType.CLUSTERED)
    private String lastName;

    @PrimaryKeyColumn(name = "first_name", type = PrimaryKeyType.CLUSTERED)
    private String firstName;

    @Column("user_name")
    private String userName;

    @Column("birth_date")
    private LocalDate birthDate;

    @Column("main_photo_link")
    private String mainPhotoLink;

    @Column("description")
    private String description;

}
