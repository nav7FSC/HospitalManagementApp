package org.education.hospitalmanagementapp.services;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an appointment in the hospital management system.
 */
public class Appointment {
    private int appointmentID;
    private int patientID;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String patientFirstName;
    private String patientLastName;
    private String doctorFirstName;
    private String doctorLastName;
    private String appointmentType;

    /**
     * Constructs an Appointment with the specified details.
     *
     * @param appointmentID      the unique ID of the appointment
     * @param patientID          the ID of the patient
     * @param appointmentDate    the date of the appointment
     * @param appointmentTime    the time of the appointment
     * @param patientFirstName   the first name of the patient
     * @param patientLastName    the last name of the patient
     * @param doctorFirstName    the first name of the doctor
     * @param doctorLastName     the last name of the doctor
     * @param appointmentType    the type of the appointment (e.g., consultation)
     */
    public Appointment(int appointmentID, int patientID, LocalDate appointmentDate, LocalTime appointmentTime,
                       String patientFirstName, String patientLastName, String doctorFirstName,
                       String doctorLastName, String appointmentType) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.appointmentType = appointmentType;
    }

    /**
     * @return the unique ID of the appointment
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return the ID of the patient
     */
    public int getPatientID() {
        return patientID;
    }

    /**
     * @return the date of the appointment
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @return the time of the appointment
     */
    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * @return the first name of the patient
     */
    public String getPatientFirstName() {
        return patientFirstName;
    }

    /**
     * @return the last name of the patient
     */
    public String getPatientLastName() {
        return patientLastName;
    }

    /**
     * @return the first name of the doctor
     */
    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    /**
     * @return the last name of the doctor
     */
    public String getDoctorLastName() {
        return doctorLastName;
    }

    /**
     * @return the type of the appointment (e.g., consultation)
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Sets the unique ID of the appointment.
     *
     * @param appointmentID the new appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Sets the ID of the patient.
     *
     * @param patientID the new patient ID
     */
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param appointmentDate the new appointment date
     */
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Sets the time of the appointment.
     *
     * @param appointmentTime the new appointment time
     */
    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Sets the first name of the patient.
     *
     * @param patientFirstName the new patient first name
     */
    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    /**
     * Sets the last name of the patient.
     *
     * @param patientLastName the new patient last name
     */
    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    /**
     * Sets the first name of the doctor.
     *
     * @param doctorFirstName the new doctor first name
     */
    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    /**
     * Sets the last name of the doctor.
     *
     * @param doctorLastName the new doctor last name
     */
    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    /**
     * Sets the type of the appointment.
     *
     * @param appointmentType the new appointment type
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Returns a string representation of the appointment.
     *
     * @return a string containing the appointment details
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", patientID=" + patientID +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", patientFirstName='" + patientFirstName + '\'' +
                ", patientLastName='" + patientLastName + '\'' +
                ", doctorFirstName='" + doctorFirstName + '\'' +
                ", doctorLastName='" + doctorLastName + '\'' +
                ", appointmentType='" + appointmentType + '\'' +
                '}';
    }
}
