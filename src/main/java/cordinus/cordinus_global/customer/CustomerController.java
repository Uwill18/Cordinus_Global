package cordinus.cordinus_global.customer;

import cordinus.cordinus_global.helper.CustomersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

//implement similar to product in that appointments associate with customers
//initate getters and setter
public class CustomerController {

    public void LoadCustomers() throws SQLException {
        CustomersQuery.select();

    }

    @FXML
    private TableColumn<?, ?> Address_Column;

    @FXML
    private TableView<?> CustomerTable;

    @FXML
    private TableColumn<?, ?> ID_Column;

    @FXML
    private TableColumn<?, ?> Name_Column;

    @FXML
    private TableColumn<?, ?> Phone_Column;

    @FXML
    private TableColumn<?, ?> Postal_Column;

    @FXML
    void OnAddCustomer(ActionEvent event) {

    }

    @FXML
    void OnDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void OnUpdateCustomer(ActionEvent event) {

    }
}
