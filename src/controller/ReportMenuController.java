package controller;

import helper.AppointmentQuery;
import helper.ContactsQuery;
import helper.CustomersQuery;
import helper.DivisionsQuery;
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
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class ReportMenuController implements Initializable {
    /**
     * The stage object that will be used to hold the GUI and data of the new screens.
     */
    Stage stage;

    /**
     * The scene object that will be used to generate the new screens.
     */
    Parent scene;

    @FXML
    private ToggleGroup sceneChangeTG;

    // Report 1 - Display Appointments Totals by Type and Month
    @FXML
    Tab totalApptsTab;
    // // Report View 1a - Display Appointments by Type
    @FXML
    TableView<ReportAppointmentType> appointmentsByTypeTable;
    @FXML
    TableColumn<ReportAppointmentType, String> totApptsTypeCol;
    @FXML
    TableColumn<ReportAppointmentType, Integer> totApptsTotalCol;

    // Report View 1b - Display Appointments by Month
    @FXML
    TableView<ReportAppointmentByMonth> appointmentsByMonthTable;
    @FXML
    TableColumn<ReportAppointmentByMonth, String> apptsByMonMonthCol;
    @FXML
    TableColumn<ReportAppointmentByMonth, Integer> apptsByMonTotalCol;

    // Report View 2 - Display Appointments by Contact
    @FXML
    Tab appointmentsByContactTab;
    @FXML
    TableView<Appointment> appointmentsByContactTable;
    @FXML
    TableColumn<Appointment, Integer> apptsByContactIDCol;
    @FXML
    TableColumn<Appointment, String> apptsByContactTitleCol;
    @FXML
    TableColumn<Appointment, String> apptsByContactTypeCol;
    @FXML
    TableColumn<Appointment, String> apptsByContactDescriptionCol;
    @FXML
    TableColumn<Appointment, LocalDateTime> apptsByContactStartCol;
    @FXML
    TableColumn<Appointment, LocalDateTime> apptsByContactEndCol;
    @FXML
    TableColumn<Appointment, Integer> apptsByContactCustomerIDCol;

    @FXML
    Label contactText;
    @FXML
    ComboBox<String> contactSelectCB;

    // Report View 3 - Show Customers by Division
    @FXML Tab customerByDivisionTab;
    @FXML TableView<ReportCustomerDivision> customerFirstLevelDivisionTableView;
    @FXML TableColumn<ReportCustomerDivision, String> custbyDivDivisionCol;
    @FXML TableColumn<ReportCustomerDivision, ArrayList<String>> custbyDivCustomersCol;


    /**
     * @param event on radio button press - switch from AppointmentForm to CustomerForm
     */
    public void goToCustomerRB(ActionEvent event) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event When the Radio Button is clicked the scene will change to Appointments Menu
     */
    public void goToAppointmentRB(ActionEvent event) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // report 1 -----
    /**
     * gets list of appointment types from all appointment data
     * gets list of appointment types without duplicates
     * gets total of type occurrences, creates instance of class and sets list for tableview
     *
     * @throws SQLException if exception has occurred
     */
    public void addAppointmentTypesTotals() throws SQLException {
        String typeToSet;
        int totalToSet;
        ObservableList<Appointment> allAppointmentData = AppointmentQuery.getAllAppointments();
        ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
        allAppointmentData.forEach(appointments -> appointmentTypes.add(appointments.getAppointmentType()));
        ObservableList<String> appointmentTypesNoDuplicates = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointmentData) {
            String type = appointment.getAppointmentType();
            if (!appointmentTypesNoDuplicates.contains(type)) {
                appointmentTypesNoDuplicates.add(type);
            }
        }
        ObservableList<ReportAppointmentType> appointmentReportTypes = FXCollections.observableArrayList();
        for (String type : appointmentTypesNoDuplicates) {
            int total = Collections.frequency(appointmentTypes, type);
            typeToSet = type;
            totalToSet = total;
            ReportAppointmentType appointmentType = new ReportAppointmentType(typeToSet, totalToSet);
            appointmentReportTypes.add(appointmentType);
        }
        appointmentsByTypeTable.setItems(appointmentReportTypes);
    }

    /**
     * gets list of appointment months from all appointment data
     * gets list of months without duplicates
     * gets total of month occurrences, creates instance of class and sets list for tableview
     *
     * @throws SQLException if exception has occurred
     */
    public void addAppointmentMonthsTotals() throws SQLException {
        String monthToSet;
        int totalToSet;
        ObservableList<Appointment> allAppointmentData = AppointmentQuery.getAllAppointments();
        ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointmentData) {
            Month month = appointment.getStart().getMonth();
            appointmentMonths.add(month);
        }
        ObservableList<Month> appointmentMonthsNoDuplicates = FXCollections.observableArrayList();
        for (Month month : appointmentMonths) {
            if (!appointmentMonthsNoDuplicates.contains(month)) {
                appointmentMonthsNoDuplicates.add(month);
            }
        }
        ObservableList<ReportAppointmentByMonth> appointmentReportMonths = FXCollections.observableArrayList();
        for (Month month : appointmentMonthsNoDuplicates) {
            int total = Collections.frequency(appointmentMonths, month);
            monthToSet = month.name();
            totalToSet = total;
            ReportAppointmentByMonth monthVar = new ReportAppointmentByMonth(monthToSet, totalToSet);
            appointmentReportMonths.add(monthVar);
        }
        appointmentsByMonthTable.setItems(appointmentReportMonths);
    }

    /**
     * combines above functions
     */
    public void addTypeMonthTotalAppointments() throws SQLException {
        addAppointmentTypesTotals();
        addAppointmentMonthsTotals();
    }

    // report 2 -----

    /**
     * shows selection box and label
     * sets contact names in comboBox
     *
     * @throws SQLException if exception has occurred
     */
    public void addAppointmentsByContact() throws SQLException {
        contactText.setVisible(true);
        contactSelectCB.setVisible(true);
        ObservableList<Contact> allContacts = ContactsQuery.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact contacts : allContacts) {
            String contactName = contacts.getContactName();
            contactNames.add(contactName);
        }
        contactSelectCB.setItems(contactNames);
    }

    /**
     * gets selected contact name from combo box
     * matches selected name with contact ID from all contact data
     * gets selected contact appointment data from all appointment data
     *
     * @throws SQLException if exception has occurred
     */
    public void addContactAppointmentData() throws SQLException {
        int selectedContactID = 0;
        Appointment selectedAppointmentData;
        String selectedContactName = contactSelectCB.getSelectionModel().getSelectedItem();
        if (selectedContactName != null) {

            ObservableList<Contact> allContactData = ContactsQuery.getAllContacts();
            for (Contact contact : allContactData) {
                if (selectedContactName.equals(contact.getContactName())) {
                    selectedContactID = contact.getId();
                }
            }
            ObservableList<Appointment> allAppointmentData = AppointmentQuery.getAllAppointments();
            ObservableList<Appointment> appointmentDataForSelectedContact = FXCollections.observableArrayList();
            for (Appointment appointment : allAppointmentData) {
                if (selectedContactID == appointment.getContactID()) {
                    selectedAppointmentData = appointment;
                    appointmentDataForSelectedContact.add(selectedAppointmentData);
                }
            }
            appointmentsByContactTable.setItems(appointmentDataForSelectedContact);
        }
    }

    // Report View 3 - Show Customers by Division

    /**
     * This method matches Division ID with Customer Division ID in order to prevent duplicates additions to the database.
     */
    public void addCustomersByDivision() throws SQLException {
        String divisionToSet ;
        ArrayList<String> customerListToSet;
        ReportCustomerDivision record;
        String previousSetDivisionName = "";

        ObservableList<Division> allFirstLevelDivisionData = DivisionsQuery.getAllDivisions();
        ObservableList<Customer> allCustomerData = CustomersQuery.getAllCustomers();
        ObservableList<ReportCustomerDivision> observableListToSet = FXCollections.observableArrayList();

        for (Division division : allFirstLevelDivisionData) {
            customerListToSet = new ArrayList<>();
            for (Customer customer : allCustomerData) {
                if (division.getDivisionID() == customer.divisionID) {
                    divisionToSet = division.getDivisionName();
                    customerListToSet.add(customer.getCustomerName());
                    record = new ReportCustomerDivision(divisionToSet, customerListToSet);
                    if (!record.getFirstLevelDivision().equals(previousSetDivisionName)) {
                        observableListToSet.add(record);
                        previousSetDivisionName = record.getFirstLevelDivision();
                    }
                }
            }
        }
        customerFirstLevelDivisionTableView.setItems(observableListToSet);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initial Status of Label and Combobox
        contactText.setVisible(false);
        contactSelectCB.setVisible(false);
        contactSelectCB.setEditable(true);
        contactSelectCB.getEditor().setEditable(false);

        // Report View 1a - Display Appointments by Type
        totApptsTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        totApptsTotalCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        // Report View 1b - Display Appointments by Month
        apptsByMonMonthCol.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        apptsByMonTotalCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        // Report View 2 - Display Appointments by Contact
        apptsByContactIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptsByContactTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptsByContactTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptsByContactDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptsByContactStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptsByContactEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptsByContactCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        // Report View 3 - Show Customers by Division
        custbyDivDivisionCol.setCellValueFactory(new PropertyValueFactory<>("firstLevelDivision"));
        custbyDivCustomersCol.setCellValueFactory(new PropertyValueFactory<>("customerList"));
    }

}

