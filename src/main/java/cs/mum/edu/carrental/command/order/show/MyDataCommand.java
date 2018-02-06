package cs.mum.edu.carrental.command.order.show;

import cs.mum.edu.carrental.Model.Order;
import cs.mum.edu.carrental.Model.User;
import cs.mum.edu.carrental.command.CommandTemplate;
import cs.mum.edu.carrental.dao.DaoFactory;
import cs.mum.edu.carrental.dao.DaoOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;


public class MyDataCommand extends CommandTemplate {

    private static final Logger logger = LoggerFactory.getLogger(MyDataCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null ) {
            infoRedirect(request, response, "LOG_IN_WARN");
            return;
        }
        try {
            DaoOrder daoOrder = DaoFactory.getDaoOrder();
            String path = request.getServletPath();
            String status = null;
            switch (path){
                case "/my_new_orders":
                    request.getSession().setAttribute("message", "MY_NEW_ORDERS");
                    status = "new";
                    break;
                case "/my_rejected_orders":
                    request.getSession().setAttribute("message", "MY_REJECTED_ORDERS");
                    status = "rejected";
                    break;
                case "/my_approved_orders":
                    request.getSession().setAttribute("message", "MY_APPROVED_ORDERS");
                    status = "approved";
                    break;
                case "/my_closed_orders":
                    request.getSession().setAttribute("message", "MY_CLOSED_ORDERS");
                    status = "closed";
                    break;
            }
            List<Order> orders = daoOrder.getByUserIdAndStatus(user.getUserId(), status);
            request.getSession().setAttribute("orders", orders);
        } catch (SQLException e) {
            logger.error("DBError", e);
        }
        dispatcherForward(request, response, request.getRequestDispatcher(request.getServletPath() + ".tiles"));
    }
    
}
