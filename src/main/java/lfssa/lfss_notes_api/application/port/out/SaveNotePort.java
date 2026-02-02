package lfssa.lfss_notes_api.application.port.out;

import lfssa.lfss_notes_api.domain.entity.Note;

/**
 * Outbound port: persist a note (implemented by infrastructure).
 */
public interface SaveNotePort {

    Note save(Note note);
}
