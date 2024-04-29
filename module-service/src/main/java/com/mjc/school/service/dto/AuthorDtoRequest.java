package com.mjc.school.service.dto;

import jakarta.validation.constraints.Pattern;

public record AuthorDtoRequest(
        Long id,
        @Pattern(regexp = "^.{3,15}$", message = "Name length must be from 3 to 15 characters")
        String name
) {
}
