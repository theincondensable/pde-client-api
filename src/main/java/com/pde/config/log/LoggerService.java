package com.pde.config.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author abbas
 */
@Service
public class LoggerService {
    private final Logger logger = LogManager.getLogger(LoggerService.class);

    /**
     * <p>Logs the Incoming Requests.</p>
     *
     * @param scope      is the Controller's Class Name joined with Controller's Method Name.
     * @param parameters the incoming Parameters.
     */
    public void logRequest(String trackId, String scope, Object[] parameters) {
        String params = null;
        if (parameters != null)
            params = Arrays.toString(parameters);

        logger.info("Track ID: " + trackId +
                " Request in: " + scope
                + (params == null ? "" : " with Parameters: " + params)
        );
    }

    /**
     * <p>Logs the Outgoing Responses.</p>
     *
     * @param scope      is the Controller's Class Name joined with Controller's Method Name.
     * @param parameters the outgoing Parameters.
     * @param duration   the duration of the request to response.
     */
    public void logResponse(String trackId, String scope, String parameters, long duration) {
        logger.info("Track ID: " + trackId +
                " Response in: " + scope
                + (parameters.isBlank() ? "" : " with Parameters: " + parameters)
                + " in " + duration + " milliseconds."
        );
    }

    public void logException(String trackId, String scope, String message, long duration) {
        logger.error("Track ID: " + trackId +
                " Exception in: " + scope
                + " Message: " + message
                + " in " + duration + " milliseconds."
        );
    }

    public void customLog(String scope, String log) {
        logger.info("Response in: " + scope +
                " With Message: " + log
        );
    }
}
