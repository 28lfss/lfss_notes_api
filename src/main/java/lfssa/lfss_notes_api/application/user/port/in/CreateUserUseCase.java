package lfssa.lfss_notes_api.application.user.port.in;

import lfssa.lfss_notes_api.application.user.dto.CreateUserCommand;
import lfssa.lfss_notes_api.application.user.dto.UserOutput;

public interface CreateUserUseCase {
    public UserOutput createUser(CreateUserCommand user);
}
