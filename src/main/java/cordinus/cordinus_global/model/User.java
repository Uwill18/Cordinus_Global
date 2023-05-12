package cordinus.cordinus_global.model;

import cordinus.cordinus_global.DAO.UsersQuery;

/**Used standard variables according to the database and functional requisites Initialed constructors, getters, and setters according to my initial understanding of the data types.
 * In the future I would do more UML diagramming to align myself better with best practices */

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


    /**I may experiment with functions below in the future to properly
     * manipulate user data across the application*/
//
//    public String setPassword(String password) {
//        Password = password;
//        return password;
//    }
//
    @Override
    public String toString(){
        return User_Name;
    }

}
