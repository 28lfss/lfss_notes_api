package lfssa.lfss_notes_api.application.dto;

/**
 * Input for the CreateUser use case (application layer DTO).
 */
public record CreateUserCommand(String name, String password) {
}
