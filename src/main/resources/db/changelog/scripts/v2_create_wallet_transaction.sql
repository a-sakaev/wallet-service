--liquibase formatted sql
--changeset aidar:2
CREATE TABLE IF NOT EXISTS wallet_transaction (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    wallet_id uuid NOT NULL REFERENCES wallet(id),
    operation_type varchar(16) NOT NULL,
    amount numeric(19,4) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    request_id uuid
);

CREATE INDEX idx_wallet_transaction_wallet_id ON wallet_transaction(wallet_id);