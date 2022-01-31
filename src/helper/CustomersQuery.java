package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

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

    public static int insert(String customerName, String address, String postalCode, String phone) throws SQLException {
        /*int customerId = 0;
        PreparedStatement ps1 = JDBC.connection.prepareStatement("SELECT MAX(customerId) FROM customer");
        ResultSet rs = ps1.executeQuery();
        while(rs.next()){
            customerId = rs.getInt("MAX(customerId)");
        }
        customerId++;
         */

        String sql = "INSERT INTO customer(customerName, address, postalCode, phone) VALUES(?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);//Create Prepared Statement
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3,postalCode);
        ps.setString(4, phone);
        return ps.executeUpdate();

    }

    public static int getMaxID() throws SQLException {
        int customerId = 0;
        PreparedStatement ps1 = JDBC.connection.prepareStatement("SELECT MAX(customerId) FROM customer");
        ResultSet rs = ps1.executeQuery();
        if (rs.next()){
            customerId = rs.getInt(1);
        }
        return customerId;
    }

    /**
     * @return observable list of all customer data from database
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); //Create Prepared Statement
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");
            Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID);
            customersObservableList.add(customer);
        }
        return customersObservableList;
    }



}
