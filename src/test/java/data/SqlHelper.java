package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlHelper {

    private static final QueryRunner runner = new QueryRunner();
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");
    private static Connection connection;

    @SneakyThrows
    public static void getConnection() {
        connection = DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static void deleteDataBase() {
        getConnection();

        runner.update(connection, "DELETE FROM payment_entity");
        runner.update(connection, "DELETE FROM order_entity");
        runner.update(connection, "DELETE FROM credit_request_entity");
    }

    @SneakyThrows
    public static String getPayStatus() {
        String codeSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        getConnection();
        return runner.query(connection, codeSql, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        String codeSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        getConnection();
        return runner.query(connection, codeSql, new ScalarHandler<String>());

    }
}
