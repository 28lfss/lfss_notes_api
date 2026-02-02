package lfssa.lfss_notes_api.domain.entity;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain entity: a note belonging to a user (1:N). No framework dependencies.
 */
public class Note {

    private final UUID id;
    private final UUID userId;
    private final String title;
    private final String content;
    private final Instant createdAt;

    public Note(UUID id, UUID userId, String title, String content, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
