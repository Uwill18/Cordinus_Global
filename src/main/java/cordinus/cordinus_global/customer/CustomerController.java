package cordinus.cordinus_global.customer;

import cordinus.cordinus_global.controller.MainController;
import cordinus.cordinus_global.controller.ModCustController;
import cordinus.cordinus_global.helper.CustomersQuery;
import cordinus.cordinus_global.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
        Countries.setCellValueFactory(new PropertyValueFactory<>("Countries"));
    }

    public void LoadCustomers() throws SQLException {
        //CustomersQuery.select();
        try{

            String sql = "SELECT * FROM CUSTOMERS ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                /**This line filters the above sql string to select  data from specific columns, then send them to an instance of Customer
                 * that appends to customerdata*/
                customerdata.add(new Customer( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(10)));
            }
        } catch (SQLException e){
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE,null,e);
        }
        /**customer data is added to the CustomerTable in the view*/
       CustomerTable.setItems(customerdata);
    }

//toDO : select customer and pass data back to select/add appt for customer, add appt for
    //toDo allow a search for a customer



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


        CustomersQuery.delete(CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID());

    }
//
    @FXML
    void OnUpdateCustomer(ActionEvent event) throws IOException {
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

//Customer Controller must select Data from the TableView and pass
// it ModifyCust


}
