package hacker;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.message.MessageFactory;

public class HackedLogger extends Logger {
    private static ArrayList<String> list = new ArrayList();

    protected HackedLogger(LoggerContext context, String name, MessageFactory messageFactory) {
        super(context, name, messageFactory);
    }

    public static HackedLogger getLogger() {
        Logger logger = (Logger) LogManager.getLogger();
        return new HackedLogger(logger.getContext(), logger.getName(), logger.getMessageFactory());
    }

    @Override
    public void error(String message, Throwable t) {
        for (String s : list) {
            if (message.startsWith(s)) return;
        }

        super.error(message, t);
    }

    static {
        list.add("Using missing texture");
        list.add("Skipping BlockEntity");
    }
}
