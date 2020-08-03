package gr.codehub.rsapi.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JExample {

   private static final Logger logger = LoggerFactory.getLogger(SLF4JExample.class);

    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }
}