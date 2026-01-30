package lfssa.lfss_notes_api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * API request body for creating a user (presentation layer).
 */
public record CreateUserRequest(
        @NotBlank(message = "name is required")
        @Size(max = 32)
        String name,

        @NotBlank(message = "password is required")
        String password
) {
}
