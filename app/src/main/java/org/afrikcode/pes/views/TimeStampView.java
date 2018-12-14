package org.afrikcode.pes.views;

import org.afrikcode.pes.base.BaseView;
import org.afrikcode.pes.models.Day;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Service;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;

import java.util.List;

public interface TimeStampView extends BaseView {

    void onServiceAdded();

    void onYearAdded();

    void onMonthAdded();

    void onWeekAdded();

    void onDayAdded();

    void ongetServices(List<Service> serviceList);

    void ongetYears(List<Year> yearList);

    void ongetMonthsinYear(List<Month> monthList);

    void ongetWeeksinMonth(List<Week> weekList);

    void ongetDaysinWeek(List<Day> dayList);

    void onActivate(String msg);

    void onError(String error);
}
