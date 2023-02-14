package com.velb.social_network.model.dto.yandex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YandexResponseDto {

    private String href;

    private HttpMethod method;

    private boolean templated;

    private String message;

    private String description;

    private String error;

}
