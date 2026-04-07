package lfssa.lfss_notes_api.web;

import lfssa.lfss_notes_api.application.user.dto.CreateUserCommand;
import lfssa.lfss_notes_api.application.user.dto.UserOutput;
import lfssa.lfss_notes_api.domain.entity.User;
import lfssa.lfss_notes_api.web.dto.CreateUserRequest;

public class WebMapper {
    public static CreateUserCommand createRequestToCommand(CreateUserRequest user) {
        return new CreateUserCommand(user.name(), user.email(), user.password());
    }

    public static UserOutput userToOutput(User user) {
        return new UserOutput(user.getId(), user.getName(), user.getEmail());
    }
}
