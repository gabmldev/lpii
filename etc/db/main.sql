CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Tabla role
CREATE TABLE role (
    id          UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    name        TEXT    NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ DEFAULT NOW(),
    updated_at  TIMESTAMPTZ DEFAULT NOW()
);

-- Tabla profile
CREATE TABLE profile (
    id          UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    avatar      TEXT,
    created_at  TIMESTAMPTZ DEFAULT NOW(),
    updated_at  TIMESTAMPTZ DEFAULT NOW()
);

-- Tabla users
CREATE TABLE users (
    id          UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    username    TEXT    NOT NULL,
    email       TEXT    NOT NULL,
    pwd         TEXT    NOT NULL,
    role_id     UUID    NOT NULL,
    profile_id  UUID    NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT NOW(),
    updated_at  TIMESTAMPTZ DEFAULT NOW(),

    CONSTRAINT fk_users_role
        FOREIGN KEY (role_id)
        REFERENCES role (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_users_profile
        FOREIGN KEY (profile_id)
        REFERENCES profile (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Tabla session
CREATE TABLE session (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    jti        TEXT        NOT NULL,
    token       TEXT        NOT NULL,
    user_id     UUID        NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    expires_at  TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_session_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla provider
CREATE TABLE provider (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    area        TEXT        NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT NOW(),
    updated_at  TIMESTAMPTZ DEFAULT NOW()
);

-- Tabla product_metadata
CREATE TABLE product_metadata (
    id              UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    brand           TEXT    NOT NULL,
    category        TEXT    NOT NULL,
    has_stack       BOOLEAN NOT NULL DEFAULT false,
    has_sell_state  BOOLEAN NOT NULL DEFAULT false,
    created_at      TIMESTAMPTZ DEFAULT NOW(),
    updated_at      TIMESTAMPTZ DEFAULT NOW()
);

-- Tabla product
CREATE TABLE product (
    id                   UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    name                 TEXT    NOT NULL,
    description          TEXT,
    product_metadata_id  UUID    NOT NULL,
    provider_id          UUID    NOT NULL,
    profile_id           UUID    NOT NULL,
    created_at           TIMESTAMPTZ DEFAULT NOW(),
    updated_at           TIMESTAMPTZ DEFAULT NOW(),

    CONSTRAINT fk_product_metadata
        FOREIGN KEY (product_metadata_id)
        REFERENCES product_metadata (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_product_provider
        FOREIGN KEY (provider_id)
        REFERENCES provider (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_product_profile
        FOREIGN KEY (profile_id)
        REFERENCES profile (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Tabla payment_address
CREATE TABLE payment_address (
    id          UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    street      TEXT    NOT NULL,
    city        TEXT    NOT NULL,
    state       TEXT    NOT NULL,
    postal_code TEXT    NOT NULL,
    country     TEXT    NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT NOW(),
    updated_at  TIMESTAMPTZ DEFAULT NOW()
);

-- Tabla payment
CREATE TABLE payment (
    id                  UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    number              TEXT    NOT NULL,
    holder_name         TEXT    NOT NULL,
    expiry_month        INTEGER NOT NULL,
    expiry_year         INTEGER NOT NULL,
    cvv                 TEXT    NOT NULL,
    brand               TEXT    NOT NULL,
    provider_id         UUID,
    profile_id          UUID,
    payment_address_id  UUID,
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW(),

    CONSTRAINT fk_payment_provider
        FOREIGN KEY (provider_id)
        REFERENCES provider (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT fk_payment_profile
        FOREIGN KEY (profile_id)
        REFERENCES profile (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT fk_payment_address
        FOREIGN KEY (payment_address_id)
        REFERENCES payment_address (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT chk_expiry_month CHECK (expiry_month BETWEEN 1 AND 12),
    CONSTRAINT chk_expiry_year CHECK (expiry_year >= EXTRACT(YEAR FROM NOW())::INTEGER)
);

-- Tabla log
CREATE TABLE log (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    trigger     TEXT        NOT NULL,
    origin      TEXT        NOT NULL,
    payload     TEXT,
    created_at  TIMESTAMPTZ DEFAULT NOW()
);

-- Índices únicos
CREATE UNIQUE INDEX uq_users_username ON users (username);
CREATE UNIQUE INDEX uq_users_email ON users (email);
CREATE UNIQUE INDEX uq_profile_user ON users (profile_id); -- Un usuario por profile
CREATE UNIQUE INDEX uq_product_metadata ON product (product_metadata_id); -- Relación 1:1
CREATE UNIQUE INDEX uq_payment_address ON payment (payment_address_id); -- Relación 1:1

-- Índices para mejorar rendimiento
CREATE INDEX idx_session_user_jti ON session(user_id, jti);
CREATE INDEX idx_session_expires_at ON session(expires_at);
CREATE INDEX idx_product_provider ON product(provider_id);
CREATE INDEX idx_product_profile ON product(profile_id);
CREATE INDEX idx_payment_profile ON payment(profile_id);
CREATE INDEX idx_payment_provider ON payment(provider_id);
CREATE INDEX idx_log_created_at ON log(created_at);
CREATE INDEX idx_log_trigger ON log(trigger);

-- Constraints adicionales
ALTER TABLE session ADD CONSTRAINT chk_expires_after_created
    CHECK (expires_at > created_at);

-- Función para actualizar updated_at automáticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers para updated_at
CREATE TRIGGER update_role_updated_at BEFORE UPDATE ON role FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_profile_updated_at BEFORE UPDATE ON profile FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_provider_updated_at BEFORE UPDATE ON provider FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_product_metadata_updated_at BEFORE UPDATE ON product_metadata FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_product_updated_at BEFORE UPDATE ON product FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_payment_address_updated_at BEFORE UPDATE ON payment_address FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_payment_updated_at BEFORE UPDATE ON payment FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();