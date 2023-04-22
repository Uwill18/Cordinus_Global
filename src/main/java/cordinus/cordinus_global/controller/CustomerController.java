package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.model.Alerts;
import cordinus.cordinus_global.model.Customer;
import cordinus.cordinus_global.DAO.CustomersQuery;
import cordinus.cordinus_global.DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;



//implement similar to product in that appointments associate with customers
//initiate getters and setter in CustomerModel
//todo ADD Countries to view
//todo Make sure Customer appointments get deleted before customer is deleted as well, confirmation dialog box saying to delete  appts first should happen automatically
//source: https://www.youtube.com/watch?v=3tmz-0g3EPs&list=PLVo4QEZoUs5G88wJ2AajTIS33oFF8R5N-&index=2


public class CustomerController {

    private ObservableList<Customer> customerdata;
    @FXML
    private TableColumn<?, ?> Address;

    @FXML
    private TableView<Customer> CustomerTable;

    @FXML
    private TableColumn<?, ?> Customer_ID;

    @FXML
    private TableColumn<?, ?> Customer_Name;


    @FXML
    private TableColumn<?, ?> Phone;

    @FXML
    private TableColumn<?, ?> Postal_Code;


    @FXML
    private TableColumn<?, ?> Division_ID;


    @FXML
    private TableColumn<?, ?> Countries;


    public void initialize() throws SQLException {
        customerdata = FXCollections.observableArrayList();
        LoadCustomers();
        setCustomerCellTable();
    }

    private void setCustomerCellTable(){

        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        Division_ID.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        Countries.setCellValueFactory(new PropertyValueFactory<>("Country"));
    }

    public void LoadCustomers() throws SQLException {
        customerdata = CustomersQuery.getAllCustomers();
        /**customer data is added to the CustomerTable in the view*/
       CustomerTable.setItems(customerdata);
    }

//toDO : select customer and pass data back to select/add appt for customer, add appt for
    //toDo allow a search for a customer
    //toDO:
//    --insert division id as fk
//--country object will have and id
//    Country countryid = countryid.getCountry_ID;
//
//--division object will have div id, and info
//    Division divisionid = divsionid.getDivision_ID;
//


    @FXML
    void OnAddCustomer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AddCust.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }
//
    @FXML
    void OnDeleteCustomer(ActionEvent event) throws SQLException {

        if((CustomerTable.getSelectionModel().getSelectedItem() != null)){

            try{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Warning");
                alert.setHeaderText("Deleting Customer");
                alert.setContentText("Are you sure you wish to delete this customer? "
                        + "\nCUSTOMER ID# : " + CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID()
                        +"\nCUSTOMER NAME :" + CustomerTable.getSelectionModel().getSelectedItem().getCustomer_Name());
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()  && result.get() != ButtonType.CANCEL){
                    if(result.isPresent()  && result.get() ==ButtonType.OK){
                        Alert delete_alert = new Alert(Alert.AlertType.CONFIRMATION);
                        delete_alert.setTitle("Delete Confirmation");
                        delete_alert.setHeaderText("Customer Deleted");
                        delete_alert.setContentText("THE FOLLOWING CUSTOMER HAS BEEN DELETED!" +
                                "\nCUSTOMER ID# : " + CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID()
                                +"\nCUSTOMER NAME : "
                                + CustomerTable.getSelectionModel().getSelectedItem().getCustomer_Name());
                        Optional<ButtonType> delete_result = delete_alert.showAndWait();
                        CustomersQuery.delete(CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID());
                    }
                }
            }catch(NullPointerException e){
               Alerts.SelectionError();
            }
        }else{
            Alerts.SelectionError();
        }
    }
//
    @FXML
    void OnUpdateCustomer(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/ModCust.fxml"));
            loader.load();
            ModCustController modCustController = loader.getController();//get controller tied to view
            modCustController.Customer_Passer(CustomerTable.getSelectionModel().getSelectedIndex(),CustomerTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Modify Customer");
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }catch (NullPointerException e){
            Alerts.SelectionError();
        }
    }

    @FXML
    void MainMenuReturn(ActionEvent event) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void AddAppointment(ActionEvent event) throws IOException {

        try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/AddAppt.fxml"));
            loader.load();
            AddApptController addApptController = loader.getController();
            addApptController.Customer_Passer(CustomerTable.getSelectionModel().getSelectedIndex(),CustomerTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Add Appointments");
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }catch (NullPointerException e){
            Alerts.SelectionError();;
        }


    }

    @FXML
    void UpdateAppointment(ActionEvent event) throws IOException {

        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/ModAppt.fxml"));
        loader.load();
        ModifyApptController modifyApptController = loader.getController();
        modifyApptController.Customer_Passer(CustomerTable.getSelectionModel().getSelectedIndex(),CustomerTable.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Update Appointments");
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

        }catch (NullPointerException e){
            Alerts.SelectionError();
        }

    }

//Customer Controller must select Data from the TableView and pass
// it ModifyCust


}
