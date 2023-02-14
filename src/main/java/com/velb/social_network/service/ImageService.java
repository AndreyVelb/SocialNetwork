package com.velb.social_network.service;

import com.velb.social_network.exception.ExceptionMessage;
import com.velb.social_network.exception.YandexException;
import com.velb.social_network.model.dto.yandex.YandexResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final RestTemplate restTemplate;

    @Value("${app.basket.yandex-disk.upload-host}")
    private String UPLOAD_IMAGE_URL;

    @Value("${app.basket.yandex-disk.host}")
    private String YANDEX_RESOURCES_URL;

    @Value("${app.basket.yandex-disk.download-link-host}")
    private String DOWNLOAD_LINK_URL;

    @Value("${app.basket.yandex-disk.o-auth-token}")
    private String O_AUTH_TOKEN;


    public String uploadPhotoOnYandexDisk(String imageName, InputStream content, UUID userId) throws IOException {
        HttpHeaders headers = getBasicHeaders();

        HttpEntity<Void> headersEntity = new HttpEntity<>(headers);

        YandexResponseDto uploadPermissionResponse = restTemplate
                .exchange(UPLOAD_IMAGE_URL + userId + "/" + imageName,
                        HttpMethod.GET,
                        headersEntity,
                        YandexResponseDto.class)
                .getBody();
        if (isYandexResponseDtoNotException(uploadPermissionResponse)) {
            HttpEntity<byte[]> uploadImageEntity = new HttpEntity<>(content.readAllBytes(), headers);
            try (content) {
                restTemplate.put(uploadPermissionResponse.getHref(), uploadImageEntity);
            }
        } else throw new YandexException(new ExceptionMessage().YANDEX_DISK_MESSAGE);

        YandexResponseDto uploadImageResponse = restTemplate.exchange(DOWNLOAD_LINK_URL + userId + "/" + imageName,
                        HttpMethod.GET,
                        headersEntity,
                        YandexResponseDto.class)
                .getBody();
        if (isYandexResponseDtoNotException(uploadImageResponse)) {
            return uploadImageResponse.getHref();
        } else throw new YandexException(new ExceptionMessage().YANDEX_DISK_MESSAGE);
    }

    public void deleteImage(UUID userId, String imageName) {
        HttpEntity<Void> headersEntity = new HttpEntity<>(getBasicHeaders());
        restTemplate.exchange(YANDEX_RESOURCES_URL + "?path=" + userId + "/" + imageName,
                HttpMethod.DELETE,
                headersEntity, Object.class);
    }

    //TODO посмотреть response Exception
    public void createDirectory(UUID userId) {
        HttpEntity<Void> entity = new HttpEntity<>(getBasicHeaders());

        restTemplate.put(YANDEX_RESOURCES_URL + "?path=" + userId, entity);
    }

    private HttpHeaders getBasicHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "OAuth " + O_AUTH_TOKEN);
        return headers;
    }

    private boolean isYandexResponseDtoNotException(YandexResponseDto responseDto) {
        return responseDto != null && responseDto.getError() != null;
    }


}
