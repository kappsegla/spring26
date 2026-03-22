CREATE TABLE IF NOT EXISTS user_entities
(
    id           VARCHAR(255) PRIMARY KEY,
    name         VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_credentials
(
    credential_id              VARCHAR(255) PRIMARY KEY,
    user_entity_user_id        VARCHAR(255) NOT NULL,
    public_key BYTEA NOT NULL,
    signature_count            BIGINT       NOT NULL,
    uv_initialized             BOOLEAN      NOT NULL,
    backup_eligible            BOOLEAN      NOT NULL,
    authenticator_transports   VARCHAR(255),
    public_key_credential_type VARCHAR(255),
    backup_state               BOOLEAN      NOT NULL,
    attestation_object BYTEA,
    attestation_client_data_json BYTEA,
    created                    TIMESTAMP    NOT NULL,
    last_used                  TIMESTAMP    NOT NULL,
    label                      VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS device_link_token
(
    token      VARCHAR(255) PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP    NOT NULL,
    used       BOOLEAN      NOT NULL
);
