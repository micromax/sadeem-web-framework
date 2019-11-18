package app.models.logic;

import app.models.Admin;
import app.models.finder.AuthModel;
import com.cloudsgen.system.core.DataBase.CGModel;
import io.vertx.ext.web.RoutingContext;

public class AdminAuth  extends CGModel {






    public AdminAuth(RoutingContext rx) {
        super(rx);
        String Tokin =  this.input.GetCookie("_cgmain");
        String UUID =  this.input.GetCookie("_cgalphas");

        String UserIp = this.input.getAgentIp();
        AuthModel authModel  = new AuthModel();

        Admin admin = null;
        if(Tokin == null || UUID == null)
        {
            this.setLook(true);
            Redirect("login");
        }else {


            long lid = 0;

            try {
                lid = Long.parseLong(UUID);
            }catch (NumberFormatException ex)
            {

               lid = 0 ;
            }

            try {
                admin = authModel.byIdAndTookenAndIp(lid , Tokin , UserIp);
                if(admin == null)
                {


                    Redirect("login");
                }else
                {
                    this.setLook(false);

                }
            }catch ( ExceptionInInitializerError e)
            {
                Redirect("syserrors/misconf");
            }


        }




        System.out.println(this.isLook());
    }









}
