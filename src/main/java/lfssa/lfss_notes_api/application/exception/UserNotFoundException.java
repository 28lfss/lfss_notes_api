package lfssa.lfss_notes_api.application.exception;

import java.util.UUID;

/**
 * Thrown when a user is required but not found (e.g. when creating a note for a non-existent user).
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID userId) {
        super("User not found: " + userId);
    }
}
