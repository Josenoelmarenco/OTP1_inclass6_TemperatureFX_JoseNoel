package fi.metropolia.tempfx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemperatureUnitTest {

    @Test
    void fullConstructorAndGetters() {
        TemperatureUnit unit = new TemperatureUnit(1, "C", "Celsius");
        assertEquals(1, unit.getId());
        assertEquals("C", unit.getCode());
        assertEquals("Celsius", unit.getName());
    }

    @Test
    void settersUpdateFields() {
        TemperatureUnit unit = new TemperatureUnit();
        unit.setId(2);
        unit.setCode("F");
        unit.setName("Fahrenheit");
        assertEquals(2, unit.getId());
        assertEquals("F", unit.getCode());
        assertEquals("Fahrenheit", unit.getName());
    }

    @Test
    void toStringIsComboBoxFriendly() {
        TemperatureUnit unit = new TemperatureUnit("K", "Kelvin");
        assertTrue(unit.toString().contains("Kelvin"));
        assertTrue(unit.toString().contains("K"));
    }
}
