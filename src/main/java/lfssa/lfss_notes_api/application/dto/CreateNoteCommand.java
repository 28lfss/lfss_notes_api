package lfssa.lfss_notes_api.application.dto;

import java.util.UUID;

/**
 * Input for the CreateNote use case (application layer).
 */
public record CreateNoteCommand(UUID userId, String title, String content) {
}
