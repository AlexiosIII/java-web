package homework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ClassName: JDBCTestButch
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: Alexios
 * @Create: 2024/10/27 - 15:53
 * @Version: v1.0
 */
public class JDBCTestButch {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";
        String sql = "INSERT INTO teacher (id,name,course,birthday) VALUES (?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql);) {
                for (int i = 1; i <= 500; i++) {
                    ps.setInt(1, i);
                    ps.setString(2, ""+i);
                    ps.setString(3, "高等数学");
                    ps.setString(4, "2000/1/1");
                    ps.addBatch();
                    if (i % 100 == 0) {
                        ps.executeBatch();
                        conn.commit();
                        ps.clearBatch();
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
