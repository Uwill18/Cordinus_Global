package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.model.Alerts;
import cordinus.cordinus_global.model.Customer;
import cordinus.cordinus_global.DAO.CustomersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


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

    @FXML
    private Button addApptBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Label customersLbl;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button updateApptBtn;

    @FXML
    private Button updateBtn;

    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");

    public void initialize() throws SQLException {
        customerdata = FXCollections.observableArrayList();
        loadCustomers();
        customersLbl.setText(rb.getString("CUSTOMERS"));
        Customer_ID.setText(rb.getString("CustomerID"));
        Customer_Name.setText(rb.getString("CustomerName"));
        Postal_Code.setText(rb.getString("Postal"));
        Phone.setText(rb.getString("Phone"));
        Division_ID.setText(rb.getString("DivisionID"));
        Countries.setText(rb.getString("Countries"));
        addBtn.setText(rb.getString("addCust"));
        updateBtn.setText(rb.getString("modCust"));
        addApptBtn.setText(rb.getString("addAppt"));
        updateApptBtn.setText(rb.getString("modAppt"));
        deleteBtn.setText(rb.getString("DELETE"));
        backBtn.setText(rb.getString("BACK"));





        setCustomerCellTable();
    }

    private void setCustomerCellTable() {
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        Division_ID.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        Countries.setCellValueFactory(new PropertyValueFactory<>("Country"));
    }

    /**
     * customer data is added to the CustomerTable in the view
     */
    public void loadCustomers() throws SQLException {
        customerdata = CustomersQuery.getAllCustomers();
        CustomerTable.setItems(customerdata);
    }


    @FXML
    void onAddCustomer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AddCust.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("addCust"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * I want to spend some time talking about this line of code:
     * if((result.isPresent()  && result.get() ==ButtonType.OK) && !(CustomersQuery.deleteConfirmation(CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID()))){
     * Alerts.deleteError();
     * }
     * The default return for deleteConfirmation was true. if it was not true, then that entailed that there were appointments with matching ids, and that a delete should not happen.
     * else the delete operation from Customer's Query is executed
     */
    @FXML
    void onDeleteCustomer(ActionEvent event) throws SQLException {
        if ((CustomerTable.getSelectionModel().getSelectedItem() != null)) {
            try {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(rb.getString("DelWarn"));
                alert.setHeaderText(rb.getString("DelCust"));
                alert.setContentText(rb.getString("DeleteCustQ")
                        + "\nCUSTOMER ID# : " + CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID()
                        + "\nCUSTOMER NAME :" + CustomerTable.getSelectionModel().getSelectedItem().getCustomer_Name());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() != ButtonType.CANCEL) {
                    if (result.get() == ButtonType.OK && !CustomersQuery.deleteConfirmation(CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID())) {
                        Alerts.deleteError();
                    } else {
                        Alert delete_alert = new Alert(Alert.AlertType.CONFIRMATION);
                        delete_alert.setTitle(rb.getString("DelConfirm"));
                        delete_alert.setHeaderText("Customer Deleted");
                        delete_alert.setContentText(rb.getString("CustDelResults") +
                                "\nCUSTOMER ID# : " + CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID()
                                + "\nCUSTOMER NAME : "
                                + CustomerTable.getSelectionModel().getSelectedItem().getCustomer_Name());
                        Optional<ButtonType> delete_result = delete_alert.showAndWait();
                        CustomersQuery.delete(CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID());
                    }
                }
            } catch (NullPointerException e) {
                Alerts.selectionError();
            }
        } else {
            Alerts.selectionError();
        }
    }

    //
    @FXML
    void onUpdateCustomer(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/ModCust.fxml"));
            loader.load();
            ModCustController modCustController = loader.getController();//get controller tied to view
            modCustController.customer_Passer(CustomerTable.getSelectionModel().getSelectedIndex(), CustomerTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(rb.getString("modCust"));
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alerts.selectionError();
        }
    }

    @FXML
    void mainMenuReturn(ActionEvent event) throws IOException {
        mainMenuReturnView(event);
    }

    static void mainMenuReturnView(ActionEvent event) throws IOException {
        LoginController.mainMenuView(event);
    }


    @FXML
    void addAppointment(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/AddAppt.fxml"));
            loader.load();
            AddApptController addApptController = loader.getController();
            addApptController.customer_Passer(CustomerTable.getSelectionModel().getSelectedIndex(), CustomerTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(rb.getString("addAppt"));
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alerts.selectionError();
        }
    }

    @FXML
    void updateAppointment(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/ModAppt.fxml"));
            loader.load();
            ModifyApptController modifyApptController = loader.getController();
            modifyApptController.customer_Passer(CustomerTable.getSelectionModel().getSelectedIndex(), CustomerTable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(rb.getString("modAppt"));
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alerts.selectionError();
        }
    }

}
