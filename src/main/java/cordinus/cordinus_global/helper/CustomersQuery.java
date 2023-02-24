package cordinus.cordinus_global.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//https://wgu.webex.com/webappng/sites/wgu/recording/bf6e7b5d5d06103abd8f005056815ee6/playback
public abstract class CustomersQuery {


    //paste updated insert method here, then call it in AddCustController, and pass all values from the fxml into the method
    public static int insert( String custName, String Address, String Postal,String Phone,String CreateDate,String CreatedBy,String LastUpdate,String LastUpdatedBy, int DivID) throws SQLException {
        String sql ="INSERT INTO CUSTOMERS ( Customer_Name, Address, Postal_Code, Phone,Create_Date,Created_By, Last_Update, Last_Updated, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setString(1,fruitName);
//        ps.setInt(2,colorId);

        try{
            //ps.setInt(1,custID);//.size()+1 ?? //figure out how to autoincrement the id field for customer
            ps.setString(1,custName);
            ps.setString(2,Address);
            ps.setString(3,Postal);
            ps.setString(4,Phone);
            ps.setString(5,CreateDate);
            ps.setString(6,CreatedBy);
            ps.setString(7,LastUpdate);
            ps.setString(8,LastUpdatedBy);
            ps.setInt(9,DivID);

            //userlogin
            //timestamp
            //ps.execute();
        }catch(Exception e){
            System.out.println("Exception");
        }
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

    public static int delete(int fruitId) throws SQLException {
        String sql = "DELETE FROM FRUITS WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            int customerId = rs.getInt( "CUSTOMER_ID");//retrieve data from result set
//            String customerName = rs.getString("CUSTOMER_Name");
//            System.out.print(customerId + " | " );
//            System.out.print(customerName + "\n" );
//        }

    }

//    public static void select(int colorId) throws SQLException {
//        String sql = "SELECT * FROM FRUITS WHERE Color_ID = ?";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setInt(1,colorId);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            int fruitId = rs.getInt( "Fruit_ID");//retrieve data from result set
//            String fruitName = rs.getString("Fruit_Name");
//            int colorIdFk = rs.getInt("Color_ID");
//            System.out.print(fruitId + " | " );
//            System.out.print(fruitName + "  |   " );
//            System.out.print(colorIdFk + "\n" );
//        }
//    }
}


