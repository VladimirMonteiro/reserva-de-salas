-- V6__update_reservation_status_enum.sql

-- 1️⃣ Remove a constraint antiga (caso tenha nome automático, usamos ALTER COLUMN DROP CONSTRAINT)
ALTER TABLE reservation
    DROP CONSTRAINT IF EXISTS reservation_reservation_status_check;

-- 2️⃣ Cria a nova constraint com os valores corretos
ALTER TABLE reservation
    ADD CONSTRAINT reservation_reservation_status_check
    CHECK (reservation_status IN ('ACTIVE', 'CANCELLED', 'FINISHED'));
