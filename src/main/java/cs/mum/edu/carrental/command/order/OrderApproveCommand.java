package cs.mum.edu.carrental.command.order;

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

public class OrderApproveCommand extends CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(OrderApproveCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().removeAttribute("error");
            int orderId = Integer.parseInt(request.getParameter("id"));
            OrderDao orderDao = DaoFactory.getOrderDao();
            if (orderDao.changeStatus(orderId, "approved")) {
                Order order = orderDao.getDataById(orderId);
                request.getSession().setAttribute("order", order);
            } else {
                request.getSession().setAttribute("error", "DATABASE_PROBLEM");
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "DATABASE_PROBLEM");
            logger.error("DBError: ", e);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/order.tiles");
        dispatcherForward(request, response, requestDispatcher);
    }
}