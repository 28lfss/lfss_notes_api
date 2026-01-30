package lfssa.lfss_notes_api.application.port.out;

import lfssa.lfss_notes_api.domain.entity.User;

/**
 * Outbound port: persistence contract (implemented by infrastructure).
 */
public interface SaveUserPort {

    User save(User user);
}
