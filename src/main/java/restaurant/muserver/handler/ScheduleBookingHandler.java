package restaurant.muserver.handler;

import io.muserver.ContentTypes;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restaurant.muserver.commons.Commons;
import restaurant.muserver.commons.Constants;
import restaurant.muserver.pojo.Booking;
import restaurant.muserver.service.BookingService;

/**
 *
 * @author Jorge J Arcila Santiago
 */
public class ScheduleBookingHandler implements RouteHandler {

    private final BookingService bookingService;
    private static final Logger log = LoggerFactory.getLogger(ScheduleBookingHandler.class);

    public ScheduleBookingHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void handle(MuRequest request, MuResponse response, Map<String, String> pathParams) throws Exception {
        log.info("method handle");
        response.status(200);
        response.contentType(ContentTypes.TEXT_HTML);
        String dateStr = request.form().get("bookingDate");
        String fName = request.form().get("fName");
        String lName = request.form().get("lName");
        int tSize = request.form().getInt("tSize", 0);
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append(Constants.linksHeaderHtml);
        if (dateStr.isEmpty() || fName.isEmpty() || lName.isEmpty() || tSize == 0) {
            htmlResponse.append("<main><h2>Error on Form</h2><p>");
            htmlResponse.append("All fields are mandatory. Table Size must be greater than 0");
            htmlResponse.append("<br/><a href=\"/newBooking\"><-Go Back</a></p></main>");
        } else {
            Booking booking = new Booking(Commons.convertStringToDate(dateStr), fName, lName, tSize);

            log.info("New Booking scheduled:\tFirst Name:" + booking.getFirstName() + "\tLast Name:" + booking.getLastName()
                    + "\tTable Size:" + booking.getTableSize() + "\tBooking Date: " + booking.getBookingDateStr());

            this.bookingService.scheduleBooking(booking);
            htmlResponse.append(Constants.linksHeaderHtml);
            htmlResponse.append("<main><h2>Booking Scheduled</h2><p>");
            htmlResponse.append("<table><tr><td><label>Last name:</label></td>");
            htmlResponse.append(Constants.trClose);
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(booking.getLastName());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.trOpen);
            htmlResponse.append("<td><label>First name:</label></td>");
            htmlResponse.append(Constants.trClose);
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(booking.getFirstName());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.trOpen);
            htmlResponse.append("<td><label>Date:</label></td>");
            htmlResponse.append(Constants.trClose);
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(booking.getBookingDateStr());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.trOpen);
            htmlResponse.append("<td><label>Table Size:</label></td>");
            htmlResponse.append(Constants.trClose);
            htmlResponse.append(Constants.tdOpen);
            htmlResponse.append(booking.getTableSize());
            htmlResponse.append(Constants.tdClose);
            htmlResponse.append(Constants.trClose);
            htmlResponse.append("</table>");
            response.redirect("newBooking.html");
        }
    }

}
