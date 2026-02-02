package lfssa.lfss_notes_api.infrastructure.persistence.adapter;

import lfssa.lfss_notes_api.application.port.out.LoadNotesPort;
import lfssa.lfss_notes_api.application.port.out.SaveNotePort;
import lfssa.lfss_notes_api.domain.entity.Note;
import lfssa.lfss_notes_api.infrastructure.persistence.entity.NoteJpaEntity;
import lfssa.lfss_notes_api.infrastructure.persistence.entity.UserJpaEntity;
import lfssa.lfss_notes_api.infrastructure.persistence.repository.NoteJpaRepository;
import lfssa.lfss_notes_api.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Adapter: implements outbound ports for notes; maps between domain Note and JPA entities.
 */
@Component
public class NotePersistenceAdapter implements SaveNotePort, LoadNotesPort {

    private final NoteJpaRepository noteRepository;
    private final UserJpaRepository userRepository;

    public NotePersistenceAdapter(NoteJpaRepository noteRepository, UserJpaRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Note save(Note note) {
        UserJpaEntity userRef = userRepository.getReferenceById(note.getUserId());
        NoteJpaEntity entity = new NoteJpaEntity(
                note.getId(),
                userRef,
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt()
        );
        NoteJpaEntity saved = noteRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Note> findByUserId(UUID userId) {
        return noteRepository.findByUser_IdOrderByCreatedAtDesc(userId).stream()
                .map(NotePersistenceAdapter::toDomain)
                .toList();
    }

    private static Note toDomain(NoteJpaEntity entity) {
        return new Note(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt()
        );
    }
}
