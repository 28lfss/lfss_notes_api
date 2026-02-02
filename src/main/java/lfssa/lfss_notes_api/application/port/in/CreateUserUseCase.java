package lfssa.lfss_notes_api.application.port.in;

import lfssa.lfss_notes_api.application.dto.CreateUserCommand;
import lfssa.lfss_notes_api.application.dto.UserOutput;

/**
 * Inbound port: use case for creating a user (application boundary).
 * Returns application DTO so presentation never depends on domain.
 */
public interface CreateUserUseCase {

    UserOutput create(CreateUserCommand command);
}
