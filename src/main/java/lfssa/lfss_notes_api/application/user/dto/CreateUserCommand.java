package lfssa.lfss_notes_api.application.user.dto;

public record CreateUserCommand(
    String name,
    String email,
    String password
) {}
