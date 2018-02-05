package cs.mum.edu.carrental.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DaoFactory {
    private static Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    private static DataSource dataSource;
    static {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource)  initialContext.lookup("java:comp/env/jdbc/carrental");
        } catch (NamingException e) {
            logger.error("DBCreateInit", e);
        }
    }

    public static DbInitDestroyDao getDbInitDestroyDao() throws SQLException {
        DbInitDestroyDao DbInitDestroyDao = new DbInitDestroyDao();
        System.out.println(dataSource);
        DbInitDestroyDao.setConnection(dataSource.getConnection());
        return DbInitDestroyDao;
    }
}
