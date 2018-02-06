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

public class OrderRejectCommand extends CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(OrderRejectCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().removeAttribute("error");
            int orderId = Integer.parseInt(request.getParameter("id"));
            String reason = request.getParameter("reason");
            OrderDao orderDao = DaoFactory.getOrderDao();
            if (orderDao.changeReason(orderId, reason)) {
                Order order = orderDao.getDataById(orderId);
                request.getSession().setAttribute("order", order);
            } else {
                request.getSession().setAttribute("error", "DATABASE_PROBLEM");
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/order.tiles");
            dispatcherForward(request, response, requestDispatcher);
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "DATABASE_PROBLEM");
            logger.error("DBError: ", e);
        }

    }
}
