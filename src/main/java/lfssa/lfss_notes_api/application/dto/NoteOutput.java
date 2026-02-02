package lfssa.lfss_notes_api.application.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Output for note use cases (application layer). Presentation maps this to its own DTOs.
 */
public record NoteOutput(UUID id, UUID userId, String title, String content, Instant createdAt) {
}
