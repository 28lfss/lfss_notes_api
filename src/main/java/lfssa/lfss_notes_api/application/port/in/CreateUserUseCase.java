package lfssa.lfss_notes_api.application.port.in;

import lfssa.lfss_notes_api.application.dto.CreateUserCommand;
import lfssa.lfss_notes_api.domain.entity.User;

/**
 * Inbound port: use case for creating a user (application boundary).
 */
public interface CreateUserUseCase {

    User create(CreateUserCommand command);
}
