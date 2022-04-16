package jdbc.springdb1.connection;

import static jdbc.springdb1.connection.ConnectionConst.PASSWORD;
import static jdbc.springdb1.connection.ConnectionConst.URL;
import static jdbc.springdb1.connection.ConnectionConst.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBConnectionUtil {
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("connection = {}, class = {}", connection, connection.getClass());
            return connection;

        } catch (SQLException e) {
            // Checked Exception -> Unchecked Exception 변환
            throw new IllegalStateException(e);
        }
    }

    public static void close(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw new IllegalStateException(e);
        }
    }

    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        close(connection, preparedStatement);

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw new IllegalStateException(e);
        }
    }
}
