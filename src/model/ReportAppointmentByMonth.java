package model;

public class ReportAppointmentByMonth {

    public String appointmentMonth;
    public int appointmentTotalMonth;

    /**
     * @param appointmentMonth the appointment month to set
     * @param appointmentTotalMonth the appointment total to set
     */
    public ReportAppointmentByMonth(String appointmentMonth, int appointmentTotalMonth) {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotalMonth = appointmentTotalMonth;
    }

    /**
     * @return the appointment month
     */
    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    /**
     *
     * @return the appointment total
     */
    public int getAppointmentTotalMonth() {
        return appointmentTotalMonth;
    }

}
