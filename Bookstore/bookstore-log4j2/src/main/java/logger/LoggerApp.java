package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerApp {

    private Logger logger;

    public LoggerApp(Class clazz) {
        logger = LogManager.getLogger(clazz);
    }

    public void infoLogger(String message) {
        logger.info(message);
    }

    public void warningLogger(String message) {
        logger.warn(message);
    }

    public void errorLogger(String message) {
        logger.error(message);
    }

}
