package com.velb.social_network.controller;

import com.velb.social_network.model.entity.Photo;
import com.velb.social_network.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${http.url.version}")
@RequiredArgsConstructor
public class PhotoController {

    private final UserService userService;


    @GetMapping(value = "/{userId}/photos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authentication.principal.id == #userId")
    public List<Photo> getSelfPhotos(@PathVariable UUID userId) {
        return userService.getPhotos(userId);
    }

    @GetMapping(value = "{userId}/photos/{photoId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authentication.principal.id == #userId")
    public Photo getSelfPhoto(@PathVariable UUID userId, @PathVariable UUID photoId) {
        return userService.getPhoto(userId, photoId);
    }

    @PostMapping(value = "/{userId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.principal.id == #userId")
    public ResponseEntity<Void> addPhoto(@PathVariable("userId") UUID userId,
                                         @RequestPart("photo") MultipartFile image) throws IOException {
        UUID photoId = userService.addPhoto(userId, image);

        URI location = URI.create("/api/v1/" + userId + "/photos/" + photoId );
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{userId}/photos/{photoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("authentication.principal.id == #userId")
    public void deletePhoto(@PathVariable UUID userId, @PathVariable UUID photoId) {
        userService.deletePhoto(userId, photoId);
    }

    @GetMapping(value = "users/{userId}/photos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Photo> getPhotos(@PathVariable UUID userId) {
        return userService.getPhotos(userId);
    }

    @GetMapping(value = "users/{userId}/photos/{photoId}")
    @ResponseStatus(HttpStatus.OK)
    public Photo getPhoto(@PathVariable UUID userId, @PathVariable UUID photoId) {
        return userService.getPhoto(userId, photoId);
    }

}
