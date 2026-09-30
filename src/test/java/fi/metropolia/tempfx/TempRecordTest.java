package fi.metropolia.tempfx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TempRecordTest {

    @Test
    void newRecordConstructorLeavesIdAtZero() {
        TempRecord record = new TempRecord(98.6, 2, "F", 37.0);
        assertEquals(0, record.getId());
        assertEquals(98.6, record.getInputValue(), 0.0001);
        assertEquals(2, record.getUnitId());
        assertEquals("F", record.getUnitCode());
        assertEquals(37.0, record.getCelsiusValue(), 0.0001);
    }

    @Test
    void fullConstructorStoresEveryField() {
        TempRecord record = new TempRecord(7, 300, 3, "K", 26.85, "2026-09-30 12:00:00");
        assertEquals(7, record.getId());
        assertEquals(300, record.getInputValue(), 0.0001);
        assertEquals(3, record.getUnitId());
        assertEquals("K", record.getUnitCode());
        assertEquals(26.85, record.getCelsiusValue(), 0.0001);
        assertEquals("2026-09-30 12:00:00", record.getCreatedAt());
    }

    @Test
    void settersUpdateFields() {
        TempRecord record = new TempRecord();
        record.setId(1);
        record.setInputValue(0);
        record.setUnitId(1);
        record.setUnitCode("C");
        record.setCelsiusValue(0);
        record.setCreatedAt("now");
        assertEquals(1, record.getId());
        assertEquals("C", record.getUnitCode());
        assertEquals("now", record.getCreatedAt());
    }

    @Test
    void toStringIncludesKeyFields() {
        TempRecord record = new TempRecord(50, 1, "C", 50);
        assertTrue(record.toString().contains("C"));
    }
}
