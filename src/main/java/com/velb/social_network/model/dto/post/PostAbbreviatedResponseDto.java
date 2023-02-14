package com.velb.social_network.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostAbbreviatedResponseDto {

    private UUID userId;

    private UUID postId;

    private String content;

}
