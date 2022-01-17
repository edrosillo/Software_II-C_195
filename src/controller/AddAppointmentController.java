package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> monthCB;
    private String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    @FXML
    private ComboBox<String> dayCB;
    private String[] days = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};


    @FXML
    private ComboBox<String> yearCB;
    private String[] years = {"2021","2022","2023","2024"};

    @FXML
    private ComboBox<String> startMinuteCB;

    private String[] startMinute = {"00","15","30","45"};

    @FXML
    private ComboBox<String> startTimeOfDayCB;
    private String[] startTimeOfDay = {"AM","PM"};

    @FXML
    private ComboBox<String> startHourCB;
    private String[] startHour = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    @FXML
    private ComboBox<String> endMinuteCB;

    private String[] endMinute = {"00","15","30","45"};

    @FXML
    private ComboBox<String> endTimeOfDayCB;
    private String[] endTimeOfDay = {"AM","PM"};

    @FXML
    private ComboBox<String> endHourCB;
    private String[] endHour = {"01","02","03","04","05","06","07","08","09","10","11","12"};

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

        monthCB.getItems().addAll(months);
        dayCB.getItems().addAll(days);
        yearCB.getItems().addAll(years);
        startHourCB.getItems().addAll(startHour);
        startMinuteCB.getItems().addAll(startMinute);
        startTimeOfDayCB.getItems().addAll(startTimeOfDay);
        endHourCB.getItems().addAll(endHour);
        endMinuteCB.getItems().addAll(endMinute);
        endTimeOfDayCB.getItems().addAll(endTimeOfDay);

    }

}
