package controller;

import java.io.IOException;
import java.sql.SQLException;

import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField emailIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    /**
     * The stage object that will be used to hold the GUI and data of the new screens.
     */
    Stage stage;

    /**
     * The scene object that will be used to generate the new screens.
     */
    Parent scene;

    /**
     * This method Exits the program.
     * @param event Exit button action.
     */

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void login(ActionEvent event) throws SQLException, IOException {
        System.out.println(emailIdField.getText());
        System.out.println(passwordField.getText());

        if (emailIdField.getText().isEmpty()) {
            displayAlert(3);
            return;
        }
        if (passwordField.getText().isEmpty()) {
            displayAlert(4);
            return;
        }

        String emailId = emailIdField.getText();
        String password = passwordField.getText();
        ;

        boolean flag = JDBC.validate(emailId, password);

        if (!flag) {
            displayAlert(4);
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/scheduleapp.fxml"));
            scene.setStyle("-fx-font-family: 'SansSerif';");
            stage.setScene(new Scene(scene));
            stage.show();
        }
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
                alertError.setTitle("Failed");
                alertError.setHeaderText("Please enter correct Email and Password");
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


    }

}
