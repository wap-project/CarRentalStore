package cs.mum.edu.carrental.command;

import cs.mum.edu.carrental.Model.User;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandTemplate implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CommandTemplate.class);

    public void dispatcherForward(HttpServletRequest request, HttpServletResponse response, RequestDispatcher requestDispatcher) {
        try {
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("Forward", e);
        }
    }


    public void infoRedirect(HttpServletRequest request, HttpServletResponse response, String message){
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/info.tiles");
        request.setAttribute("info", message);
        dispatcherForward(request, response, requestDispatcher);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        infoRedirect(request, response, "BAD_COMMAND" );
    }

    public User getUserFromParameters(HttpServletRequest request) {
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (ReflectiveOperationException e) {
            logger.error("BeanUtilsError", e);
        }
        return user;
    }

    public boolean isAccessNotPermitted(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !user.getIsAdmin() ) {
            infoRedirect(request, response, "LOG_IN_WARN");
            return true;
        }
        return false;
    }

    public RequestDispatcher getSamePageDispatcher(HttpServletRequest request) {
        String[] path = request.getServletPath().split("/");
        if (path.length < 2)
            return request.getRequestDispatcher("/index" +".tiles");
        return request.getRequestDispatcher("/"+ path[1] +".tiles");
    }

}
