package cs.mum.edu.carrental.command.user;

import cs.mum.edu.carrental.Model.User;
import cs.mum.edu.carrental.Model.UserErrors;
import cs.mum.edu.carrental.command.CommandTemplate;
import cs.mum.edu.carrental.dao.DaoFactory;
import cs.mum.edu.carrental.dao.UserDao;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthCommand extends CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(AuthCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().removeAttribute("userError");
        request.getSession().removeAttribute("auth");

        User user = getUserFromParameters(request);
        UserErrors userErrors = new UserErrors();
        boolean isAnyError = verifyUserParams(request, user, userErrors);
        if (isAnyError) {
            //store user validation error info
            request.getSession().setAttribute("userError", userErrors);
            request.getSession().setAttribute("auth", true);
        } else {
            addAuthCookies(request, response, user);
        }

        RequestDispatcher requestDispatcher = getSamePageDispatcher(request);

    }

    private boolean verifyUserParams(HttpServletRequest request, User user, UserErrors userErrors) {
        boolean isAnyError = false;
        if (user.getEmail() != null && user.getPassport() != null && !user.getEmail().trim().equals("")
                && user.getPassport().trim().equals("")) {
            try {
                UserDao userDao = DaoFactory.getUserDao();
                isAnyError =  !userDao.findByEmailAndPassword(user);
                if (isAnyError) {
                    if (userDao.findByEmail(user)) {
                        userErrors.setPassport("WRONG_PASSWORD");
                    } else {
                        userErrors.setEmail("NO_USER_FOR_EMAIL");
                    }
                }

            } catch (SQLException e) {
                userErrors.setEmail("BAD_DB_CONN");
                logger.error("DBError: ", e);
            }
        } else {
            isAnyError = true;
            userErrors.setEmail("BLANK_FIELDS");
        }

        // why?
        request.getSession().setAttribute("user", user);

        return isAnyError;
    }

    public void addAuthCookies(HttpServletRequest request, HttpServletResponse response, User user) {
        String cookieOn = request.getParameter("cookieOn");
        if (cookieOn != null && cookieOn.equals("on")) {
            Map<String, String> userMap = new HashMap<>();
            try {
                userMap = BeanUtils.describe(user);
            } catch (Exception e) {
                logger.error("BeanUtils Error: ", e);
            }

            for (String key : userMap.keySet()) {
                Cookie cookie = new Cookie(key, userMap.get(key));
                cookie.setMaxAge(3600 * 24 * 7);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }
    }
}
