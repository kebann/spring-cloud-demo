package com.example.exception;

import java.util.Optional;

public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException(Integer sol, String camera) {
        super(("No picture found for sol=%s".formatted(sol) +
                Optional.ofNullable(camera).map(val -> "and camera=" + val).orElse("")));
    }
}