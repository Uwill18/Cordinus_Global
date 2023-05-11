package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.Alerts;
import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.model.Contact;
import cordinus.cordinus_global.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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



    public static boolean deleteConfirmation(int custID) throws SQLException {

        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        for (Appointment appointment : allAppointments) {
            if ((appointment.getCustomer_ID() == custID)) {
                return false;
            }
        }
        return true;
    }

    //change over
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM CUSTOMERS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            /**This line filters the above sql string to select  data from specific columns, then send them to an instance of Customer
             * that appends to customerdata*/

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String divisionID = rs.getString("Division_ID");
                customerList.add(new Customer(customerID, customerName, address, postalCode, phone, divisionID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;

    }

}


