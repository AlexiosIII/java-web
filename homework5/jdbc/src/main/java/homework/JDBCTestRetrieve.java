package homework;

import java.sql.*;

/**
 * ClassName: JDBCTestRetrieve
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: Alexios
 * @Create: 2024/10/27 - 15:06
 * @Version: v1.0
 */
public class JDBCTestRetrieve {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM teacher");
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getObject(1) + " " + rs.getObject(2)
                             + " " + rs.getObject(3)+ " " + rs.getObject(4));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
