package restaurant.muserver.handler;

import io.muserver.ContentTypes;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;
import restaurant.muserver.commons.Commons;
import restaurant.muserver.commons.Constants;
import restaurant.muserver.pojo.Booking;
import restaurant.muserver.service.BookingService;

/**
 *
 * @author Jorge J Arcila Santiago
 */
public class SearchBookingsHandler implements RouteHandler {

    private final BookingService bookingService;

    public SearchBookingsHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void handle(MuRequest request, MuResponse response, Map<String, String> pathParams) throws Exception {
        response.status(200);
        response.contentType(ContentTypes.TEXT_HTML);

        //tempMethod();

        Date selectedDate = Commons.convertStringToDate(request.form().get("selectedDate"));
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append(Constants.linksHeaderHtml);
        htmlResponse.append("<main><table>");
        htmlResponse.append(Constants.trOpen);
        htmlResponse.append(Constants.tdOpenHeader);
        htmlResponse.append("First Name");
        htmlResponse.append(Constants.tdClose);
        htmlResponse.append(Constants.tdOpenHeader);
        htmlResponse.append("Middle Name");
        htmlResponse.append(Constants.tdClose);
        htmlResponse.append(Constants.tdOpenHeader);
        htmlResponse.append("Last Name");
        htmlResponse.append(Constants.tdClose);
        htmlResponse.append(Constants.tdOpenHeader);
        htmlResponse.append("Table Size");
        htmlResponse.append(Constants.tdClose);
        htmlResponse.append(Constants.tdOpenHeader);
        htmlResponse.append("Booking Date");
        htmlResponse.append(Constants.tdClose);
        htmlResponse.append(Constants.trClose);

        Consumer<Booking> consumer = b -> {
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(b.getFirstName());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(b.getMiddletName());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(b.getLastName());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(b.getTableSize());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(b.getBookingDateStr());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.trClose);
        };

        if (null != selectedDate) {
            this.bookingService.findBookingsByDate(selectedDate).forEach(consumer);
        } else {
            this.bookingService.findAll().forEach(consumer);
        }
        htmlResponse.append("</table></main>");

        response.sendChunk(htmlResponse.toString());
    }

    private void tempMethod() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (bookingService.findAll().isEmpty()) {
            bookingService.scheduleBooking(new Booking(cal.getTime(), "John", "Smith", 1, "M."));
            bookingService.scheduleBooking(new Booking(cal.getTime(), "Jane", "Doe", 2, ""));

            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 2);
            bookingService.scheduleBooking(new Booking(cal.getTime(), "Jorge", "Arcila", 3, "Juan"));
            bookingService.scheduleBooking(new Booking(cal.getTime(), "Juan", "Santiago", 4, ""));
            bookingService.scheduleBooking(new Booking(cal.getTime(), "Ady", "Yescas", 5, "R."));
        }
    }
}
