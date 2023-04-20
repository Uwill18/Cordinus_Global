package cordinus.cordinus_global.DAO;

import cordinus.cordinus_global.model.Country;
import cordinus.cordinus_global.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountriesQuery {
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();


        try {
            String sql = "SELECT * FROM Countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                countryList.add(new Country(countryID,country));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countryList;

    }

    public static Country getCountryByDivision(int divisionID){
        Country country = null;
        try {
            String sql = "SELECT * FROM Countries AS C INNER JOIN First_Level_Divisions AS D ON C.Country_ID = D.Country_ID AND D.Division_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryname = rs.getString("Country");
                country = new Country(countryID,countryname) ;
                return country;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}



