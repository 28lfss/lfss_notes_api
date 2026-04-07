package lfssa.lfss_notes_api.infrastructure;

import lfssa.lfss_notes_api.domain.entity.User;
import lfssa.lfss_notes_api.infrastructure.entity.UserJpaEntity;

public class InfraMapper {
    public static UserJpaEntity entityToJpa(User user) {
        return new UserJpaEntity(user.getId(), user.getName(), user.getEmail(), user.getPasswordHash(), user.getCreatedAt());
    }

    public static User jpaToEntity(UserJpaEntity user) {
        return new User(user.getId(), user.getName(), user.getEmail(), user.getPasswordHash(), user.getCreatedAt());
    }
}
