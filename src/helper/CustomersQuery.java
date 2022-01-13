package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomersQuery {

    public static void select() throws SQLException {

        String sql = "SELECT * FROM customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);//Create Prepared Statement
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            //Get Column Data
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String division = rs.getString("Division");
            String country = rs.getString("Country");
            int divisionIdFK = rs.getInt("Division_ID");
            int countryIdFK = rs.getInt("Country_ID");
        }

    }

}
