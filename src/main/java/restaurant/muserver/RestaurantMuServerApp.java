package restaurant.muserver;

import io.muserver.*;
import io.muserver.handlers.ResourceHandlerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import restaurant.muserver.commons.Constants;
import restaurant.muserver.handler.NewBookingHandler;
import restaurant.muserver.handler.ScheduleBookingHandler;
import restaurant.muserver.handler.RequestLoggingHandler;
import restaurant.muserver.handler.SearchBookingsHandler;
import restaurant.muserver.handler.ViewBookingsHandler;
import restaurant.muserver.service.BookingService;

public class RestaurantMuServerApp {
    private static final Logger log = LoggerFactory.getLogger(RestaurantMuServerApp.class);

    public static void main(String[] args) {
        BookingService bookingService = new BookingService();
        log.info("Starting restaurant-mu-server app");
        MuServer server = MuServerBuilder.muServer()
            .withHttpPort(Constants.httpPort)
            .withHttpsPort(Constants.httpsPort)
            .withHttpsConfig(SSLContextBuilder.sslContext()
                    .withKeystoreFromClasspath("/keystore.jks")
                    .withKeystoreType("JKS")
                    .withKeystorePassword("Very5ecure")
                    .withKeyPassword("ActuallyNotSecure")
                    .build()
            )
            .addHandler(new RequestLoggingHandler())
            .addHandler(Method.GET, "/newBooking", new NewBookingHandler())
            .addHandler(Method.POST, "/scheduleBooking", new ScheduleBookingHandler(bookingService))
            .addHandler(Method.GET, "/viewBookings", new ViewBookingsHandler())
            .addHandler(Method.POST, "/searchBookings", new SearchBookingsHandler(bookingService))
            .addHandler(ResourceHandlerBuilder.fileOrClasspath(Constants.webPagesPath, Constants.webFolder)
                .withPathToServeFrom("/")
                .withDefaultFile("index.html")
                .build())
            .start();

        log.info("Server started at " + server.httpUri() + " and " + server.httpsUri());
    }
}
