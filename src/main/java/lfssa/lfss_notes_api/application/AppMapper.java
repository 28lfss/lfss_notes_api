package lfssa.lfss_notes_api.application;

import lfssa.lfss_notes_api.application.user.dto.UserOutput;
import lfssa.lfss_notes_api.domain.entity.User;

public class AppMapper {
    public static UserOutput userToOutput(User user) {
        return new UserOutput(user.getId(), user.getName(), user.getEmail());
    }
}
