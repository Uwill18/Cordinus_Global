package cordinus.cordinus_global;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Demo implements Initializable  {


    public ComboBox<LocalTime> startCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        for(int i = 0; i < 24; i++){
//            //startCombo.getItems().add(LocalTime.of(i,0));

//            if(i< 23){
//                startCombo.getItems().add(LocalTime.of(i+1,0));
//                //add 15 to minute, loop w/i loop
//                //outerloop for hours
//                //inloop for minutes, then add minute increments
//            }
//            else{
//                startCombo.getItems().add(LocalTime.of(0,0));
//            }
//
//        }

//Mr. Wabara reviewed timeloops with me to output to combobox
        //we discussed that combining the times with the date picker to a timestamp
        //would help format the time
        for(int h = 0; h < 24; h++){
            for(int  m = 0; m < 4; m++){ //<4 for 15m increments
                System.out.println(LocalTime.of(h,0));
                System.out.println(LocalTime.of(h,15));
                System.out.println(LocalTime.of(h,30));
                System.out.println(LocalTime.of(h,45));
                //hardcode the times for forloops
            }
        }


    }
}
