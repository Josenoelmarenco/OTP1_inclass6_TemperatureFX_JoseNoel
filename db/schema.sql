-- Database schema for the Temperature Converter (In-class 6).
-- Two related tables: temperature_unit (1) --- (many) temp_record.

CREATE DATABASE IF NOT EXISTS tempfx;
USE tempfx;

-- Lookup table: the temperature units (Celsius, Fahrenheit, Kelvin)
CREATE TABLE IF NOT EXISTS temperature_unit (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(5)  NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL
);

-- Each conversion the user saves, linked to the unit it was entered in
CREATE TABLE IF NOT EXISTS temp_record (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    input_value   DOUBLE NOT NULL,
    unit_id       INT    NOT NULL,
    celsius_value DOUBLE NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_temp_record_unit
        FOREIGN KEY (unit_id) REFERENCES temperature_unit(id)
);
