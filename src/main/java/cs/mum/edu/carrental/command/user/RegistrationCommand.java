package cs.mum.edu.carrental.command.user;

import cs.mum.edu.carrental.Model.User;
import cs.mum.edu.carrental.Model.UserErrors;
import cs.mum.edu.carrental.command.CommandTemplate;
import cs.mum.edu.carrental.dao.DaoFactory;
import cs.mum.edu.carrental.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class RegistrationCommand extends CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = getUserFromParameters(request);
        request.getSession().removeAttribute("userError");
        UserErrors userErrors = new UserErrors();
        boolean isUserValid = persistUser(user, userErrors);

        request.getSession().setAttribute("user", user);

        if (!isUserValid) {
            request.getSession().setAttribute("userError", userErrors);
            dispatcherForward(request, response, request.getRequestDispatcher("/registration.tiles"));
        } else {
            infoRedirect(request, response, "REG_SUCCESS" );
        }
    }

    private boolean persistUser(User user, UserErrors userErrors) {
        if (isUserValid(user, userErrors)) {
            try {
                UserDao daoUser = DaoFactory.getUserDao();
                daoUser.add(user);
                daoUser.findByEmail(user);
                return true;
            } catch (SQLException e) {
                userErrors.setEmail("DOUBLE_EMAIL");
                logger.warn("DBError", e);
            }
        }
        return false;
    }

    private boolean isUserValid(User user, UserErrors userErrors) {
        boolean isUserValid = true;
        if (!user.getPassword().equals(user.getPasswordCheck()) || user.getPassword().equals("")){
            isUserValid = false;
            userErrors.setPassword("INCORRECT_PASSWORD");
        }else {
            if (!user.getFirstname().matches("[A-Za-zА-Яа-я]+")){
                isUserValid = false;
                userErrors.setFirstname("BLANK_WRONG_SYMBOLS");
            }
            if (user.getFirstname().length() >= 55){
                isUserValid = false;
                userErrors.setFirstname("BAD_LENGTH");
            }
            if (!user.getLastname().matches("[A-Za-zА-Яа-я]+")){
                isUserValid = false;
                userErrors.setLastname("BLANK_WRONG_SYMBOLS");
            }
            if (user.getLastname().length() >= 55){
                isUserValid = false;
                userErrors.setLastname("BAD_LENGTH");
            }
            if (!user.getPassport().matches("[A-Za-zА-Яа-я0-9 ]+")){
                isUserValid = false;
                userErrors.setPassport("BLANK_WRONG_SYMBOLS");
            }
            if (user.getPassport().length() >= 55){
                isUserValid = false;
                userErrors.setPassport("BAD_LENGTH");
            }
            if (!user.getEmail().matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
                    "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
                isUserValid = false;
                userErrors.setEmail("WRONG_EMAIL");
            }
            if (user.getEmail().length() >= 55){
                isUserValid = false;
                userErrors.setEmail("BAD_LENGTH");
            }
        }
        return isUserValid;
    }
}
