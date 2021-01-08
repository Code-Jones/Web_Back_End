package dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * This is connection pool that establishes and contain connections to the Database
 */
public class ConnectionPool {
   
    public static ConnectionPool pool = null;
    public static DataSource dataSource = null;

    /**
     * Private ConnectionPool constructor
     * Creates connection to database
     */
    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/userdb");
        } catch (NamingException e) {
            System.out.println(e);
        }
    }

    /**
     * Method that creates the ConnectionPool instance
     * @return an instance of ConnectionPool
     */
    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    /**
     * Method for getting a connection to the database
     * @return a connection to the data source
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Method that frees up a connection currently being used
     * @param c the connection to be freed
     */
    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}

