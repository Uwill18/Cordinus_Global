package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.Country;
import cordinus.cordinus_global.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionsQuery {

    public static ObservableList<Division> getAllDivisions() {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM First_Level_Divisions";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                divisionList.add(new Division(divisionID,divisionName,countryID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return divisionList;
    }

    /**returns a division object given divisionID, useful in populating the mod cust controller
     * with division name*/
    public static Division getDivisionByID(int divisionID){
        Division division = null;

        try {
            String sql = "SELECT * FROM First_Level_Divisions WHERE Division_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                division =new Division(divisionID,divisionName,countryID);
                return division;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
