package lfssa.lfss_notes_api.domain.entity;

import java.sql.Timestamp;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String name;
    private final String email;
    private final String passwordHash;
    private final Timestamp createdAt;

    public User(UUID id, String name, String email, String passwordHash, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Timestamp getCreatedAt() { return createdAt; }
}
