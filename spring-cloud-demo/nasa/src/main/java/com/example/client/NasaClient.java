package com.example.client;


import com.example.dto.NasaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("nasa-proxy-service")
public interface NasaClient {

    @GetMapping("/mars-photos/api/v1/rovers/curiosity/photos")
    NasaResponse getCuriosityRoverPhotos(@RequestParam Integer sol, @RequestParam(required = false) String camera,
                                         @RequestParam(required = false) String apiKey);
}
