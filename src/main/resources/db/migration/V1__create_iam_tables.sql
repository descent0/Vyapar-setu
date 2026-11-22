CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(16) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    platform_role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE businesses (
    id BIGSERIAL PRIMARY KEY,
    business_name VARCHAR(150) NOT NULL,
    business_type VARCHAR(20) NOT NULL,
    address VARCHAR(500),
    latitude NUMERIC(10, 7),
    longitude NUMERIC(10, 7),
    verification_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE business_memberships (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    business_id BIGINT NOT NULL REFERENCES businesses(id),
    membership_role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT uk_membership_user_business UNIQUE (user_id, business_id)
);

CREATE INDEX idx_membership_user_id ON business_memberships(user_id);
CREATE INDEX idx_membership_business_id ON business_memberships(business_id);
