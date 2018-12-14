package org.afrikcode.pes.contracts;

import org.afrikcode.pes.enums.TimestampType;
import org.afrikcode.pes.models.Day;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Service;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;

public interface TimeStampContract {

    void addService(Service service);

    void addYear(Year year);

    void addMonth(Month month);

    void addWeek(Week week);

    void addDay(Day day);

    void getServices();

    void getYears(String serviceID);

    void getMonthsinYear(String yearID);

    void getWeeksinMonth(String yearID, String monthID);

    void getDaysinWeeK(String yearID, String monthID, String weekID);

    void setTimelineActiveStatus(String id, TimestampType type, boolean isActive);
}
