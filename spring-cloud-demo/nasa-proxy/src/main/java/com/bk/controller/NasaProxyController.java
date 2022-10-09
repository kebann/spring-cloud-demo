package com.bk.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping
public class NasaProxyController {

    @Value("${nasa.api.url}")
    private String nasaBaseUrl;

    @GetMapping("/mars-photos/api/v1/rovers/curiosity/photos")
    public JsonNode getMarsCuriosityPhotos(@RequestParam Integer sol, @RequestParam Optional<String> camera,
                                           @RequestParam Optional<String> apiKey) {
        var restTemplate = new RestTemplate();
        var nasaUrl = buildNasaUrl(sol, camera, apiKey);

        return restTemplate.getForObject(nasaUrl, JsonNode.class);
    }

    private String buildNasaUrl(Integer sol, Optional<String> camera,
                                Optional<String> apiKey) {
        return UriComponentsBuilder.fromHttpUrl(nasaBaseUrl)
                .path("mars-photos/api/v1/rovers/curiosity/photos")
                .queryParam("api_key", apiKey.orElse("DEMO_KEY"))
                .queryParam("sol", sol)
                .queryParamIfPresent("camera", camera)
                .toUriString();
    }
}
