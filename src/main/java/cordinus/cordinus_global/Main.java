package cordinus.cordinus_global;

import cordinus.cordinus_global.DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

//loads homescreen in this case, the loginscreen




public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

        //https://www.tutorialspoint.com/javafx/javafx_images.htm
        //Creating an image
        Image image = new Image(new FileInputStream("src/main/java/cordinus/cordinus_global/image/Globe.png"));

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(225);
        imageView.setY(125);

        //setting the fit height and width of the image view
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        //Creating a Group object
        /**after researching loading multiple views, I learned that I could load the fxmlloader in with the Group object*/
        Group root = new Group( fxmlLoader.load(),imageView);

        //Creating a scene object
       Scene scene = new Scene(root);



        //Scene scene = new Scene(fxmlLoader.load());
        //scene.getStylesheets().add("src/main/resources/cordinus/cordinus_global/styles.css");
        stage.setTitle("Cordinus Global");
        stage.setScene(scene);
        stage.show();




        //displayImage();

    }

//    private void displayImage() {
//
//
//        //Image myImage = new Image(getClass().getResourceAsStream("src/main/java/cordinus/cordinus_global/image/Globe.png"));
//        //GlobeImgView.setImage(myImage);
//       // GlobeImgView.getImage();
//    }

    public static void main(String[] args) throws SQLException, IOException {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }

    @FXML
    ImageView GlobeImgView;
}