package lfssa.lfss_notes_api.mapper;

import lfssa.lfss_notes_api.application.user.dto.CreateUserCommand;
import lfssa.lfss_notes_api.application.user.dto.UserOutput;
import lfssa.lfss_notes_api.domain.entity.User;
import lfssa.lfss_notes_api.infrastructure.entity.UserJpaEntity;
import lfssa.lfss_notes_api.web.dto.CreateUserRequest;

public class UserMapper {
    public static CreateUserCommand createRequestToCommand(CreateUserRequest user) {
        return new CreateUserCommand(user.name(), user.email(), user.password());
    }

    public static UserOutput userToOutput(User user) {
        return new UserOutput(user.getId(), user.getName(), user.getEmail());
    }

    public static UserJpaEntity entityToJpa(User user) {
        return new UserJpaEntity(user.getId(), user.getName(), user.getEmail(), user.getPasswordHash(), user.getCreatedAt());
    }

    public static User jpaToEntity(UserJpaEntity user) {
        return new User(user.getId(), user.getName(), user.getEmail(), user.getPasswordHash(), user.getCreatedAt());
    }
}
