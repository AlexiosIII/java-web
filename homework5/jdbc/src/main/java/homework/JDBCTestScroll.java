package homework;

import java.sql.*;

/**
 * ClassName: JDBCTestScroll
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: Alexios
 * @Create: 2024/10/27 - 16:07
 * @Version: v1.0
 */
public class JDBCTestScroll {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";
        String sql = "SELECT * FROM teacher ";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                rs.absolute(-2);
                System.out.println(rs.getObject(1) + " " + rs.getObject(2)
                        + " " + rs.getObject(3)+ " " + rs.getObject(4));
            }catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
