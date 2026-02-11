package lfssa.lfss_notes_api.infrastructure.adapter;

import lfssa.lfss_notes_api.application.user.port.out.SaveUser;
import lfssa.lfss_notes_api.domain.entity.User;
import lfssa.lfss_notes_api.infrastructure.entity.UserJpaEntity;
import lfssa.lfss_notes_api.infrastructure.repository.UserJpaRepository;
import lfssa.lfss_notes_api.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements SaveUser {
    private final UserJpaRepository userJpaRepository;

    public UserAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity userJpa = UserMapper.entityToJpa(user);
        return UserMapper.jpaToEntity(userJpaRepository.save(userJpa));
    }
}
