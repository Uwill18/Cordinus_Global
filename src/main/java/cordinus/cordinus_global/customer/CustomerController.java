package cordinus.cordinus_global.customer;

import cordinus.cordinus_global.helper.CustomersQuery;
import cordinus.cordinus_global.helper.JDBC;
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
    //private ResultSet rs = null;
    private ObservableList<CustomersList> customerdata;
    @FXML
    private TableColumn<?, ?> Address;

    @FXML
    private TableView<CustomersList> CustomerTable;

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

//    @FXML
//    void OnAddCustomer(ActionEvent event) {
//
//    }
//
//    @FXML
//    void OnDeleteCustomer(ActionEvent event) {
//
//    }
//
//    @FXML
//    void OnUpdateCustomer(ActionEvent event) {
//
//    }

    public void initialize() throws SQLException {
        customerdata = FXCollections.observableArrayList();
        LoadCustomers();
        setCellTable();

    }




    private void setCellTable(){

        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        Division_ID.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
    }

    public void LoadCustomers() throws SQLException {
        //CustomersQuery.select();
        try{
//            pst = con.prepareStatement("Select * from Customers");
//            rs = pst.executeQuery();
            String sql = "SELECT * FROM CUSTOMERS ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                customerdata.add(new CustomersList( rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(10)));
            }
        } catch (SQLException e){
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE,null,e);
        }
       CustomerTable.setItems(customerdata);
    }




}
