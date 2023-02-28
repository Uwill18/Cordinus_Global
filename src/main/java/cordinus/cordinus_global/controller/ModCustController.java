package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.customer.CustomersList;
import cordinus.cordinus_global.helper.CustomersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModCustController {
    //when edit an existing customer, you have to update lastupdate
    //by new login user
    //only change lastupdate and lastupdateby
    //**CreatedBy and CreateDate are NOT to be modified,
    //https://www.youtube.com/watch?v=1AmIKxHbLJo
    //https://www.youtube.com/watch?v=mY54KZkuZPQ


        @FXML
        private TextField AddressTxt;

        @FXML
        private TextField Customer_ID;

        @FXML
        private TextField Customer_Name;

        @FXML
        private TextField Division_ID;

        @FXML
        private TextField Phone;

        @FXML
        private TextField Postal_Code;

        private CustomersList customer;

        private int index;


        @FXML
        void CustomerScreenButton(ActionEvent event) throws IOException {

                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/CustomersScreen.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Customers");
                stage.setScene(scene);
                stage.show();

        }

        @FXML
        void ModifyCustomer(ActionEvent event) throws SQLException {
                int customerid = Integer.parseInt(Customer_ID.getText());
                String custname = Customer_Name.getText();
                String Address = AddressTxt.getText();
                String ZipCode = Postal_Code.getText();
                String PhoneNumber = Phone.getText();

                Date date = new Date();//use LocalDateTime
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());

                Timestamp CreateDate = timestamp;
                Timestamp LastUpdate = timestamp;
                String CreatedBy ="test";

                String LastUpdatedBy ="test";
                int DivID = Integer.parseInt(Division_ID.getText());
                System.out.print( CreateDate);
                CustomersQuery.update(custname, Address, ZipCode, PhoneNumber, CreateDate, CreatedBy, LastUpdate, LastUpdatedBy, DivID, customerid);

        }


        //ModifyCust must autopop selected data from customercontroller
        //allow editing of select fields, then pass that data back to the db
        //afterwards both the database and the app should hold an updated customers view

        public void Customer_Passer(int index, CustomersList customer){
                this.customer = customer;
                this.index = index;

                Customer_ID.setText((String.valueOf(customer.getCustomer_ID())));
                Customer_Name.setText(customer.getCustomer_Name());
                AddressTxt.setText(customer.getAddress());
                Postal_Code.setText(customer.getPostal_Code());
                Phone.setText(customer.getPhone());
                Division_ID.setText((String.valueOf(customer.getDivision_ID())));

        }





}
