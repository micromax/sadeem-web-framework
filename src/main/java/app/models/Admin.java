package app.models;


import io.ebean.Ebean;
import io.ebean.Model;
import io.ebean.annotation.Index;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="admin")
public class Admin extends Model {


    @Id
    public long userId;


    @Index(unique=true)
    public String userName;

    public String userPassword;

    public Timestamp dateCreated;

    public String lastIP;

    public String token;

    public Timestamp lastLogin;

    public String getAPI_token() {
        return API_token;
    }

    public void setAPI_token(String API_token) {
        this.API_token = API_token;
    }

    public String API_token;

    public String getAPI_Key() {
        return API_Key;
    }

    public void setAPI_Key(String API_Key) {
        this.API_Key = API_Key;
    }

    public String API_Key;

    public Admin() {
        super();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastIP() {
        return lastIP;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Admin  finder(long id){
        return Ebean.find(Admin.class , id);

    }
}
