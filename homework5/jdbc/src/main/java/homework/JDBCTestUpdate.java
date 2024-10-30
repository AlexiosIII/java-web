package homework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ClassName: JDBCTestUpdate
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: Alexios
 * @Create: 2024/10/27 - 15:41
 * @Version: v1.0
 */
public class JDBCTestUpdate {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";
        String sql = "UPDATE teacher SET name = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, "b");
                ps.setInt(2, 1);
                ps.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
