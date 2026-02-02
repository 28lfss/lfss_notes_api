package lfssa.lfss_notes_api.infrastructure.persistence.adapter;

import lfssa.lfss_notes_api.application.port.out.LoadUserPort;
import lfssa.lfss_notes_api.application.port.out.SaveUserPort;
import lfssa.lfss_notes_api.domain.entity.User;
import lfssa.lfss_notes_api.infrastructure.persistence.entity.UserJpaEntity;
import lfssa.lfss_notes_api.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter: implements outbound ports and maps between domain and JPA entities.
 */
@Component
public class UserPersistenceAdapter implements SaveUserPort, LoadUserPort {

    private final UserJpaRepository jpaRepository;

    public UserPersistenceAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = toJpaEntity(user);
        UserJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(UserPersistenceAdapter::toDomain);
    }

    private static UserJpaEntity toJpaEntity(User user) {
        return new UserJpaEntity(user.getId(), user.getName(), user.getPassword());
    }

    private static User toDomain(UserJpaEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getPassword());
    }
}
