package cs.mum.edu.carrental.command.user;

import cs.mum.edu.carrental.Model.User;
import cs.mum.edu.carrental.command.CommandTemplate;
import cs.mum.edu.carrental.dao.DaoFactory;
import cs.mum.edu.carrental.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class UserInfoCommand extends CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (isAccessNotPermitted(request, response))
            return;

        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            UserDao daoUser = DaoFactory.getUserDao();
            User userChosen = daoUser.findById(userId);

            /*handle request on not existing users start*/
            if (userChosen.getEmail() == null) {
                infoRedirect(request, response, "USER_BY_ID_REQUEST_FAILED" );
                /*handle request on not existing users end*/
            } else {
                request.getSession().setAttribute("userInfo", userChosen);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user" + ".tiles");
                requestDispatcher.forward(request, response);
            }
        } catch (NumberFormatException e) {
            infoRedirect(request, response, "USER_BY_ID_REQUEST_FAILED" );
            logger.warn("User By ID request Failed", e);
        } catch (SQLException e) {
            infoRedirect(request, response, "DATABASE_PROBLEM" );
            logger.error("Forward", e);
        } catch (Exception e) {
            logger.error("Forward", e);
        }
    }
}
