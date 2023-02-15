package cordinus.cordinus_global.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//https://wgu.webex.com/webappng/sites/wgu/recording/bf6e7b5d5d06103abd8f005056815ee6/playback
public abstract class AppointmentsQuery {

    public static int insert(String fruitName, int colorId) throws SQLException {
        String sql ="INSERT INTO FRUITS (Fruit_Name, Color_ID) VALUES(?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,fruitName);
        ps.setInt(2,colorId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int fruitId,String fruitName) throws SQLException {
        String sql ="UPDATE FRUITS SET Fruit_Name = ? WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,fruitName);
        ps.setInt(2,fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;


    }

    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM client_schedule.customers ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerId = rs.getInt( "Customer_ID");//retrieve data from result set
            String customerName = rs.getString("Customer_Name");
            System.out.print(customerId + " | " );
            System.out.print(customerName+ "\n" );
        }
    }

    public static void select(int colorId) throws SQLException {
        String sql = "SELECT * FROM FRUITS WHERE Color_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,colorId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int fruitId = rs.getInt( "Fruit_ID");//retrieve data from result set
            String fruitName = rs.getString("Fruit_Name");
            int colorIdFk = rs.getInt("Color_ID");
            System.out.print(fruitId + " | " );
            System.out.print(fruitName + "  |   " );
            System.out.print(colorIdFk + "\n" );
        }
    }
}

