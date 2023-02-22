package cordinus.cordinus_global.controller;


import cordinus.cordinus_global.customer.CustomerController;
import cordinus.cordinus_global.customer.CustomersList;
import cordinus.cordinus_global.helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0
public class AddCustController {


    private ObservableList<CustomersList> customerdata;

    @FXML
    private TextField AddressTxt;

    @FXML
    private Label AddressLbl;

    @FXML
    private Label CustomerID_Lbl;

    @FXML
    private Label CustomerNameLbl;

    @FXML
    private TextField Customer_ID;

    @FXML
    private TextField Customer_Name;

    @FXML
    private Label DivisionID_Lbl;

    @FXML
    private TextField Division_ID;

    @FXML
    private TextField Phone;

    @FXML
    private Label PhoneLbl;

    @FXML
    private Label PostalCodeLbl;

    @FXML
    private TextField Postal_Code;



    @FXML
    void CreateCustomer(ActionEvent event) {

        int custID = Integer.parseInt(Customer_ID.getText());
        String custname = Customer_Name.getText();
        String Address = AddressTxt.getText();
        String ZipCode = String.valueOf(Integer.parseInt(Postal_Code.getText()));
        String PhoneNumber = Phone.getText();
        int DivID = Integer.parseInt(Division_ID.getText());


        try{

            String sql ="INSERT INTO CUSTOMERS ( Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int rowsAffected = ps.executeUpdate();
                return rowsAffected;
                //customerdata.add(new CustomersList( rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(10)));

            }
        } catch (SQLException e){
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE,null,e);
        }
        /**customer data is added to the CustomerTable in the view*/
        //CustomerTable.setItems(customerdata);


    }

    @FXML
    public void CustomerScreenButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/CustomersScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }



}
