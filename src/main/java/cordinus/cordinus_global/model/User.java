package cordinus.cordinus_global.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
    int User_ID;
    String User_Name;
    String Password;


    LocalDateTime Create_Date;

    LocalDateTime Last_Update;


    String Created_By;

    String Last_Updated_By;

    public User(int user_ID, String user_Name, String password, LocalDateTime create_Date, LocalDateTime last_Update, String created_By, String last_Updated_By) {
        User_ID = user_ID;
        User_Name = user_Name;
        Password = password;
        Create_Date = create_Date;
        Last_Update = last_Update;
        Created_By = created_By;
        Last_Updated_By = last_Updated_By;
    }


    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }
}
