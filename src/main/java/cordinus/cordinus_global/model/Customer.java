package cordinus.cordinus_global.model;

import cordinus.cordinus_global.DAO.CountriesQuery;

public class Customer {



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

    @Override
    public String toString(){
        return Customer_Name;
    }

}
