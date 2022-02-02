package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import helper.JDBC;
import helper.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Text titleLbl;

    @FXML
    private Text userNameLbl;

    @FXML
    private Text passwordLbl;

    @FXML
    private TextField userNameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private Button loginBtn;

    @FXML
    private Button resetBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Text userLocationLbl;

    /**
     * The stage object that will be used to hold the GUI and data of the new screens.
     */
    Stage stage;

    /**
     * The scene object that will be used to generate the new screens.
     */
    Parent scene;

    boolean successLogin = false;
    String errorTitle = "Log-In Failed";
    String errorMessage ="Invalid username or password";

    /**
     * This method Exits the program.
     * @param event Exit button action.
     */

    @FXML
    void onActionExit(ActionEvent event) {
        JDBC.closeConnection();
        System.exit(0);
    }

    @FXML
    void clearLogin(ActionEvent event) {
        userNameTF.clear();
        passwordTF.clear();
    }

    @FXML
    public void onActionLogin(ActionEvent event) throws SQLException, IOException {
        try {
            if (userNameTF.getText().isEmpty()) {
                displayAlert(3);
                return;
            }
            if (passwordTF.getText().isEmpty()) {
                displayAlert(4);
                return;
            }
            String userName = userNameTF.getText();
            String password = passwordTF.getText();

            successLogin = UserQuery.validate(userName, password);

            if (!successLogin) {
                displayAlert(5);
            } else {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
                scene.setStyle("-fx-font-family: 'SansSerif';");
                stage.setScene(new Scene(scene));
                stage.show();
                AppointmentMenuController login = new AppointmentMenuController();
                login.appointmentNotification();
            }
        }
        catch(Exception e){
            // print SQL exception information
            System.out.println(e.getMessage());
        }
        userActivity();
    }

    /**
     * This function sets checks the user's language and location settings.
     * It will translate the interface into French if user’s computer language setting = fr.
     * Headers, Buttons, Labels and Error Messages are translated into French.
     */
    public void checkLanguage() {
        ZoneId currentZone = ZoneId.systemDefault();
        userLocationLbl.setText("User Location: " + currentZone);
        Locale French = new Locale("fr", "FR");
        ResourceBundle rb = ResourceBundle.getBundle("main/Nat", Locale.FRENCH);
        if(Locale.getDefault().getLanguage().equals("fr")) {
            Locale.setDefault(French);
            titleLbl.setText((rb.getString("Appointment,Management,System")).replaceAll(",", " "));
            userNameLbl.setText((rb.getString("Username")).replaceAll(",", " "));
            passwordLbl.setText((rb.getString("password")).replaceAll(",", " "));
            //passwordTF.setPromptText(rb.getString("password").replaceAll(",", " "));
            loginBtn.setText(rb.getString("Submit"));
            resetBtn.setText(rb.getString("Reset"));
            exitBtn.setText(rb.getString("Exit"));
            int indexOfSeparation = (currentZone.toString()).indexOf("/");
            String printRegion = (currentZone.toString()).substring(0, indexOfSeparation);
            String printCountryFR;
            if (printRegion.equals("Pacific") || printRegion.equals("America") || printRegion.equals("Europe")) {
                printCountryFR = (rb.getString(printRegion));
                int indexOfEnd = (currentZone.toString()).length();
                String locationToPrint = (currentZone.toString()).substring(indexOfSeparation, indexOfEnd);
                userLocationLbl.setText((rb.getString("User,Location")).replaceAll(",", " ") +
                        ": " + printCountryFR + locationToPrint);
            }
            else {
                userLocationLbl.setText((rb.getString("User,Location")).replaceAll(",", " ") +
                        ": " + currentZone);
            }
            errorMessage = rb.getString("Invalid,username,or,password").replaceAll(",", " ");
            errorTitle = rb.getString("Log-In,Failed").replaceAll(",", " ");
        }
    }


    /**
     * This function used to create a log file of the user's activity
     * which adds new entries as the application gets used
     */
    public void userActivity() throws IOException {
        LocalDate loginDate = LocalDateTime.now().toLocalDate();
        Timestamp loginTimestamp = Timestamp.valueOf(LocalDateTime.now());
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        outputFile.print("Date: " + loginDate + " -- ");
        outputFile.print("Timestamp: " + loginTimestamp + " -- ");
        if (successLogin) {
            outputFile.print("Status: Log In Attempt Successful\n");
        }
        else {
            outputFile.print("Status: Log In Attempt Failed\n");
        }
        outputFile.close();
    }

    /**
     * This switch statement is used to display a variety of alert and error messages.
     * @param alertType Alert selects a message based on case number.
     */

    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        DialogPane dp = alert.getDialogPane();
        dp.setStyle("-fx-font-family:sans-serif");
        DialogPane dp2 = alertError.getDialogPane();
        dp2.setStyle("-fx-font-family:sans-serif");

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("User ID does not exist");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect Password entered");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please enter username");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please enter password");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle(errorTitle);
                alertError.setHeaderText(errorMessage);
                alertError.showAndWait();
                break;
            case 6:
                alertConfirmation.setTitle("Success");
                alertConfirmation.setHeaderText("Logging In");
                alertConfirmation.showAndWait();
                break;
        }
    }


    /**
     * This method initializes the MainController and populates the Part and Product table views.
     *
     * @param url The url is used to find the relative paths for the root object.
     * @param resourceBundle The resource bundle is used to localize the root object.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        checkLanguage();


    }

}
