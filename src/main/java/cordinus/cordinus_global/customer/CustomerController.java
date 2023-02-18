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
import java.util.logging.Level;
import java.util.logging.Logger;


//implement similar to product in that appointments associate with customers
//initiate getters and setter in CustomerModel
//source: https://www.youtube.com/watch?v=3tmz-0g3EPs&list=PLVo4QEZoUs5G88wJ2AajTIS33oFF8R5N-&index=2


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

    public void initialize() throws SQLException {
        customerdata = FXCollections.observableArrayList();
        setCellTable();
        LoadCustomers();
    }




    private void setCellTable(){

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
                customerdata.add(new CustomersList( rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e){
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE,null,e);
        }
       CustomerTable.setItems(customerdata);
    }




}
