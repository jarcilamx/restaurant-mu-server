package restaurant.muserver.commons;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge J Arcila Santiago
 */
public class Commons {

    public static Date convertStringToDate(String dateStr) {
        try {
            Calendar cal = Calendar.getInstance();
            String[] dateArr = dateStr.split("T")[0].split("-");
            String[] timeArr = dateStr.split("T")[1].split(":");

            cal.set(Calendar.YEAR, Integer.parseInt(dateArr[0]));
            cal.set(Calendar.MONTH, Integer.parseInt(dateArr[1]) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArr[2]));
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));

            return cal.getTime();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(Commons.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
