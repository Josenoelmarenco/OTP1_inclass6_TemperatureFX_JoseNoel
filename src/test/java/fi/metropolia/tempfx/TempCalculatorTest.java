package fi.metropolia.tempfx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pure unit tests for the conversion logic (no database, no UI).
 */
@DisplayName("TempCalculator")
class TempCalculatorTest {

    private TempCalculator calc;

    @BeforeEach
    void setUp() {
        calc = new TempCalculator();
    }

    @Test
    void fahrenheitToCelsius() {
        assertEquals(0.0, calc.fahrenheitToCelsius(32), 0.0001);
        assertEquals(100.0, calc.fahrenheitToCelsius(212), 0.0001);
    }

    @Test
    void celsiusToFahrenheit() {
        assertEquals(32.0, calc.celsiusToFahrenheit(0), 0.0001);
        assertEquals(212.0, calc.celsiusToFahrenheit(100), 0.0001);
    }

    @Test
    void kelvinToCelsius() {
        assertEquals(26.85, calc.kelvinToCelsius(300), 0.0001);
        assertEquals(-273.15, calc.kelvinToCelsius(0), 0.0001);
    }

    @Test
    void celsiusToKelvin() {
        assertEquals(273.15, calc.celsiusToKelvin(0), 0.0001);
        assertEquals(373.15, calc.celsiusToKelvin(100), 0.0001);
    }

    @Test
    @DisplayName("toCelsius handles C, F and K (case-insensitive)")
    void toCelsiusHandlesAllUnits() {
        assertEquals(25.0, calc.toCelsius(25, "C"), 0.0001);
        assertEquals(0.0, calc.toCelsius(32, "F"), 0.0001);
        assertEquals(26.85, calc.toCelsius(300, "K"), 0.0001);
        assertEquals(0.0, calc.toCelsius(32, "f"), 0.0001); // lower-case works too
    }

    @Test
    void toCelsiusRejectsNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> calc.toCelsius(10, null));
    }

    @Test
    void toCelsiusRejectsUnknownUnit() {
        assertThrows(IllegalArgumentException.class, () -> calc.toCelsius(10, "X"));
    }
}
