package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.Country;
import cordinus.cordinus_global.model.CustomerSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerScheduleQuery {

    public static ObservableList<CustomerSchedule> getAllSchedules(){
        ObservableList<CustomerSchedule> scheduleList = FXCollections.observableArrayList();


        try {
            String sql = "SELECT Appointment_ID, Customer_ID, Start, End  FROM client_schedule.appointments ;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int customerId = rs.getInt("Customer_ID");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                scheduleList.add(new CustomerSchedule(appointmentId,customerId,start,end));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scheduleList;

    }
    public static CustomerSchedule getScheduleData(){
        CustomerSchedule customerSchedule = null;
        try {
            String sql = "SELECT Appointment_ID, Customer_ID, Start, End  FROM client_schedule.appointments ;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int customerId = rs.getInt("Customer_ID");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                customerSchedule = new CustomerSchedule(appointmentId,customerId,start,end);
                return customerSchedule;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
