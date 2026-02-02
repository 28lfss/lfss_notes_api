package lfssa.lfss_notes_api.application.port.in;

import lfssa.lfss_notes_api.application.dto.CreateNoteCommand;
import lfssa.lfss_notes_api.application.dto.NoteOutput;

/**
 * Inbound port: create a note for a user (1:N). Fails if user does not exist.
 */
public interface CreateNoteUseCase {

    NoteOutput create(CreateNoteCommand command);
}
