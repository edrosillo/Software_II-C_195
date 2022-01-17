package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.time.LocalTime;
import java.util.ArrayList;

public abstract class ListManager {

    //Observable Array Lists see Inventory Class C482
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static ObservableList<Division> filteredDivisions = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> timeanddate = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> filteredtimeanddate = FXCollections.observableArrayList();
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ArrayList<Appointment> dailyAppoinmentsList = new ArrayList<>();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static void addUser(User user) { allUsers.add(user);}

    public static ObservableList<User> getAllUsers() {return allUsers; }

    public static void addCustomer(Customer customer) {allCustomers.add(customer); }

    public static ObservableList<Customer> getAllCustomers() {return allCustomers; }

    public static void addAppointment(Appointment appointment) { allAppointments.add(appointment);}

    public static ObservableList<Appointment> getAllAppointments() {return allAppointments; }

    public static Customer lookupCustomer(int customerID) {
        Customer customerSearch = null;

        for (Customer c : allCustomers) {
            if (c.getCustomerID() == customerID) {
                customerSearch = c;
                return customerSearch;
            }
        }
        return null;
    }


}
