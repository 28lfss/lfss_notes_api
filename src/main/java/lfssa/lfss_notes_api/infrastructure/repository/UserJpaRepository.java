package lfssa.lfss_notes_api.infrastructure.repository;

import lfssa.lfss_notes_api.infrastructure.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {

}
