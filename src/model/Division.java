package model;

import helper.DivisionsQuery;
import javafx.collections.ObservableList;


public class Division {

    private int divisionID;
    private String divisionName;
    public int countryID;

    /**
     *
     * @param divisionID the division ID to set
     * @param divisionName the division name to set
     * @param country_ID the country ID to set
     */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * @return the division ID
     */
    public int getDivisionID() { return divisionID; }

    /**
     * @return the division name
     */
    public String getDivisionName() { return divisionName; }

    /**
     *
     * @return the country ID
     */
    public int getCountryID() { return countryID; }


    /** This method searches the first level division combo box for matching division ID of the customer selected.
     * @param divId The customer's division ID.
     * @return Returns the first level division with matching ID.*/
    public static Division getDivisionIdMatch(int divId) {
        ObservableList<Division> divisions = DivisionsQuery.getAllDivisions();

        Division division = null;

        for (int i = 0; i < divisions.size(); i++) {
            Division firstLevelDivision = divisions.get(i);

            if (firstLevelDivision.getDivisionID() != divId) {
                continue;
            } else {
                division = firstLevelDivision;
                break;
            }

        }

        return division;

    }


}
