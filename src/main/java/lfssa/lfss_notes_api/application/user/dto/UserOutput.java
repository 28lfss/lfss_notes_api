package lfssa.lfss_notes_api.application.user.dto;

import java.util.UUID;

public record UserOutput(
        UUID id,
        String name,
        String email
) {}
