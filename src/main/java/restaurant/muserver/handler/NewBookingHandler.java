package restaurant.muserver.handler;

import io.muserver.ContentTypes;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jorge J Arcila Santiago
 */
public class NewBookingHandler implements RouteHandler {

    private static final Logger log = LoggerFactory.getLogger(NewBookingHandler.class);

    @Override
    public void handle(MuRequest request, MuResponse response, Map<String, String> pathParams) throws Exception {
        log.info("method handle");
        response.status(200);
        response.contentType(ContentTypes.TEXT_HTML);
        response.redirect("newBooking.html");
    }

}
