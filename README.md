-----------------------------------------
Cordinus Global v1.0
------------------------------------------

This desktop scheduling application retrieves information from a mysql database, and allows the user to manipulate the datasets in the application view through the use of JavaFX controls for appointments, customers, and reports.

Directions for how to run the program

Starting the program directs the user to the Homescreen where a button prompts them to proceed to the Loginscreen.
The Loginscreen awaits the user to enter the appropriate username and password that match the database credentials.
Each validation attempt is executed through the confirm button and written to login_activity.txt. Upon successful
validation the user is navigated to the Main Menu to select one of four buttons respective to CustomerScreen,
AppointmentScreen, ReportScreen, and a back button that redirects back to the login for the user to exit if needed.

Electing the Appointment Screen displays all appointments while presenting the user with the choices of adding,
updating, or deleting appointments. Should the user wish to the see the effects of the executed operation
they can click the back button to return to the Main Menu, and then navigate back to the Appointment Screen.

In similar fashion the Customer Screen displays all customers while presenting the user with the choices of adding,
updating, or deleting customers. Further for any selected customer the user can perform an addition and/or update
of an appointment for that chosen customer. After entering the relevant data for those operations, the user is 
navigated back to the appointment screen, and can return to the Main Menu through the Back button.


The Report Screen is partitioned by three tabs with each pertaining to its own report type.
Report one exacts the total number of customer appointments by type and month. The second
report shows a schedule for each contact within the organization. The final and third report
relays to textfield the remaining number of appointments per day by scheduling interval.

Exception Control & Alerts
Last but not least error control is implemented with a series of alerts catered to each of the
above sections highlighted earlier. This application will also switch to French if that is detected
to be the user's preferred language.


-----------------------------------------
Courtesy of:

Author: Uri W. Easter

Student Application Version: 1.0 

Date: 05/07/2023 




IntelliJ IDEA Community Edition 2022.3.1, 

JavaFX-SDK-11.0.17,

Scene Builder 19.0.0

mysql-connector-j-8.0.31

Thank you!
------------------------------------------
