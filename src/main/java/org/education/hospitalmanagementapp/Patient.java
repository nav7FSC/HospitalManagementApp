package org.education.hospitalmanagementapp;

/**
 * Represents a patient in the hospital management system.
 */
public class Patient {
    private int patientID;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String contactNumber;
    private String address;
    private int cost;
    private String services;

    /**
     * Constructs a Patient with the specified details.
     *
     * @param patientID     the unique ID of the patient
     * @param firstName     the first name of the patient
     * @param lastName      the last name of the patient
     * @param dateOfBirth   the date of birth of the patient (in YYYY-MM-DD format)
     * @param contactNumber the contact number of the patient
     * @param address       the address of the patient
     * @param cost          the total cost of services for the patient
     * @param services      the services received by the patient
     */
    public Patient(int patientID, String firstName, String lastName, String dateOfBirth,
                   String contactNumber, String address, int cost, String services) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.address = address;
        this.cost = cost;
        this.services = services;
    }

    /**
     * Returns a string representation of the patient's details.
     *
     * @return a string containing patient information
     */
    @Override
    public String toString() {
        return "Patient ID: " + patientID +
                ", First Name: " + firstName +
                ", Last Name: " + lastName +
                ", Date of Birth: " + dateOfBirth +
                ", Contact Number: " + contactNumber +
                ", Address: " + address +
                ", Cost: " + cost +
                ", Services: " + services;
    }

    // Getters and Setters
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
