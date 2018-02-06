package cs.mum.edu.carrental.command.user;

import cs.mum.edu.carrental.command.CommandTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand extends CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        try {
            response.sendRedirect("/index");
        } catch (Exception e) {
            logger.error("Logout Redirect: ", e);
        }
    }
}
