package fi.metropolia.tempfx;

/**
 * A saved conversion. Maps to the temp_record table.
 * unitCode is denormalized (copied from the joined unit) only for display.
 */
public class TempRecord {
    private int id;
    private double inputValue;
    private int unitId;
    private String unitCode;
    private double celsiusValue;
    private String createdAt;

    public TempRecord() {
    }

    /** Used when creating a new record (id and createdAt are set by the database). */
    public TempRecord(double inputValue, int unitId, String unitCode, double celsiusValue) {
        this.inputValue = inputValue;
        this.unitId = unitId;
        this.unitCode = unitCode;
        this.celsiusValue = celsiusValue;
    }

    /** Used when reading a record back from the database. */
    public TempRecord(int id, double inputValue, int unitId, String unitCode,
                      double celsiusValue, String createdAt) {
        this.id = id;
        this.inputValue = inputValue;
        this.unitId = unitId;
        this.unitCode = unitCode;
        this.celsiusValue = celsiusValue;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public double getCelsiusValue() {
        return celsiusValue;
    }

    public void setCelsiusValue(double celsiusValue) {
        this.celsiusValue = celsiusValue;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TempRecord{id=" + id + ", inputValue=" + inputValue
                + ", unitCode='" + unitCode + '\'' + ", celsiusValue=" + celsiusValue
                + ", createdAt='" + createdAt + '\'' + '}';
    }
}
