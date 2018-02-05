package cs.mum.edu.carrental.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DbInitDestroyDao {
    Connection connection;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("basecreation");

    public DbInitDestroyDao() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void initDB() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(resourceBundle.getString("CLASSES_CREATE"));
        statement.executeUpdate(resourceBundle.getString("BRANDS_CREATE"));
        statement.executeUpdate(resourceBundle.getString("MODELS_CREATE"));
        statement.executeUpdate(resourceBundle.getString("CARS_CREATE"));
        statement.executeUpdate(resourceBundle.getString("USER_CREATE"));
        statement.executeUpdate(resourceBundle.getString("ORDER_CREATE"));
        statement.executeUpdate(resourceBundle.getString("BRANDS_INSERT"));
        statement.executeUpdate(resourceBundle.getString("CLASSES_INSERT"));
        statement.executeUpdate(resourceBundle.getString("MODELS_INSERT"));
        statement.executeUpdate(resourceBundle.getString("CARS_INSERT"));
        statement.executeUpdate(resourceBundle.getString("USERS_INSERT"));
        statement.executeUpdate(resourceBundle.getString("ORDERS_INSERT"));
    }

    public void destroyDB() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(resourceBundle.getString("DROP_ORDERS"));
        statement.executeUpdate(resourceBundle.getString("DROP_USERS"));
        statement.executeUpdate(resourceBundle.getString("DROP_CARS"));
        statement.executeUpdate(resourceBundle.getString("DROP_MODELS"));
        statement.executeUpdate(resourceBundle.getString("DROP_CLASSES"));
        statement.executeUpdate(resourceBundle.getString("DROP_BRANDS"));
    }

}
