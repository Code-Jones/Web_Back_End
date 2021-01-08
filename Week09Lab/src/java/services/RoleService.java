package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;

/**
 * Class that calls the RoleDB methods
 */
public class RoleService {
    
    /**
     * Method that calls getAll() in RoleDB
     * @return the List of Users
     * @throws Exception if there is a Exception with PreparedStatements and ResultSets
     */
    public List<Role> getAll() throws Exception {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }
}
