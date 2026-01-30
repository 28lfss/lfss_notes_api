package lfssa.lfss_notes_api.infrastructure.persistence.repository;

import lfssa.lfss_notes_api.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA repository (infrastructure).
 */
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
}
