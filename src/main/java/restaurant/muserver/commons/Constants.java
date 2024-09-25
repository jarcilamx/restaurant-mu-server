package restaurant.muserver.commons;

/**
 *
 * @author Jorge J Arcila Santiago
 */
public class Constants {

    public static int httpPort = 8080;
    public static int httpsPort = 8443;
    public static String webPagesPath = "src/main/resources/web";
    public static String webFolder = "/web";

    public static String linksHeaderHtml = "<header>"
            + "<h1>Restaurant</h1>"
            + "<nav>"
            + "<a href=\"/\">Home</a>&emsp;"
            + "<a href=\"/newBooking\">New Booking</a>&emsp;"
            + "<a href=\"/viewBookings\">View Scheduled Bookings</a>&emsp;"
            + "</nav>"
            + "</header>"
            + "<br/>";
    
    public static String tdOpenHeader = "<td style=\"border: 2px solid\">";
    public static String tdOpen = "<td style=\"text-align: left;\">";
    public static String tdClose = "</td>";
    public static String trOpen = "<tr>";
    public static String trClose = "</tr>";
}
