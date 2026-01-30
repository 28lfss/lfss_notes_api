package lfssa.lfss_notes_api.domain.entity;

import java.util.UUID;

/**
 * Domain entity: core business concept with no framework dependencies.
 */
public class User {

    private final UUID id;
    private final String name;
    private final String password;

    public User(UUID id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
