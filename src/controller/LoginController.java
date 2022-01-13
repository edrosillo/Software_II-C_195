package controller;

import java.io.IOException;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField userNameTxt;

    @FXML
    private PasswordField passwordTxt;

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
        JDBC.closeConnection();
        System.exit(0);
    }

    @FXML
    public void onActionLogin(ActionEvent event) throws SQLException, IOException {
        try {
            if (userNameTxt.getText().isEmpty()) {
                displayAlert(3);
                return;
            }
            if (passwordTxt.getText().isEmpty()) {
                displayAlert(4);
                return;
            }

            String userName = userNameTxt.getText();
            String password = passwordTxt.getText();

            boolean successLogin = UserQuery.validate(userName, password);

            if (!successLogin) {
                displayAlert(5);
            } else {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ScheduleApp.fxml"));
                scene.setStyle("-fx-font-family: 'SansSerif';");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e){
            // print SQL exception information
            System.out.println(e.getMessage());
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
