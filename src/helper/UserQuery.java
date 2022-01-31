package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//private static final String loginQuery = "SELECT * FROM users WHERE email_id = ? and password = ?";

public abstract class UserQuery {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM users"; //SQL
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); //Create Prepared Statement
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            ListManager.addUser(new User(userId, userName, password));
        }
    }

    public static boolean validate(String userName, String password) throws SQLException {
        try{
            String sql = "SELECT * FROM users WHERE User_Name = ? and Password = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql) ;
            {
                ps.setString(1, userName);
                ps.setString(2, password);

                System.out.println(ps);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            // print SQL exception information
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @return observable list of all user data from database
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> usersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users"; //SQL
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); //Create Prepared Statement
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            User user = new User(userID, userName, userPassword);
            usersObservableList.add(user);
        }
        return usersObservableList;
    }


}
