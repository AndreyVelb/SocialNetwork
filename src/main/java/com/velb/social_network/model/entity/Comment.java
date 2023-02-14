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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Table("comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @PrimaryKeyColumn(name = "post_id", type = PrimaryKeyType.PARTITIONED)
    private UUID postId;

    @PrimaryKeyColumn(name = "author_id", type = PrimaryKeyType.CLUSTERED)
    private UUID authorId;

    @PrimaryKeyColumn(name = "comment_id", type = PrimaryKeyType.CLUSTERED)
    private UUID commentId;

    @Column("content")
    private String content;

    @Column("author_full_name")
    private String authorFullName;

    @Column("time")
    private LocalTime creatingTime;

    @Column("date")
    private LocalDate creatingDate;

}
