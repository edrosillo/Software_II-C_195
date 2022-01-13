package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    /**
     * The stage object that will be used to hold the GUI and data of the new screens.
     */
    Stage stage;

    /**
     * The scene object that will be used to generate the new screens.
     */
    Parent scene;

    @FXML
    private TextField AppointmentIDTxt;

    @FXML
    private TextField userIDTxt;

    @FXML
    private TextField customerIDTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private TextField LocationTxt;

    @FXML
    private TextField contactTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private ChoiceBox<?> monthCB;

    @FXML
    private ChoiceBox<?> dayCB;

    @FXML
    private ChoiceBox<?> yearCB;

    @FXML
    private ChoiceBox<?> startMinuteCB;

    @FXML
    private ChoiceBox<?> startTimeOfDayCB;

    @FXML
    private ChoiceBox<?> startHourCB;

    @FXML
    private ChoiceBox<?> endMinuteCB;

    @FXML
    private ChoiceBox<?> endTimeOfDayCB;

    @FXML
    private ChoiceBox<?> endHourCB;

    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ScheduleApp.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) {

    }



    /**
     * This method initializes the AddCustomerController.
     *
     * @param url The url is used to find the relative paths for the root object.
     * @param resourceBundle The resource bundle is used to localize the root object.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

}
