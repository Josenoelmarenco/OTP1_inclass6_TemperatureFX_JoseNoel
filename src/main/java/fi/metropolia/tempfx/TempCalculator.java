package fi.metropolia.tempfx;

/**
 * Pure temperature-conversion logic. No database, no UI — fully unit-testable.
 */
public class TempCalculator {

    // Fahrenheit -> Celsius.  C = (F - 32) * 5 / 9
    public double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    // Celsius -> Fahrenheit.  F = (C * 9 / 5) + 32
    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    // Kelvin -> Celsius.  C = K - 273.15
    public double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    // Celsius -> Kelvin.  K = C + 273.15
    public double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    /**
     * Converts any supported unit to Celsius.
     *
     * @param value    the temperature value
     * @param unitCode "C", "F" or "K" (case-insensitive)
     * @return the value expressed in Celsius
     * @throws IllegalArgumentException if the unit code is null or unknown
     */
    public double toCelsius(double value, String unitCode) {
        if (unitCode == null) {
            throw new IllegalArgumentException("unitCode must not be null");
        }
        switch (unitCode.toUpperCase()) {
            case "C":
                return value;
            case "F":
                return fahrenheitToCelsius(value);
            case "K":
                return kelvinToCelsius(value);
            default:
                throw new IllegalArgumentException("Unknown unit: " + unitCode);
        }
    }
}
