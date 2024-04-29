package com.mjc.school.service.dto;

import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record AuthorDtoResponse(
        Long id,
        @Pattern(regexp = "^.{3,15}$", message = "Name length must be from 3 to 15 characters")
        String name,
        LocalDateTime createDate,
        LocalDateTime lastUpdateDate
) {
}
