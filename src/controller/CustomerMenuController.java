package controller;

import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Country;
import model.Customer;
import model.Division;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.function.Function;

public class CustomerMenuController implements Initializable {
    /**
     * The stage object that will be used to hold the GUI and data of the new screens.
     */
    Stage stage;

    /**
     * The scene object that will be used to generate the new screens.
     */
    Parent scene;

    int lastButton = -1; // used with lambda #2


    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableColumn<Customer, Integer> customerIDCol;
    @FXML
    public TableColumn<Customer, String> customerNameCol;
    @FXML
    public TableColumn<Customer, String> customerAddressCol;
    @FXML
    public TableColumn<Customer, Integer> customerDivisionCol;
    @FXML
    public TableColumn<Customer, String> customerPostalCodeCol;
    @FXML
    public TableColumn<Customer, String> customerPhoneCol;
    @FXML
    private Button saveCustomerBtn;

    @FXML
    private Button modifyCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private ToggleGroup sceneChangeTG;

    @FXML
    private TextField customerIDTF;

    @FXML
    private TextField customerNameTF;

    @FXML
    private TextField customerAddressTF;

    @FXML
    private ComboBox<String> customerCountryCB;

    @FXML
    private ComboBox<String> customerDivisionCB;

    @FXML
    private TextField customerPostalCodeTF;

    @FXML
    private TextField customerPhoneNumberTF;

    @FXML
    void goToAppointmentRB(ActionEvent event) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void goToReportsRB(ActionEvent event) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void deleteCustomer(ActionEvent event) throws SQLException {

        String sqlDelete = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlDelete);
        int customerIDDelete = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
        ObservableList<Appointment> appointmentsObservableList = AppointmentQuery.getAllAppointments();
        for (Appointment appointment : appointmentsObservableList) {
            int customerIDCheck = appointment.getCustomerID();
            int appointmentIDDelete = appointment.getAppointmentID();
            String appointmentTypeMessage = appointment.getAppointmentType();
            if (customerIDDelete == customerIDCheck) {
                String sqlDeleteAppointments = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps1 = JDBC.connection.prepareStatement(sqlDeleteAppointments);
                ps1.setInt(1, appointmentIDDelete);
                ps1.execute();
                JOptionPane.showMessageDialog(null,
                        "Appointment with ID: " + appointmentIDDelete + "\nAppointment of Type: " + appointmentTypeMessage + "\nhas been deleted from Database",
                        "Appointment Deleted", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        ps.setInt(1, customerIDDelete);
        ps.execute();
        addCustomerDataToTable();
        clearData();
        JOptionPane.showMessageDialog(null,
                "Customer with ID: " + customerIDDelete + "has been deleted from Database",
                "Customer Deleted", JOptionPane.INFORMATION_MESSAGE);


    }

    /**
     *
     * gets data that was populated in boxes
     * matches first-level division name with ID
     * gets and executes prepared update statement
     * calls appropriate functions to display data and reset boxes
     *
     * @throws SQLException if exception has occurred
     */
    public void modifyCustomer() throws SQLException {
        int idUpdate = Integer.parseInt(customerIDTF.getText());
        String nameUpdate = customerNameTF.getText();
        String addressUpdate = customerAddressTF.getText();
        String postalCodeUpdate = customerPostalCodeTF.getText();
        String phoneNumberUpdate = customerPhoneNumberTF.getText();
        String divisionUpdateString = customerDivisionCB.getValue();
        int divisionUpdateInt = 0;
        ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
        for (Division firstLevelDivision : allDivisions) {
            if (divisionUpdateString.equals(firstLevelDivision.getDivisionName())) {
                divisionUpdateInt = firstLevelDivision.getDivisionID();
            }
        }
        Timestamp lastUpdateUpdate = Timestamp.valueOf(LocalDateTime.now());
        String lastUpdatedByUpdate = "admin";
        String sqlUpdate = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, " +
                "Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlUpdate);
        ps.setInt(1, idUpdate);
        ps.setString(2, nameUpdate);
        ps.setString(3, addressUpdate);
        ps.setString(4, postalCodeUpdate);
        ps.setString(5, phoneNumberUpdate);
        ps.setTimestamp(6, lastUpdateUpdate);
        ps.setString(7, lastUpdatedByUpdate);
        ps.setInt(8, divisionUpdateInt);
        ps.setInt(9, idUpdate);
        ps.execute();
        addCustomerDataToTable();
        consoleNotification();
        clearData();
    }

    @FXML
    void filterDivisionCB(ActionEvent event) {

        ObservableList<Division> allFirstLevelDivisions = DivisionsQuery.getAllDivisions();
        ObservableList<String> divisionNamesUS = FXCollections.observableArrayList();
        ObservableList<String> divisionNamesUK = FXCollections.observableArrayList();
        ObservableList<String> divisionNamesCanada = FXCollections.observableArrayList();
        for (Division division : allFirstLevelDivisions) {
            if (division.getCountryID() == 1) {
                divisionNamesUS.add(division.getDivisionName());
            }
            else if (division.getCountryID() == 2) {
                divisionNamesUK.add(division.getDivisionName());
            }
            else if (division.getCountryID() == 3) {
                divisionNamesCanada.add(division.getDivisionName());
            }
        }
        String selectedCountry = customerCountryCB.getSelectionModel().getSelectedItem();
        if (selectedCountry.equals("U.S")) {
            customerDivisionCB.setItems(divisionNamesUS);
        }
        else if (selectedCountry.equals("UK")) {
            customerDivisionCB.setItems(divisionNamesUK);
        }
        else if (selectedCountry.equals("Canada")) {
            customerDivisionCB.setItems(divisionNamesCanada);
        }

    }


    @FXML
    void addCustomerDataToBoxes(MouseEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            ObservableList<Country> allCountries = CountriesQuery.getAllCountries();
            ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
            ObservableList<String> divisionAllNames = FXCollections.observableArrayList();
            for (Division division : allDivisions) {
                divisionAllNames.add(division.getDivisionName());
            }
            customerDivisionCB.setItems(divisionAllNames);
            customerIDTF.setText(String.valueOf((selectedCustomer.getCustomerID())));
            customerNameTF.setText(selectedCustomer.getCustomerName());
            customerAddressTF.setText(selectedCustomer.getAddress());
            customerPostalCodeTF.setText(selectedCustomer.getPostalCode());
            customerPhoneNumberTF.setText(selectedCustomer.getPhone());
            int divisionIDToSet = selectedCustomer.getDivisionID();
            String divisionNameToSet = "";
            int countryIDToSet;
            String countryNameToSet = "";
            for (Division division : allDivisions) {
                if (divisionIDToSet == division.getDivisionID()) {
                    divisionNameToSet = division.getDivisionName();
                    countryIDToSet = division.getCountryID();
                    for (Country country : allCountries) {
                        if (countryIDToSet == country.getCountryID()) {
                            countryNameToSet = country.getCountry();
                        }
                    }
                }
            }
            customerDivisionCB.setValue(divisionNameToSet);
            customerCountryCB.setValue(countryNameToSet);
        }

    }

    /**
     *
     * verifies
     * auto-generates customer ID
     * matches division name string with division ID
     * gets and executes prepared insert statement
     * calls appropriate functions to display data and reset boxes
     *
     * @throws SQLException if exception has occurred
     */
    public void saveCustomer() throws SQLException {
        ObservableList<Customer> allCustomersList = CustomersQuery.getAllCustomers();
        int lastID = 0;
        for (Customer customer : allCustomersList) {
            lastID = customer.getCustomerID();
        }
        int idToAdd = lastID + 1;
        String nameAdd = customerNameTF.getText();
        String addressAdd = customerAddressTF.getText();
        String postalCodeAdd = customerPostalCodeTF.getText();
        String phoneNumberAdd = customerPhoneNumberTF.getText();
        int divisionIDAdd = 0;
        String selectedDivision = customerDivisionCB.getSelectionModel().getSelectedItem();
        ObservableList<Division> allFirstLevelDivisions = DivisionsQuery.getAllDivisions();
        for (Division division : allFirstLevelDivisions) {
            if (selectedDivision.equals(division.getDivisionName())) {
                divisionIDAdd = division.getDivisionID();
            }
        }
        LocalDateTime createdDateToAdd = LocalDateTime.now();
        String createdByAdd = "admin";
        Timestamp lastUpdateAdd = Timestamp.valueOf(LocalDateTime.now());
        String lastUpdatedByAdd = "admin";
        String sqlInsert = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlInsert);
        ps.setInt(1, idToAdd);
        ps.setString(2, nameAdd);
        ps.setString(3, addressAdd);
        ps.setString(4, postalCodeAdd);
        ps.setString(5, phoneNumberAdd);
        ps.setTimestamp(6, Timestamp.valueOf(createdDateToAdd));
        ps.setString(7, createdByAdd);
        ps.setTimestamp(8, lastUpdateAdd);
        ps.setString(9, lastUpdatedByAdd);
        ps.setInt(10, divisionIDAdd);
        ps.execute();
        addCustomerDataToTable();
        consoleNotification();
        clearData();
    }



    /**
     * @throws SQLException if exception has occurred
     */
    public void addCustomerDataToTable() throws SQLException {
        ObservableList<Customer> allCustomersList = CustomersQuery.getAllCustomers();
        customerTable.setItems(allCustomersList);
    }


    /**
     * This switch statement is used to display a variety of alert and error messages.
     * @param alertType Alert selects a message based on case number.
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dp = alert.getDialogPane();
        dp.setStyle("-fx-font-family:sans-serif");
        DialogPane dp2 = alertInfo.getDialogPane();
        dp2.setStyle("-fx-font-family:sans-serif");

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Customer");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
            case 3:
                alertInfo.setTitle("Alert");
                alertInfo.setHeaderText("Appointment Deleted");
                alertInfo.setContentText("Selected appointment has been deleted from database");
                alertInfo.showAndWait();
                break;
            case 4:
                alertInfo.setTitle("Alert");
                alertInfo.setHeaderText("Customer Deleted");
                alertInfo.setContentText("Selected customer has been deleted from database");
                alertInfo.showAndWait();
                break;
        }
    }

    /**
     * resets boxes for appropriate user interaction
     */
    public void clearData() {
        customerTable.getSelectionModel().clearSelection();
        customerIDTF.clear();
        customerNameTF.clear();
        customerAddressTF.clear();
        customerPostalCodeTF.clear();
        customerPhoneNumberTF.clear();
        customerCountryCB.setValue("");
        customerDivisionCB.setValue("");
    }

    /**
     * This method initializes the AddCustomerController.
     *
     * @param url The url is used to find the relative paths for the root object.
     * @param resourceBundle The resource bundle is used to localize the root object.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        ObservableList<Country> allCountries = CountriesQuery.getAllCountries();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        for (Country Country : allCountries) {
            countryNames.add(Country.getCountry());
        }
        customerCountryCB.setItems(countryNames);
        customerCountryCB.setEditable(true);
        customerCountryCB.getEditor().setEditable(false);
        ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
        ObservableList<String> divisionAllNames = FXCollections.observableArrayList();

        // Lambda #1 -
        allDivisions.forEach(Division -> divisionAllNames.add(Division.getDivisionName()));
        setCustomerOptionAddAndUpdateButtonActions();

        customerDivisionCB.setItems(divisionAllNames);
        customerDivisionCB.setItems(divisionAllNames);
        customerDivisionCB.setEditable(true);
        customerDivisionCB.getEditor().setEditable(false);
        try {
            addCustomerDataToTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * method references lambda #2 -- purpose is to confirm successful add/update and to simplify code in add and update
     * functions
     * @param string beginning part of total string ("Customer")
     * @param fn variable string based on which button is clicked (" added to database" or "updated in database"
     * @return appropriate user notification (ex. Customer added to database or Customer updated in database)
     */
    public String changeNotification(String string, Function<String, String> fn) {
        return fn.apply(string);
    }

    /**
     *  lambda #2 -- purpose is to confirm successful add/update and to simplify code in add and update functions
     */
    public void consoleNotification() {
        Function<String, String> fn = null;
        if (lastButton == 1) { fn = parameter -> parameter + " added to the Database"; }
        if (lastButton == 2) { fn = parameter -> parameter + " updated in database"; }
        String userNotification = changeNotification("Customer", fn);
        System.out.println(userNotification);
        lastButton = -1;
    }

    /**
     * sets button values for lambda #2
     */
    public void setCustomerOptionAddAndUpdateButtonActions() {
        saveCustomerBtn.setOnAction(e -> { lastButton = 1;
            try { saveCustomer();
        } catch (SQLException throwables)
            { throwables.printStackTrace();}});
        modifyCustomerBtn.setOnAction(e -> { lastButton = 2;
            try { modifyCustomer();
        } catch (SQLException throwables)
            { throwables.printStackTrace();}});
    }


}


/*    @FXML
    void onActionSaveCustomer(ActionEvent event) throws  IOException{

        try {
            String name = customerNameTxt.getText();
            String address = customerAddressTxt.getText();
            String postalCode = customerPCTxt.getText();
            String phone = customerPhoneTxt.getText();
            //Gets Division selected from Combobox
            Division division = customerDivisionCB.getSelectionModel().getSelectedItem();
            //Gets just the ID for the division
            int divId = division.getDivisionID();

            if (name.isEmpty()){
                displayAlert(2);
            } else {
                    int rowsAffected = CustomersQuery.insert(name, address, postalCode, phone);
                    if (rowsAffected > 0 ) {
                        Customer customer = new Customer(CustomersQuery.getMaxID(), name, address, postalCode, phone, divId);
                        ListManager.addCustomer(customer);
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                        scene.setStyle("-fx-font-family: 'SansSerif';");
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }
            } catch (Exception e) {
            displayAlert(1);
        }
    }*/

