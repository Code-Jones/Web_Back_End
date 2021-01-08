package models;

import java.io.Serializable;

/**
 * This class is a javabean
 * It has a default constructor, user-defined constructor and getters and setter's for all fields
 */
public class Role implements Serializable {
    
    private int roleID;
    private String roleName;

    /**
     * Defined default constructor for Role Java-Bean
     */
    public Role() {}

    /**
     * User-Defined constructor for Role Java-Bean
     * 
     * @param roleID the ID of the role
     * @param roleName the String name of the role
     */
    public Role(int roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    /**
     * Role ID getter method
     * @return the roles ID
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * Role ID setter method
     * @param roleID the new value for the roles ID
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    /**
     * Role name getter method
     * @return the String value of the role
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Role name setter method
     * @param roleName the new String value for the roles name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
}
