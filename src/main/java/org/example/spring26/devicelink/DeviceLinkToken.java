package org.example.spring26.devicelink;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class DeviceLinkToken {

    @Id
    private String token;

    private String username;

    private Instant expiresAt;

    private boolean used;

    public DeviceLinkToken() {
    }

    public DeviceLinkToken(String token, String username, Instant expiresAt, boolean used) {
        this.token = token;
        this.username = username;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
