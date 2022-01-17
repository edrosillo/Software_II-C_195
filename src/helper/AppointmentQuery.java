package helper;

import model.Appointment;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class AppointmentQuery {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM appointments, customers WHERE customers.Customer_ID = appointments.Customer_ID"; //SQL
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); //Create Prepared Statement
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String customerName = rs.getString("Customer_Name");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            //String createDate = rs.getString("Create_Date");
            //String createdBy = rs.getString("Created_By");
            //String lastUpdate = rs.getString("Last_Update");
            ListManager.addAppointment(new Appointment(appointmentID, customerName ,title, description, location, type,
            start, end));
        }
    }


}
