package cordinus.cordinus_global.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

//https://wgu.webex.com/webappng/sites/wgu/recording/bf6e7b5d5d06103abd8f005056815ee6/playback
public abstract class AppointmentsQuery {

    public static int insert(String title, String description, String location, String type, Timestamp startby, Timestamp endby,Timestamp LastUpdate,String LastUpdatedBy, Timestamp CreatedDate,String CreatedBy,int customerid, int userid, int contact) throws SQLException {
        String sql ="INSERT INTO APPOINTMENTS ( Title, Description, Location, Type, Start, End,Create_Date,Created_By,Last_Update,Last_Updated_By,Customer_ID,User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setTimestamp(5,startby);//setTimestamp, valueof ldt

        ps.setTimestamp(6,endby);//setTimestamp
        ps.setTimestamp(7,CreatedDate);
        ps.setString(8,CreatedBy);
        ps.setTimestamp(9,LastUpdate);
        ps.setString(10,LastUpdatedBy);
        ps.setInt(11,customerid);
        ps.setInt(12,userid);
        ps.setInt(13,contact);

        System.out.println(ps);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    public static int update(String title, String description, String location, String type, Timestamp startby, Timestamp endby, String CreatedBy, Timestamp LastUpdate,String LastUpdatedBy, Timestamp CreatedDate,int customerid, int userid, int contact) throws SQLException {
        String sql ="UPDATE APPOINTMENTS SET Title = ?,Description = ?,Location= ?, Type = ?, Start = ?, End = ?,Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?,Create_Date = ?, User_ID = ?, Contact_ID,  WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setTimestamp(5,startby);//setTimestamp, valueof ldt

        ps.setTimestamp(6,endby);//setTimestamp
        ps.setTimestamp(7,CreatedDate);
        ps.setString(8,CreatedBy);
        ps.setTimestamp(9,LastUpdate);
        ps.setString(10,LastUpdatedBy);
        ps.setInt(11,customerid);
        ps.setInt(12,userid);
        ps.setInt(13,contact);

        //System.out.println(ps);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;


    }

    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
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

