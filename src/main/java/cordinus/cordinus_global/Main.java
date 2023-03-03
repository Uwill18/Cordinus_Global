package cordinus.cordinus_global;

import cordinus.cordinus_global.customer.CustomerController;
import cordinus.cordinus_global.helper.AppointmentsQuery;
import cordinus.cordinus_global.helper.CustomersQuery;
import cordinus.cordinus_global.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

//loads homescreen in this case, the loginscreen
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cordinus Global");
        stage.setScene(scene);
        stage.show();
        //
    }

    public static void main(String[] args) throws SQLException, IOException {
        JDBC.openConnection();

        launch();
        JDBC.closeConnection();
    }
}