-- Seed data: the three temperature units.
USE tempfx;

INSERT INTO temperature_unit (code, name) VALUES
    ('C', 'Celsius'),
    ('F', 'Fahrenheit'),
    ('K', 'Kelvin')
ON DUPLICATE KEY UPDATE name = VALUES(name);
