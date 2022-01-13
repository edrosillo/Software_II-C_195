package helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataManager {

    private static Statement statement; //Statement reference


    //Create Statement object
    public static void setStatement(Connection conn) throws SQLException {

        statement = conn.createStatement();

    }

    //Return statement object
    public static Statement getStatement() {
        return statement;
    }
}
