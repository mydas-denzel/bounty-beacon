CREATE TABLE programs (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    handle VARCHAR(255) NOT NULL,
    provider VARCHAR(50) NOT NULL,
    description TEXT,
    url VARCHAR(512),
    logo_url VARCHAR(512),
    bounty BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    last_polled_at TIMESTAMP,
    UNIQUE(handle, provider)
);

CREATE INDEX idx_programs_provider ON programs(provider);
CREATE INDEX idx_programs_handle ON programs(handle);
