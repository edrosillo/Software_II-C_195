package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    /**
     * The stage object that will be used to hold the GUI and data of the new screens.
     */
    Stage stage;

    /**
     * The scene object that will be used to generate the new screens.
     */
    Parent scene;

    @FXML
    private TextField customerIDTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField customerAddressTxt;

    @FXML
    private TextField customerCityTxt;

    @FXML
    private TextField customerCountryTxt;

    @FXML
    private TextField customerPCTxt;

    @FXML
    private TextField customerPhoneTxt;

    @FXML
    private TextField customerDivisionTxt;

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
