package lfssa.lfss_notes_api.presentation.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * API response for a note (presentation layer).
 */
public record NoteResponse(UUID id, UUID userId, String title, String content, Instant createdAt) {
}
