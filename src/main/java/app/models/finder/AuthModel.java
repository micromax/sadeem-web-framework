package app.models.finder;

import app.models.Admin;
import io.ebean.Ebean;
import io.ebean.Finder;

import java.util.List;

public class AuthModel extends Finder<Long, Admin>  {

    private String Username;
    private String Password;
    private int isValid;





    public AuthModel(){
        super(Admin.class);

    }
    public Admin byId(long username)
    {
        return  query().where().eq("user_id" , username ).findOne();
    }

    public Admin byName(String username)
    {
        return  query().where().eq("user_name" , username ).findOne();
    }


    public Admin byNameAndPassword(String un , String pw) {
        return Ebean.getDefaultServer().find(Admin.class).where()
                .eq("user_name", un)
                .and()
                .eq("user_password" , pw)
                .findOne();
    }


    public Admin byIdAndTookenAndIp(long id , String token , String ip)  {
        return Ebean.getDefaultServer().find(Admin.class).where()
                .eq("user_id", id)
                .and()
                .eq("token" , token)
                .and()
                .eq("last_ip" , ip)
                .findOne();
    }

    public Admin byAPI( String token , String key) {
        return Ebean.getDefaultServer().find(Admin.class).where()
                .eq("api_token", token)
                .and()
                .eq("api_key" , key)
                .findOne();
    }
    public List<Admin> GetAll()
    {
        return  query().findList();
    }
    public int GetAllCount()
    {
        return  query().findList().size();
    }

}
