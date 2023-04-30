package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactsQuery {

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
