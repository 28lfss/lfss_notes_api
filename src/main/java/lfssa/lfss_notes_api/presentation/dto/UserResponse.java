package lfssa.lfss_notes_api.presentation.dto;

import java.util.UUID;

/**
 * API response for a user (presentation layer).
 */
public record UserResponse(UUID id, String name) {
}
