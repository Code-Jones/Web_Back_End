package Models;

public class AccountService {
    String username;
    String password;
    Boolean isLoggedIn;

    public AccountService(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
    }

    public AccountService() {
        this.username = "abe";
        this.password = "password";
        this.isLoggedIn = false;
    }
    
    public boolean login(String username, String Password) {
        if (username.equals(this.username) && Password.equals(this.password)){
            this.isLoggedIn = true;
            return true;
        } else return false;
    } 
    
    public void logout() {
        this.isLoggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLoggedIn() {
        return isLoggedIn;
    }  
}
