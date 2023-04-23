package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.Country;
import cordinus.cordinus_global.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                userList.add(new User(userID,userName,password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

    }


    public static boolean userConfirmation(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = '"+username+"' AND Password ='"+password+"'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
       // User user = null;

        while (rs.next()) {
            if((rs.getString("User_Name").equals(username))&&(rs.getString("Password").equals(password))){
               // user.setUser_Name(username);
                //user.setPassword(password);
                rs.getInt("User_ID");
                return true;
            }
        }
        return false;
    }

    public static User getCurrentUserData(String username, String password){
        User user = null;
        try {
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String passWord = rs.getString("Password");
                user = new User(userID,userName,passWord) ;
                return user;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }




}
