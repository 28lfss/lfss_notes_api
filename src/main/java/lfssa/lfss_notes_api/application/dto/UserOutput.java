package lfssa.lfss_notes_api.application.dto;

import java.util.UUID;

/**
 * Output of the CreateUser use case (application layer). Presentation maps this to its own DTOs.
 */
public record UserOutput(UUID id, String name) {
}
