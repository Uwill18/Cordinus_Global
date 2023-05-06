package cordinus.cordinus_global.model;


/**Used standard variables according to the database and functional requisites Initialed constructors, getters, and setters according to my initial understanding of the data types.
 * In the future I would do more UML diagramming to align myself better with best practices */

public class Contact {

    int Contact_ID;
    String Contact_Name;
    String Email;

    public Contact(int contact_ID, String contact_Name, String email) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
        Email = email;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getContact_Name() {
        return Contact_Name;
    }

    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    /** https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=0bbf1823-b54b-4e02-8814-ab8a00019993
     * 14 minutes, class and hash code of object are displayed. To fix this, we override the existing toString method.*/
    @Override
    public String toString(){
        return getContact_Name();
    }
}
