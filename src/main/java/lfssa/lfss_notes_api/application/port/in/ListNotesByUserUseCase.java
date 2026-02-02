package lfssa.lfss_notes_api.application.port.in;

import lfssa.lfss_notes_api.application.dto.NoteOutput;

import java.util.List;
import java.util.UUID;

/**
 * Inbound port: list notes for a user (1:N).
 */
public interface ListNotesByUserUseCase {

    List<NoteOutput> listByUserId(UUID userId);
}
