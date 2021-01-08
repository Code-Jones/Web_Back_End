package services;

import dataaccess.UserDB;
import java.util.List;
import models.User;

/**
 * Class that calls the UserDB methods
 */
public class UserService {

    /**
     * Method that calls getAll() in UserDB
     *
     * @return the List of Users
     * @throws Exception if there is a Exception with PreparedStatements and
     * ResultSets
     */
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    /**
     * Method that calls get() in UserDB and forwards the email to search for
     *
     * @param email the email of the user to search for
     * @return the User found
     * @throws Exception if there is a Exception with PreparedStatements and
     * ResultSets
     */
    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

    /**
     * Method that calls insert() in UserDB
     *
     * @param user the user to be inserted
     * @throws Exception if there is a Exception with PreparedStatements and
     * ResultSets
     */
    public void insert(User user) throws Exception {
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    /**
     * Method that calls update() in UserDB
     *
     * @param user the user to be updated
     * @throws Exception if there is a Exception with PreparedStatements and
     * ResultSets
     */
    public void update(User user) throws Exception {
        UserDB userDB = new UserDB();
        userDB.update(user);
    }

    /**
     * Method that calls delete() in UserDB
     *
     * @param email the email of the user to be deleted
     * @throws Exception if there is a Exception with PreparedStatements and
     * ResultSets
     */
    public void delete(String email) throws Exception {        
        UserDB userDB = new UserDB();        
        User user = userDB.get(email);         
        userDB.delete(user);
    }
}
