package models;

import java.io.Serializable;

/*
    This class is javabean
    It has a default constructor, user-defined constructor and getters and setter's for all fields
 */
public class User implements Serializable{
    private String email;
    private boolean active;
    private String firstName;
    private String lastName;
    private String password;
    private int role;

    /**
     * Defined default constructor for User Java-Bean
     */
    public User() {}

    /**
     * User-Defined constructor for User Java-Bean
     * 
     * @param email the email of the user
     * @param active boolean value for if the user is active 
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param password the password of the user
     * @param role the integer value of the users role
     */
    public User(String email, boolean active, String firstName, String lastName, String password, int role) {
        this.email = email;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    /**
     * User email getter method
     * @return users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * User email setter method
     * @param email the new value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for if the user is active
     * @return if the user is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter method for if user is active
     * @param active the new boolean value for if the user is active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * User first name getter method
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * User first name setter method
     * @param firstName the new value for the users first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * User last name getter method
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * User last name setter method
     * @param lastName the new value for the users last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * User password getter method
     * @return the users password
     */
    public String getPassword() {
        return password;
    }

    /**
     * User password setter method
     * @param password the new value for the users password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * User role getter method
     * @return the role of the user represented by the integer value
     */
    public int getRole() {
        return role;
    }
    
    /**
     * User role setter method
     * @param role the new integer value for the users role
     */
    public void setRole(int role) {
        this.role = role;
    }
}
