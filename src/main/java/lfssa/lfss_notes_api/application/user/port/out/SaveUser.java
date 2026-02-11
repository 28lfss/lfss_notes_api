package lfssa.lfss_notes_api.application.user.port.out;

import lfssa.lfss_notes_api.domain.entity.User;

public interface SaveUser {
    User save(User test);
}
