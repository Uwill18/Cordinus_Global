package cordinus.cordinus_global.model;

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
     * 14 minutes, class and hash code are converted to string. to fix this, we override the existing toString method.*/
    @Override
    public String toString(){
        return Country;
    }
}
