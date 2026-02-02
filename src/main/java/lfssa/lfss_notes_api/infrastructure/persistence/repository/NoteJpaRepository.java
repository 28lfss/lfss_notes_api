package lfssa.lfss_notes_api.infrastructure.persistence.repository;

import lfssa.lfss_notes_api.infrastructure.persistence.entity.NoteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for notes (infrastructure).
 */
public interface NoteJpaRepository extends JpaRepository<NoteJpaEntity, UUID> {

    List<NoteJpaEntity> findByUser_IdOrderByCreatedAtDesc(UUID userId);
}
