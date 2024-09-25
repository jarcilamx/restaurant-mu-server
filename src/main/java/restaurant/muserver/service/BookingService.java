package restaurant.muserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restaurant.muserver.pojo.Booking;

/**
 *
 * @author Jorge J Arcila Santiago
 */
public class BookingService {

    private static List<Booking> bookings;
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    public List<Booking> findBookingsByDate(Date date) {
        if (null == bookings) {
            return new ArrayList<>();
        }
        Date from = createDate(date, true);
        Date to = createDate(date, false);
        log.info("findBookingsByDate From: " + from.toString() + " to " + to.toString());
        return bookings.stream().filter(b -> b.getBookingDate().after(from) && b.getBookingDate().before(to)).collect(Collectors.toList());
    }

    public void scheduleBooking(Booking booking) {
        if (null == bookings) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
    }

    private Date createDate(Date date, boolean startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, (startDate) ? 0 : 23);
        cal.set(Calendar.MINUTE, (startDate) ? 0 : 59);
        cal.set(Calendar.SECOND, (startDate) ? 0 : 59);
        return cal.getTime();
    }

    public List<Booking> findAll() {
        if (null == bookings) {
            bookings = new ArrayList<>();
        }
        return bookings;
    }

}
