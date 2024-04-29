package com.mjc.school.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record NewsDtoResponse(
        Long id,
        @Pattern(regexp = "^.{5,30}$", message = "Title length must be from 5 to 30 characters")
        String title,
        @Pattern(regexp = "^.{5,255}$", message = "Content length must be from 5 to 255 characters")
        String content,
        LocalDateTime createDate,
        LocalDateTime lastUpdateDate,
        @NotNull
        Long authorId
) {
}
