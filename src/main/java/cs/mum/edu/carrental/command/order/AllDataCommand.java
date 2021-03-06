package cs.mum.edu.carrental.command.order;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import cs.mum.edu.carrental.Model.Order;
import cs.mum.edu.carrental.command.CommandTemplate;
import cs.mum.edu.carrental.dao.DaoFactory;
import cs.mum.edu.carrental.dao.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class AllDataCommand extends CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(AllDataCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (isAccessNotPermitted(request, response)) {
            return;
        }

        try {
            OrderDao oDao = DaoFactory.getOrderDao();
            String path = request.getServletPath();
            String status = null;
            switch (path) {
                case "/all_new_orders":
                    request.getSession().setAttribute("message", "ALL_NEW_ORDERS");
                    status = "new";
                    break;
                case "/all_approved_orders":
                    request.getSession().setAttribute("message", "ALL_APPROVED_ORDERS");
                    status = "approved";
                    break;
                case "/all_rejected_orders":
                    request.getSession().setAttribute("message", "ALL_REJECTED_ORDERS");
                    status = "rejected";
                    break;
                case "/all_closed_orders":
                    request.getSession().setAttribute("message", "ALL_REJECTED_ORDERS");
                    status = "closed";
                    break;
            }
            List<Order> orders = oDao.getAllData(status);
            request.getSession().setAttribute("orders", orders);
        } catch (SQLException e) {
            logger.error("DBError: ", e);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getServletPath() + ".tiles");
        dispatcherForward(request, response, requestDispatcher);

    }

}
