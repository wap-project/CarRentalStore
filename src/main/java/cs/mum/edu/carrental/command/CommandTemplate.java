package cs.mum.edu.carrental.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandTemplate implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CommandTemplate.class);

    public void dispatherForward(HttpServletRequest request, HttpServletResponse response, RequestDispatcher requestDispatcher) {
        try {
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("Forward", e);
        }
    }



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

    }


}
