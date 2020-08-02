package other;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Day {
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    public Day() {
        calendar = new GregorianCalendar(Locale.KOREA);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public String getToday() {
        return simpleDateFormat.format(calendar.getTime());
    }

    public String getYesterday() {
        calendar.add(Calendar.DATE, -1);
        return simpleDateFormat.format(calendar.getTime());
    }
}
