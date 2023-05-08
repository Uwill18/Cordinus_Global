package cordinus.cordinus_global;
import cordinus.cordinus_global.DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

/** This was the most challenging application to date that I've had to design. To that end, I would like to give thanks
 * to each of the professors that have helped me when I have gotten stuck, and who have imparted wisdom to help me
 * improve my execution of the solutions relative to this assignment.
 *
 * Thank you:
 * Malcolm Wabara
 * Juan Ruiz
 * Mark Kinkead
 * Sunitha Kandalam
 * Carolyn SherDecusatis
 * Bill Jing
 *
 * Last but not least, I would like to thank my loving wife for her patience and flexibility with me during this process.
 * Many nights she has watched me burn the midnight oil working on this assignment, only to pursue its completion from
 * dusk to dawn the next day. I am especially appreciative for her grace in this period as we have been balancing the
 * needs of our three-year-old and newborn daughter, and I wrote this to let her know a man could not be more grateful
 * for a companion in life.
 *
 *
 * Future Goals and Lessons Learned
 * Personal affairs aside, I wanted to weigh on my future goals and lessons learned in this project.
 * To start with, in the future I would spend more time extensively mapping system design with uml in
 * addition to researching design patterns, as I have learned for each hour planning saves three hours of coding.
 * I would also look for opportunities to map through objects and their properties  to display the correct data sets
 * for the user to interact with.
 * I would try be clear on system requirements, so as not to overthink them.
 * I would also look for opportunities to use the database and the DAO to my
 * advantage as it is most effective at calculating data quickly, and retrieving specified data to the view.
 * Further, in reports I might also enable a button to save and print out current report data.
 * Lastly, if I had more time I would have the whole app translate to French when prompted since one of the use cases
 * is to translate for said case. And for kicks, I might also have a Spanish version as that is my second language,
 * and I would try to implement mutation of data configured specifically to the user.
 * All that said, I am very proud of this project, and the lessons this journey has given me.
 * Without further ado, please enjoy this User Experience!
 *
 *
 * References
 *https://srm--c.na127.visual.force.com/apex/coursearticle?Id=kA03x000000yIOLCA2
 //https://wgu.webex.com/webappng/sites/wgu/recording/bf6e7b5d5d06103abd8f005056815ee6/playback
 //https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=0bbf1823-b54b-4e02-8814-ab8a00019993
 //https://www.tutorialspoint.com/javafx/javafx_images.htm
 //https://www.youtube.com/watch?v=at4xyBOdgME
 //https://www.youtube.com/watch?v=i0j2AmsJQz0https://www.youtube.com/watch?v=i0j2AmsJQz0
 //https://www.youtube.com/watch?v=3Ht-JMQh2JI
 //https://www.youtube.com/watch?v=at4xyBOdgME
 //https://www.youtube.com/watch?v=i0j2AmsJQz0
 //https://www.youtube.com/watch?v=3tmz-0g3EPs&list=PLVo4QEZoUs5G88wJ2AajTIS33oFF8R5N-&index=2
 //https://stackoverflow.com/questions/39539838/javafx-populating-a-combobox-with-data-from-a-mysql-database-stringconverter-b
 //https://www.youtube.com/watch?v=1AmIKxHbLJo
 //https://www.youtube.com/watch?v=mY54KZkuZPQ
 //https://www.youtube.com/watch?v=3Ht-JMQh2JI
 //https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java
 //https://stackoverflow.com/questions/44031561/get-month-name-from-month-number-for-a-series-of-numbers*



 * COURTESY OF
 * Author: Uri W. Easter
 * Student Application Version: 1.0
 * Date: 05/07/2023
 * IntelliJ IDEA Community Edition 2022.3.1,
 *
 * JavaFX-SDK-11.0.17,
 * Scene Builder 19.0.0
 * mysql-connector-j-8.0.31
 * */



public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomeScreen.fxml"));

        //https://www.tutorialspoint.com/javafx/javafx_images.htm
        //Creating an image
       // Image image = new Image(new FileInputStream("src/main/java/cordinus/cordinus_global/image/Globe.png"));
        Image image = new Image(new FileInputStream("src/main/java/cordinus/cordinus_global/image/Globe.png"));
        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(225);
        imageView.setY(100);

        //setting the fit height and width of the image view
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        //Creating a Group object
        /**after researching loading multiple views, I learned that I could load the fxmlloader in with the Group object*/
        Group root = new Group( fxmlLoader.load(), imageView);

        //Creating a scene object
       Scene scene = new Scene(root);

        //Scene scene = new Scene(fxmlLoader.load());
        //scene.getStylesheets().add("src/main/resources/cordinus/cordinus_global/styles.css");
        stage.setTitle("Cordinus Global");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, IOException {
        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        JDBC.openConnection();

        launch();
        JDBC.closeConnection();


    }
}