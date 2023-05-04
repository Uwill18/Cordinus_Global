package cordinus.cordinus_global.model;

import cordinus.cordinus_global.DAO.CountriesQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Customer {

    private final ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();

    private int Customer_ID;

    private String Customer_Name;

    private String Address;
    private String Postal_Code;
    private String Phone;

    private String Division_ID;//

/**Discovered that matching the order of the constructor attributes with the view counterparts ensures that
 * they are displayed properly when loaded*/
    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone, String division_ID) {
        Customer_ID = customer_ID;
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;
        Division_ID = division_ID;
    }



    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(String division_ID) {
        Division_ID = division_ID;
    }

    /**This function displays the countries to the customer table*/
    public Country getCountry(){
        return CountriesQuery.getCountryByDivision(Integer.parseInt(Division_ID));
    }

    /**applied same logic as getCountryByDivision to convert hash values to string for customer name
     * in the appointment totals table*/
    @Override
    public String toString(){
        return Customer_Name;
    }

    public void addAssociatedAppointment(Appointment newAppt){
        if(newAppt!=null){
            try{
                associatedAppointments.add(newAppt);
            }catch (RuntimeException r){
                Alerts.SelectionError();
            }
        }

    }

    public ObservableList<Appointment> getAllAssociatedAppointments(){
        return associatedAppointments;
    }

    public void deleteAssociatedAppointment(Appointment newAppt){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "If you delete this customer you must delete all associated appointments first." +
                "\nDo you wish to proceed?");
        alert.setTitle("DELETE CONFIRMATION");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()  && result.get() != ButtonType.CANCEL){
            associatedAppointments.remove(newAppt);
        }
    }

}
