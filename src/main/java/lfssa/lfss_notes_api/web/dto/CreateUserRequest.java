package lfssa.lfss_notes_api.web.dto;

public record CreateUserRequest(
        String name,
        String email,
        String password
) {}
