package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.Contact;
import cordinus.cordinus_global.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class UsersQuery {

    public static ObservableList<User> getAllUsers(){
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Users";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                LocalDateTime create_Date = rs.getTimestamp("Create_Date").toLocalDateTime();
                LocalDateTime last_Update = rs.getTimestamp("Last_Update").toLocalDateTime();
                String created_By = rs.getString("Created_By");
                String last_Updated_By = rs.getString("Last_Updated_By");


                userList.add(new User(userID,userName,password,create_Date,last_Update,created_By,last_Updated_By));


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

    }


}
