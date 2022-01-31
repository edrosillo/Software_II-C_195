package model;

public class ReportAppointmentByMonth {

    public String appointmentByMonth;
    public int appointmentByMonthTotal;

    /**
     * @param appointmentMonth the appointment month to set
     * @param appointmentTotal the appointment total to set
     */
    public ReportAppointmentByMonth(String appointmentMonth, int appointmentTotal) {
        this.appointmentByMonth = appointmentMonth;
        this.appointmentByMonthTotal = appointmentTotal;
    }

    /**
     * @return the appointment month
     */
    public String getAppointmentByMonth() {
        return appointmentByMonth;
    }

    /**
     *
     * @return the appointment total
     */
    public int getAppointmentByMonthTotal() {
        return appointmentByMonthTotal;
    }

}
