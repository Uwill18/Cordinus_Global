package cordinus.cordinus_global.customer;

public class CustomersList {



    private String ID;
    private String Name;
    private String Address;
    private String PostalCode;
    private String PhoneNumber;



    public CustomersList(String ID, String Name, String Address, String PostalCode, String PhoneNumber) {
        this.ID = ID;
        this.Name = Name;
        this.Address = Address;
        this.PostalCode = PostalCode;
        this.PhoneNumber = PhoneNumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

}
