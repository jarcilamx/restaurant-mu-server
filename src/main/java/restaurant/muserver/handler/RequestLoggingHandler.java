package restaurant.muserver.handler;

import io.muserver.MuHandler;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jorge J Arcila Santiago
 */
public class RequestLoggingHandler implements MuHandler {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingHandler.class);

    @Override
    public boolean handle(MuRequest request, MuResponse response) {
        log.info(request.method() + " " + request.uri());
        return false;
    }
}
