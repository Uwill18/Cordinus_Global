package cordinus.cordinus_global.model;


/**Used standard variables according to the database and functional requisites Initialed constructors, getters, and setters according to my initial understanding of the data types.
 * In the future I would do more UML diagramming to align myself better with best practices */

public class Division {
    int Division_ID;
    String Division;
    int Country_ID;
    public Division(int division_ID, String division, int country_ID) {
        Division_ID = division_ID;
        Division = division;
        Country_ID = country_ID;
    }

    public int getDivision_ID() {
        return Division_ID;
    }
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }
    public String getDivision() {
        return Division;
    }
    public void setDivision(String division) {
        Division = division;
    }
    public int getCountry_ID() {
        return Country_ID;
    }
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    /** https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=0bbf1823-b54b-4e02-8814-ab8a00019993
     * 14 minutes, class and hash code of object are displayed. to fix this, we override the existing toString method.*/
    @Override
    public String toString(){
        return Division;
    }
}
