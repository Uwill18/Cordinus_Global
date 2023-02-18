package cordinus.cordinus_global.customer;

import cordinus.cordinus_global.helper.CustomersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



//implement similar to product in that appointments associate with customers
//initate getters and setter
public class CustomerController {



    private Connection con = null;//not sure if I need this...

    private PreparedStatement pst = null;//or this..
    private ResultSet rs = null;
    private static ObservableList<CustomersList> customerdata;
    @FXML
    private TableColumn<?, ?> Address_Column;

    @FXML
    private TableView<CustomersList> CustomerTable;

    @FXML
    private static TableColumn<?, ?> ID_Column;

    @FXML
    private static TableColumn<?, ?> Name_Column;

    @FXML
    private static TableColumn<?, ?> Phone_Column;

    @FXML
    private static TableColumn<?, ?> Postal_Column;

    @FXML
    void OnAddCustomer(ActionEvent event) {

    }

    @FXML
    void OnDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void OnUpdateCustomer(ActionEvent event) {

    }

    public static void initialize(){
        customerdata = FXCollections.observableArrayList();
        setCellTable();
    }




    private static void setCellTable(){

        ID_Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name_Column.setCellValueFactory(new PropertyValueFactory<>("name"));
        Postal_Column.setCellValueFactory(new PropertyValueFactory<>("postal code"));
        Phone_Column.setCellValueFactory(new PropertyValueFactory<>("phone number"));
    }

    public void LoadCustomers() throws SQLException {
        //CustomersQuery.select();
        try{
            pst = con.prepareStatement("Select * from Customers");
            rs = pst.executeQuery();
            while(rs.next()){
                customerdata.add(new CustomersList( ID, Name, Address, PostalCode, PhoneNumber));
            }
        }

    }




}
