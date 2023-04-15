package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

//https://wgu.webex.com/webappng/sites/wgu/recording/bf6e7b5d5d06103abd8f005056815ee6/playback
public abstract class CustomersQuery {


    //paste updated insert method here, then call it in AddCustController, and pass all values from the fxml into the method
    public static int insert( String custName, String Address, String Postal, String Phone,Timestamp CreateDate, String CreatedBy,Timestamp LastUpdate, String LastUpdatedBy, int DivID) throws SQLException {
        String sql ="INSERT INTO CUSTOMERS ( Customer_Name, Address, Postal_Code, Phone,Create_Date,Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,custName);
            ps.setString(2,Address);
            ps.setString(3,Postal);
            ps.setString(4,Phone);
            ps.setTimestamp(5,CreateDate);//setTimestamp, valueof ldt
            ps.setString(6,CreatedBy);
            ps.setTimestamp(7,LastUpdate);//setTimestamp
            ps.setString(8,LastUpdatedBy);
            ps.setInt(9,DivID);


         int rowsAffected = ps.executeUpdate();
         return rowsAffected;

    }

    public static int update(String custname,String Address,String ZipCode,String PhoneNumber,Timestamp CreateDate,String CreatedBy,Timestamp LastUpdate,String LastUpdatedBy,int DivID, int customerid) throws SQLException {
        String sql ="UPDATE CUSTOMERS SET Customer_Name = ?,Address = ?,Postal_Code= ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,custname);
        ps.setString(2,Address);
        ps.setString(3,ZipCode);
        ps.setString(4,PhoneNumber);
        ps.setTimestamp(5,CreateDate);//setTimestamp, valueof ldt stay same, should I not pass?
        ps.setString(6,CreatedBy);//stay same, should I not pass?
        ps.setTimestamp(7,LastUpdate);//grab current NOW()
        ps.setString(8,LastUpdatedBy);//grab current
        ps.setInt(9,DivID);
        ps.setInt(10,customerid);
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

    //change over
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String Email = rs.getString("Email");
                contactList.add(new Contact(contactID,contactName,Email));


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactList;

    }


}


