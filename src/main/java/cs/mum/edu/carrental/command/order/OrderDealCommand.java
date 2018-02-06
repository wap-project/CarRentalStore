package cs.mum.edu.carrental.command.order;

import com.sun.org.apache.xpath.internal.operations.Or;
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

public class OrderDealCommand extends CommandTemplate{
    private static final Logger logger = LoggerFactory.getLogger(OrderDealCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (isAccessNotPermitted(request, response)) {
            return;
        }

        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            OrderDao orderDao = DaoFactory.getOrderDao();
            Order order = orderDao.getDataById(orderId);
            if (order.getBrand() == null) {
                infoRedirect(request, response, "ORDER_BY_ID_REQUEST_FAILED");
            } else {
                request.getSession().setAttribute("order", order);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/order.tiles");
                dispatcherForward(request, response, requestDispatcher);
            }
        } catch (SQLException e) {
            infoRedirect(request, response, "DATABASE_PROBLEM");
            logger.error("DBError: ", e);
        }
    }
}
