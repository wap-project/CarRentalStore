package cs.mum.edu.carrental.dao;

import cs.mum.edu.carrental.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserDao {
    public final static Logger logger = LoggerFactory.getLogger(UserDao.class);

    final static ResourceBundle resourceBundle = ResourceBundle.getBundle("sqlstatements");
    final static String INSERT_USER = resourceBundle.getString("INSERT_USER");
    final static String FIND_USER_BY_EMAIL = resourceBundle.getString("FIND_USER_BY_EMAIL");
    final static String FIND_USER_BY_ID = resourceBundle.getString("FIND_USER_BY_ID");
    final static  String FIND_USER_WHERE_EMAIL_AND_PASSWORD = resourceBundle.getString("FIND_USER_WHERE_EMAIL_AND_PASSWORD");

    Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void add(User user) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassport());
            ps.setString(5, user.getPassword());
            ps.setBoolean(6, user.getIsAdmin());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("UserDao: ", e);
        }
    }

    public boolean findByEmail(User user) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setProps(user, rs);
                return  true;
            }
        } catch (SQLException e) {
            logger.error("UserDao: ", e);
        }

        return false;
    }

    public User findById(int userId) throws SQLException {
        User user = new User();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            setProps(user, resultSet);
        }

        return user;
    }

    public boolean findByEmailAndPassword(User user) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_USER_WHERE_EMAIL_AND_PASSWORD)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setProps(user, rs);
                return  true;
            }
        } catch (SQLException e) {
            logger.error("UserDao: ", e);
        }

        return false;
    }

    private void setProps(User user, ResultSet resultSet) throws SQLException {
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setPassport(resultSet.getString("passport"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setUserId(resultSet.getInt("user_id"));
        user.setIsAdmin(resultSet.getBoolean("is_admin"));
    }
}
