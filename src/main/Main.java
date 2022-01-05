package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * This is the Main Class for an Inventory Management application that is designed to manage
 * an inventory of parts and products containing of parts.
 *
 */

public class Main extends Application {

    /**
     * The start method creates the first screen in the application.
     * It generates FXML stage and loads the GUI and information of the initial scene.
     *
     * @param primaryStage This will be the GUI and information the user will see.
     *
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        root.setStyle("-fx-font-family: 'SansSerif';");
        primaryStage.setTitle("Appointment Management System");
        primaryStage.setScene(new Scene(root, 880, 607));
        primaryStage.show();
    }

    /**
     * The main method is the entry point to the application. It launches the Java application.
     * The sample data is contained within the main method so it populates the tables as soon as the program is launched.
     *
     * @param args In the case of this application the arguments are all the information that will populate the Parts and Products tables.
     */

    public static void main(String[] args) {
        launch(args);
        JDBC.openConnection();
        JDBC.closeConnection();
        //ResourceBundle rb = ResourceBundle.getBundle("main/Nat", Locale.getDefault());

        //if(Locale.getDefault().getLanguage().equals("es"))
       //     System.out.println(rb.getString());
    }
}