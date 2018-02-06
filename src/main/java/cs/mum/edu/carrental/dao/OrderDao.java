package cs.mum.edu.carrental.dao;

import cs.mum.edu.carrental.Model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderDao.class);

    Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    final static ResourceBundle sqlResourceBundle = ResourceBundle.getBundle("sqlstatements");

    final static String MY_ORDERS_REQUESTS_SELECT = sqlResourceBundle.getString("MY_ORDERS_REQUESTS_SELECT");
    final static String ALL_ORDERS_REQUESTS_SELECT = sqlResourceBundle.getString("ALL_ORDERS_REQUESTS_SELECT");
    final static String ORDERS_REQUESTS_SELECT_BY_ID = sqlResourceBundle.getString("ORDERS_REQUESTS_SELECT_BY_ID");
    final static String UPDATE_ORDER_STATUS = sqlResourceBundle.getString("UPDATE_ORDER_STATUS");
    final static String UPDATE_ORDER_REASON = sqlResourceBundle.getString("UPDATE_ORDER_REASON");
    final static String UPDATE_ORDER_PENALTY = sqlResourceBundle.getString("UPDATE_ORDER_PENALTY");

    public boolean changePenalty(int orderId, double penalty) {
        boolean result = false;
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_PENALTY)) {
            ps.setDouble(1, penalty);
            ps.setInt(2, orderId);
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("OrderDao: ", e);
        }

        return result;
    }

    public boolean changeReason(int orderId, String reason) {
        boolean result = false;
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_REASON)) {
            ps.setString(1, reason);
            ps.setInt(2, orderId);
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("OrderDao: ", e);
        }

        return result;
    }

    public boolean changeStatus(int orderId, String status) {
        boolean result = false;
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("OrderDao: ", e);
        }

        return result;
    }

    public Order getDataById(int orderId) {
        Order order = new Order();
        try (PreparedStatement ps = connection.prepareStatement(ORDERS_REQUESTS_SELECT_BY_ID)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = setProps(rs);
            }

        } catch (SQLException e) {
            logger.error("OrderDao: ", e);
        }

        return order;
    }

    public List<Order> getByUserIdAndStatus(int userId, String status) throws SQLException {
        List<Order> orders = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(MY_ORDERS_REQUESTS_SELECT);
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, status);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Order order = setProps(resultSet);
            orders.add(order);
        }

        return orders;
    }

    public List<Order> getAllData(String status) throws SQLException {
        List<Order> orders = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(ALL_ORDERS_REQUESTS_SELECT);
        preparedStatement.setString(1, status);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Order order = setProps(resultSet);
            orders.add(order);
        }

        return orders;
    }

    public Order setProps(ResultSet resultSet) throws SQLException {
        Order order = new Order();

        order.setBrand(resultSet.getString("brand_name"));
        order.setModel(resultSet.getString("model_name"));
        order.setRentTotal(resultSet.getDouble("rent_total"));
        order.setPenalty(resultSet.getDouble("penalty"));
        order.setReason(resultSet.getString("reason"));
        order.setStatus(resultSet.getString("status"));
        order.setDateStart(resultSet.getDate("date_start"));
        order.setDateEnd(resultSet.getDate("date_end"));
        order.setCarId(resultSet.getInt("car_id"));
        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserId(resultSet.getInt("user_id"));

        return order;
    }

}
