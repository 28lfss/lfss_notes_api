package lfssa.lfss_notes_api.application.port.out;

import lfssa.lfss_notes_api.domain.entity.Note;

import java.util.List;
import java.util.UUID;

/**
 * Outbound port: load notes by user id (1:N).
 */
public interface LoadNotesPort {

    List<Note> findByUserId(UUID userId);
}
