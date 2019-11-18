package app.models.logic;

import app.models.Admin;
import com.cloudsgen.system.core.DataBase.CGModel;
import com.cloudsgen.system.core.DataBase.ICrud;
import io.ebean.Ebean;
import io.vertx.ext.web.RoutingContext;

public class AdminHelper extends CGModel implements ICrud{

    public AdminHelper(RoutingContext rx)
    {
        super(rx);
    }




    @Override
    public void add(Object tClass) {
        if(tClass instanceof Admin)
        {
            Admin c = (Admin) tClass;
            c.save();


        }
    }

    @Override
    public void edit(Object id ) {
        if(id instanceof Admin) {

            Admin c = (Admin) id;


            c.update();

        }
    }

    @Override
    public void delete(Object id) {


            Admin c = Ebean.getDefaultServer().find(Admin.class).where().eq("user_id" , id).findOne();
           if(c != null) {


               Ebean.getDefaultServer().delete(c);
           }

    }


    public boolean isAdmin(Object id){
        if(id instanceof String) {
            Admin c = Ebean.getDefaultServer().find(Admin.class).where().eq("user_id" , id).findOne();
            if(c != null) {
                return true;
            }else {
                return false;
            }


        }else {
            return false;
        }
    }

}
