package cordinus.cordinus_global.appointment;

import cordinus.cordinus_global.helper.AppointmentsQuery;

import java.sql.SQLException;

//implement similar to Part
//initiate getters and setters
public class ApptController {

    public void AppointmentTable() throws SQLException {
        AppointmentsQuery.select();
    }
}
