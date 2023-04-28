package cordinus.cordinus_global.model;

import cordinus.cordinus_global.DAO.UsersQuery;

public class User {
    int User_ID;
    String User_Name;
    String Password;

    public User(int user_ID, String user_Name, String password) {
        User_ID = user_ID;
        User_Name = user_Name;
        Password = password;

    }


    public int getUser_ID() {
        return User_ID;
    }

    public int setUser_ID(int user_ID) {
        User_ID = user_ID;
        return user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public String setUser_Name(String user_Name) {
        User_Name = user_Name;
        return user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public String setPassword(String password) {
        Password = password;
        return password;
    }

    public User getUserData(User user){
        User currentUser = user;
        //user = UsersQuery.setCurrentUserData(user.getUser_Name(),user.getPassword());
        return currentUser;
    }

}
