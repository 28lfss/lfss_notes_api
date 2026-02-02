package lfssa.lfss_notes_api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * API request body for creating a note (presentation layer).
 */
public record CreateNoteRequest(
        @NotBlank(message = "title is required")
        @Size(max = 255)
        String title,

        String content
) {
}
