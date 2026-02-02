package lfssa.lfss_notes_api.application.usecase;

import lfssa.lfss_notes_api.application.dto.NoteOutput;
import lfssa.lfss_notes_api.application.port.in.ListNotesByUserUseCase;
import lfssa.lfss_notes_api.application.port.out.LoadNotesPort;
import lfssa.lfss_notes_api.domain.entity.Note;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Use case: list notes for a user (1:N).
 */
@Service
public class ListNotesByUserService implements ListNotesByUserUseCase {

    private final LoadNotesPort loadNotesPort;

    public ListNotesByUserService(LoadNotesPort loadNotesPort) {
        this.loadNotesPort = loadNotesPort;
    }

    @Override
    public List<NoteOutput> listByUserId(UUID userId) {
        List<Note> notes = loadNotesPort.findByUserId(userId);
        return notes.stream()
                .map(n -> new NoteOutput(
                        n.getId(),
                        n.getUserId(),
                        n.getTitle(),
                        n.getContent(),
                        n.getCreatedAt()
                ))
                .toList();
    }
}
