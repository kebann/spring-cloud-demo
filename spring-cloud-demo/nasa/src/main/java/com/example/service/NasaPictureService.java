package com.example.service;

import com.example.client.NasaClient;
import com.example.dto.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class NasaPictureService {

    private final RestTemplate restTemplate;
    private final NasaClient nasaClient;
    @Value("${nasa.api.key}")
    private String nasaApiKey;

    @Cacheable("largestPicture")
    public Optional<byte[]> findLargestPicture(Integer sol, String camera) {
        var nasaResponse = nasaClient.getCuriosityRoverPhotos(sol, camera, nasaApiKey);

        return nasaResponse
                .photos()
                .parallelStream()
                .map(this::enrichPhotoWithSize)
                .max(comparing(Photo::getSize))
                .map(photo -> getPictureContent(photo.getPhotoUrl()));
    }

    private byte[] getPictureContent(String pictureUri) {
        return restTemplate.getForObject(pictureUri, byte[].class);
    }

    private Photo enrichPhotoWithSize(Photo photo) {
        var headers = restTemplate.headForHeaders(photo.getPhotoUrl());
        return photo.setSize(headers.getContentLength());
    }
}
