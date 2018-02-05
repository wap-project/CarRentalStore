package cs.mum.edu.carrental.servlet;

import cs.mum.edu.carrental.Model.User;
import cs.mum.edu.carrental.command.Command;
import cs.mum.edu.carrental.command.CommandFactory;
import cs.mum.edu.carrental.dao.DaoFactory;
import cs.mum.edu.carrental.dao.DbInitDestroyDao;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "MainServlet")
public class MainServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MainServlet.class);

    @Override
    public void init() {
        try {
            DbInitDestroyDao dbInitDestroyDao = DaoFactory.getDbInitDestroyDao();
            dbInitDestroyDao.initDB();
        } catch (SQLException e) {
            logger.error("DBError: ", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearSessionInvalidUser(request);
        autorizeByCookies(request);
        Command command = CommandFactory.createCommand(request);
        command.execute(request, response);
    }

    private void autorizeByCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies!= null && cookies.length > 1) {
            Map<String, String> userMap = new HashMap<>();
            for (Cookie cookie : cookies) {
                userMap.put(cookie.getName(), cookie.getValue());
            }
            User user = new User();
            try {
                BeanUtils.populate(user, userMap);
            } catch (ReflectiveOperationException e) {
                logger.error("BeanUtilsError", e);
            }
            request.getSession().setAttribute("user", user);
        }
    }

    private void clearSessionInvalidUser(HttpServletRequest request) {
        if (request.getSession().getAttribute("userError") != null) {
            request.getSession().removeAttribute("user");
            request.getSession().removeAttribute("auth");
            request.getSession().removeAttribute("userError");
        }
    }
}
