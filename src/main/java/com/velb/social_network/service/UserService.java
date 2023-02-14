package com.velb.social_network.service;

import com.velb.social_network.exception.ExceptionMessage;
import com.velb.social_network.exception.PhotoNotFoundException;
import com.velb.social_network.exception.UserAlreadyRegisteredException;
import com.velb.social_network.exception.UserNotFoundException;
import com.velb.social_network.model.auxiliary.Role;
import com.velb.social_network.model.dto.user.UserRegistrationDto;
import com.velb.social_network.model.entity.AuthorizationUserDetails;
import com.velb.social_network.model.entity.Photo;
import com.velb.social_network.model.entity.User;
import com.velb.social_network.repository.AuthorizationUserDetailsRepository;
import com.velb.social_network.repository.PhotoRepository;
import com.velb.social_network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ImageService imageService;
    private final AuthorizationUserDetailsRepository authorizationUserDetailsRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final PasswordEncoder passwordEncoder;


    public String create(UserRegistrationDto registrationDto, MultipartFile photo) throws IOException {
        if (authorizationUserDetailsRepository.findByMail(registrationDto.getMail()).isPresent()) {
            throw new UserAlreadyRegisteredException(
                    new ExceptionMessage().USER_ALREADY_REGISTERED_MESSAGE + registrationDto.getMail());
        }

        UUID userId = authorizationUserDetailsRepository.insert(
                        AuthorizationUserDetails.builder()
                                .mail(registrationDto.getMail())
                                .id(UUID.randomUUID())
                                .encryptedPassword(passwordEncoder.encode(registrationDto.getRowPassword()))
                                .role(Role.USER)
                                .lastName(registrationDto.getLastName())
                                .firstName(registrationDto.getFirstName())
                                .build())
                .getId();

        imageService.createDirectory(userId);

        String photoLink = imageService.uploadPhotoOnYandexDisk(photo.getOriginalFilename(),
                photo.getInputStream(),
                userId);

        photoRepository.insert(Photo.builder()
                .userId(userId)
                .photoId(UUID.randomUUID())
                .link(photoLink)
                .date(LocalDate.now())
                .imageName(photo.getOriginalFilename())
                .build());

        return userRepository.insert(
                        User.builder()
                                .id(userId)
                                .lastName(registrationDto.getLastName())
                                .firstName(registrationDto.getFirstName())
                                .userName(registrationDto.getUserName())
                                .mainPhotoLink(photoLink)
                                .birthDate(registrationDto.getBirthDate())
                                .description(registrationDto.getDescription())
                                .build())
                .getUserName();
    }

    public List<Photo> getPhotos(UUID userId) {
        return photoRepository.findAllPhotoByUserId(userId);
    }

    public User getUserInfo(UUID userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                new ExceptionMessage().OPENING_PAGE_PROBLEM_MESSAGE));
    }

    public Photo getPhoto(UUID userId, UUID photoId) {
        return photoRepository.findByUserIdAndPhotoId(userId, photoId)
                .orElseThrow(() ->
                        new PhotoNotFoundException(
                                new ExceptionMessage().PHOTO_NOT_FOUND_MESSAGE));
    }

    public UUID addPhoto(UUID userId, MultipartFile image) throws IOException {
        String uniqueImageFileName = image.getOriginalFilename() + LocalTime.now().toString();
        String photoLink = imageService.uploadPhotoOnYandexDisk(uniqueImageFileName, image.getInputStream(), userId);

        return photoRepository.insert(
                        Photo.builder()
                                .userId(userId)
                                .photoId(UUID.randomUUID())
                                .link(photoLink)
                                .date(LocalDate.now())
                                .imageName(uniqueImageFileName)
                                .build())
                .getPhotoId();
    }

    public void deletePhoto(UUID userId, UUID photoId) {
        Photo photo = photoRepository.findByUserIdAndPhotoId(userId, photoId)
                .orElseThrow(() -> new PhotoNotFoundException(
                        new ExceptionMessage().PHOTO_NOT_FOUND_MESSAGE));

        imageService.deleteImage(userId, photo.getImageName());

        photoRepository.deleteByUserIdAndPhotoId(userId, photoId);
    }

}
