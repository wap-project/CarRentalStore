package cs.mum.edu.carrental.command.user;

import cs.mum.edu.carrental.Model.User;
import cs.mum.edu.carrental.command.CommandTemplate;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LogoutCommand extends CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        cleanupCookie(request, response);
        request.getSession().invalidate();
        try {
            response.sendRedirect("/index");
        } catch (Exception e) {
            logger.error("Logout Redirect: ", e);
        }
    }

    private void cleanupCookie(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            Map<String, String> userMap = new HashMap<>();
            try {
                userMap = BeanUtils.describe(user);
            } catch (Exception e) {
                logger.error("BeanUtils Error: ", e);
            }

            for (String key : userMap.keySet()) {
                Cookie cookie = new Cookie(key, userMap.get(key));
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }
    }
}
