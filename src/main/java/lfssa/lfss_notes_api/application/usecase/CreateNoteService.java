package lfssa.lfss_notes_api.application.usecase;

import lfssa.lfss_notes_api.application.dto.CreateNoteCommand;
import lfssa.lfss_notes_api.application.dto.NoteOutput;
import lfssa.lfss_notes_api.application.exception.UserNotFoundException;
import lfssa.lfss_notes_api.application.port.in.CreateNoteUseCase;
import lfssa.lfss_notes_api.application.port.out.LoadUserPort;
import lfssa.lfss_notes_api.application.port.out.SaveNotePort;
import lfssa.lfss_notes_api.domain.entity.Note;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Use case: create a note for a user (1:N). Validates that the user exists.
 */
@Service
public class CreateNoteService implements CreateNoteUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveNotePort saveNotePort;

    public CreateNoteService(LoadUserPort loadUserPort, SaveNotePort saveNotePort) {
        this.loadUserPort = loadUserPort;
        this.saveNotePort = saveNotePort;
    }

    @Override
    public NoteOutput create(CreateNoteCommand command) {
        if (loadUserPort.findById(command.userId()).isEmpty()) {
            throw new UserNotFoundException(command.userId());
        }
        Instant now = Instant.now();
        Note note = new Note(UUID.randomUUID(), command.userId(), command.title(), command.content(), now);
        Note saved = saveNotePort.save(note);
        return toOutput(saved);
    }

    private static NoteOutput toOutput(Note note) {
        return new NoteOutput(
                note.getId(),
                note.getUserId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt()
        );
    }
}
