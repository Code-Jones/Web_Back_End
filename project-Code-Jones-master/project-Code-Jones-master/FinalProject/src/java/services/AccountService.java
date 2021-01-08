package services;

import dataaccess.UserDB;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import models.User;
import utilities.PasswordAuth;

/**
 *
 * @author 786524
 */
public class AccountService {

    public void sendEmail(String email, String subject, String path, HashMap<String, String> tags) throws Exception {
        GmailService.sendMail(email, subject, path, tags);
    }

    public void resetPassword(String email, String path, String url) {
        UserDB udb = new UserDB();
        try {
            User user = udb.get(email);

            String uuid = UUID.randomUUID().toString();
            user.setResetPasswordUUID(uuid);
            udb.update(user);

            String newPath = path + "/templates/resetpassword.html";
            String link = url + "?verification=" + uuid;
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("email", email);
            tags.put("link", link);

            sendEmail(user.getEmail(), "Reset Password", newPath, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean changePassword(String verification, String password) {
        UserDB udb = new UserDB();
        try {
            User user = udb.getByUUID(verification);
            user.setPassword(password);
            user.setResetPasswordUUID(null);
            return udb.update(user);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void sendLoginEmail(String email, String subject, String path) {
        UserDB udb = new UserDB();
        try {
            User user = udb.get(email);
            String newPath = path + "/templates/login.html";
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("date", (new Date()).toString());
            GmailService.sendMail(email, subject, newPath, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerUser(String email, String subject, String path, String url, String password, String first, String last) {
        try {
            User user = new User(email, true, first, last, password);

            String newPath = path + "/templates/registration.html";
            String link = url + "?verification=true&email=" + email + "&password=" + password + "&first=" + first + "&last=" + last;

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("email", email);
            tags.put("link", link);
            GmailService.sendMail(email, subject, newPath, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendSignedUpEmail(String email, String subject, String path, String firstname, String lastname) {
        try {
            String newPath = path + "/templates/signedup.html";

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", firstname);
            tags.put("lastname", lastname);
            tags.put("email", email);
            
            GmailService.sendMail(email, subject, newPath, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
