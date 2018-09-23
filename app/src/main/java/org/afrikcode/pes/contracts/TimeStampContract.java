package org.afrikcode.pes.contracts;

import org.afrikcode.pes.enums.TimestampType;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;

public interface TimeStampContract {

    void addYear(Year year);

    void addMonth(Month month);

    void addWeek(Week week);

    void getYears();

    void getMonthsinYear(String yearID);

    void getWeeksinMonth(String yearID, String monthID);

    void setTimelineActiveStatus(String id, TimestampType type, boolean isActive);
}
