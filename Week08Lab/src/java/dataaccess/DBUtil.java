package dataaccess;

import java.sql.*;

/**
 * Class that helps us close PreparedStatements and ResultSets
 */
public class DBUtil {
    
    /**
     * Closes a PreparedStatement used on a database
     * @param preparedStatement the PreparedStatement to be closed
     */
    public static void closePreparedStatement(Statement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Close a ResultSet after it's usefulness is through
     * @param resultSet the ResultSet to be closed
     */
    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
