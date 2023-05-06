package cordinus.cordinus_global.model;

/**Used standard variables according to the database and functional requisites Initialed constructors, getters, and setters according to my initial understanding of the data types.
 * In the future I would do more UML diagramming to align myself better with best practices */

public class Country {
    int Country_ID;
    String Country;

    public Country(int country_ID, String country) {
        Country_ID = country_ID;
        Country = country;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    /** https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=0bbf1823-b54b-4e02-8814-ab8a00019993
     * 14 minutes, class and hash code of object are displayed. to fix this, we override the existing toString method.*/
    @Override
    public String toString(){
        return Country;
    }
}
