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



    //https://www.youtube.com/watch?v=tw_NXq08NUE

    @FXML
    public void CreateCustomer(ActionEvent event) throws SQLException {


        String sql ="INSERT INTO CUSTOMERS ( Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);


        //int custID = Integer.parseInt(Customer_ID.getText());
        String custname = Customer_Name.getText();
        String Address = AddressTxt.getText();
        String ZipCode = String.valueOf(Integer.parseInt(Postal_Code.getText()));
        String PhoneNumber = Phone.getText();
        int DivID = Integer.parseInt(Division_ID.getText());

        //timestamp for utc createddate and last update start as the same
        //createdby and lastupdate by user login, can be "test"/ the same
        //next steps are to implement user login, and to implement timestamps
        //modify sql statement, manually run sql statement on mysql workbench first, and embed in the code
        //e.g. (make sure order is correct:)
        ////Timestamp timestamp = UDT;





        try{
            //ps.setInt(1,custID);
            ps.setString(2,custname);
            ps.setString(3,Address);
            ps.setString(4,ZipCode);
            ps.setString(5,PhoneNumber);
            ps.setInt(5,DivID);
            // e.g. : ps.setString(6,timestamp);
            ////
            //userlogin
            //timestamp
            ps.execute();
        }catch(Exception e){

        }

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
