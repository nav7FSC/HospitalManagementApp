package org.education.hospitalmanagementapp;

import java.util.Arrays;

/**
 * Represents a user in the Hospital Management App with attributes for personal and account information.
 */
public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private byte[] profilePicture;

    /**
     * Default constructor for creating an empty User object.
     */
    public User(){

    }

    /**
     * Parameterized constructor for creating a User object with specific details.
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param userName the user's username
     * @param email the user's email address
     * @param password the user's password
     */
    public User(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the first name of the user.
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the profile picture of the user.
     * @return the user's profile picture as a byte array
     */
    public byte[] getProfilePicture() {
        return profilePicture;
    }

    /**
     * Sets the profile picture of the user.
     * @param profilePicture the profile picture to set as a byte array
     */
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * Sets the first name of the user.
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the password of the user.
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email address of the user.
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the username of the user.
     * @return the user's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}