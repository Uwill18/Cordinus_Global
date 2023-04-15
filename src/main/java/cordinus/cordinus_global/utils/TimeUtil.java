package cordinus.cordinus_global.utils;

import java.time.*;

public class TimeUtil {
    private static final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));

    public static boolean businessHoursCheck( LocalDateTime start, LocalDateTime end){
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = localStart.plusHours(14);

        if(end.isBefore(localStart.toLocalDateTime()) || start.isAfter(localEnd.toLocalDateTime())){
            return false;
        } else {
            return true;
        }


    }
}
