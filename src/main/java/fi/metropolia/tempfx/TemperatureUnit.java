package fi.metropolia.tempfx;

/**
 * A temperature unit (e.g., Celsius, Fahrenheit, Kelvin).
 * Maps to the temperature_unit table.
 */
public class TemperatureUnit {
    private int id;
    private String code;
    private String name;

    public TemperatureUnit() {
    }

    public TemperatureUnit(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public TemperatureUnit(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Shown in the JavaFX ComboBox, e.g. "Celsius (C)". */
    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}
