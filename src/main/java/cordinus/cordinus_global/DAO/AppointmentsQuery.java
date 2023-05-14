package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

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


        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }
//remove create date & created by
    public static int update(String title, String description, String location, String type, Timestamp startby, Timestamp endby, Timestamp LastUpdate,String LastUpdatedBy, int userid, int contact,int customerid, int appointmentID) throws SQLException {
        String sql ="UPDATE APPOINTMENTS SET Title = ?,Description = ?,Location= ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, User_ID = ?, Contact_ID = ?, Customer_ID = ?  WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setTimestamp(5,startby);//setTimestamp, valueof ldt
        ps.setTimestamp(6,endby);//setTimestamp
        ps.setTimestamp(7,LastUpdate);
        ps.setString(8,LastUpdatedBy);
        ps.setInt(9,userid);
        ps.setInt(10,contact);
        ps.setInt(11,customerid);
        ps.setInt(12,appointmentID);
        //System.out.println(ps);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    //toDO:implementing customer compare
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**This line filters the below sql string to select  data from specific columns, then sends them to an instance of Appointment
     * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates. Similar operations
     * are implemented in this file to condense code*/
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String contactID = rs.getString("Contact_ID");
                String description = rs.getString("Description");
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                String location = rs.getString("Location");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String customerID = rs.getString("Customer_ID");
                String userID = rs.getString("User_ID");
                appointmentList.add(new Appointment(appointmentID, title, description, location, contactID, type, start, end, customerID, userID ));
                //getCustomerByID(Integer.parseInt(customerID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentList;
    }


    public static ObservableList<Appointment> getMonthAppointments(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM APPOINTMENTS WHERE MONTH(Start) = month(now())";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String contactID = rs.getString("Contact_ID");
                String description = rs.getString("Description");
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                String location = rs.getString("Location");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String customerID = rs.getString("Customer_ID");
                String userID = rs.getString("User_ID");
                appointmentList.add(new Appointment(appointmentID, title, description, location, contactID, type, start, end, customerID, userID ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentList;
    }

    public static ObservableList<Appointment> getWeekAppointments(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM APPOINTMENTS WHERE Start >= current_date() AND Start <= date_add(current_date(),interval 7 day)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String contactID = rs.getString("Contact_ID");
                String description = rs.getString("Description");
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                String location = rs.getString("Location");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String customerID = rs.getString("Customer_ID");
                String userID = rs.getString("User_ID");
                appointmentList.add(new Appointment(appointmentID, title, description, location, contactID, type, start, end, customerID, userID ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentList;
    }



    public static ObservableList<Appointment> getDayAppointments(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM APPOINTMENTS WHERE Start >= current_date() AND Start <= date_add(current_date(),interval 1 day)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String contactID = rs.getString("Contact_ID");
                String description = rs.getString("Description");
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                String location = rs.getString("Location");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String customerID = rs.getString("Customer_ID");
                String userID = rs.getString("User_ID");
                appointmentList.add(new Appointment(appointmentID, title, description, location, contactID, type, start, end, customerID, userID ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentList;
    }



    /**applied same logic as getCountryByDivision and performed a join, and returned the object from the query*/
    public static Customer getCustomerByID(int customerID){
        try {
            String sql = "SELECT * FROM APPOINTMENTS AS A INNER JOIN CUSTOMERS AS C ON C.Customer_ID = A.Customer_ID AND C.Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            Customer customer = null;
            while (rs.next()) {
                //int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String divisionID = rs.getString("Division_ID");
                customer = new Customer(customerID,customerName,address,postalCode,phone,divisionID);
                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static int getAppointmentByTypeMonth(String type, int month){
        try {
            String sql = "SELECT COUNT(*) AS Result FROM client_schedule.appointments WHERE TYPE=? AND month(START)=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, type);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("Result");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    public static ObservableList<Appointment> getTotalAppointments(String totaltype, int month){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM APPOINTMENTS WHERE TYPE=? AND month(START)=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, totaltype);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String contactID = rs.getString("Contact_ID");
                String description = rs.getString("Description");
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                String location = rs.getString("Location");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String customerID = rs.getString("Customer_ID");
                String userID = rs.getString("User_ID");
                appointmentList.add(new Appointment(appointmentID, title, description, location, contactID, type, start, end, customerID, userID ));
                //getCustomerByID(Integer.parseInt(customerID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentList;
    }

    /**https://learnsql.com/cookbook/how-to-calculate-the-difference-between-two-timestamps-in-mysql/#:~:text=To%20calculate%20the%20difference%20between%20the%20timestamps%20in%20MySQL%2C%20use,have%20done%20here%2C%20choose%20SECOND%20.*/
    /**https://learnsql.com/cookbook/how-to-sum-values-of-a-column-in-sql/#:~:text=The%20aggregate%20function%20SUM%20is,all%20records%20in%20the%20table.*/
    public static int getTimeSum(){
        try {
            String sql = "SELECT SUM(TIMESTAMPDIFF(MINUTE, Start, End)) AS Difference_Total\n" +
                    "FROM client_schedule.appointments WHERE Start >= current_date() AND Start <= date_add(current_date(),interval 1 day)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("Difference_Total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**This observablelist method is a lambda expression that filters customer ids
     * through comparing the @custid parameter to appointment object's getCustomer_ID()
     * method call. The results are stored in a collection of type observableArrayList.*/
    public static ObservableList<Appointment> getCustomerAppointments(int custid){
        ObservableList<Appointment> list = getAllAppointments().stream()
                .filter(appointment -> appointment.getCustomer_ID()==custid)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }




    public static Contact getContactByID (int contactID){
       Contact contact = null;
        try {
            String sql = "SELECT * FROM Contacts AS C INNER JOIN Appointments AS A ON C.Contact_ID = A.Contact_ID AND A.Contact_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contact_ID = rs.getInt("Contact_ID");
                String contactname = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                contact= new Contact(contact_ID,contactname,email);
                return contact;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static User  getUserByID(int userID){
        User user = null;
        try {
            String sql = "SELECT * FROM Users AS U INNER JOIN Appointments AS A ON U.User_ID = A.User_ID AND A.User_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int user_id = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String passWord = rs.getString("Password");
                user = new User(user_id, userName, passWord);
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}

