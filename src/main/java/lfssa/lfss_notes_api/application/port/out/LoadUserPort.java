package lfssa.lfss_notes_api.application.port.out;

import lfssa.lfss_notes_api.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Outbound port: load a user by id (e.g. to validate owner when creating a note).
 */
public interface LoadUserPort {

    Optional<User> findById(UUID id);
}
