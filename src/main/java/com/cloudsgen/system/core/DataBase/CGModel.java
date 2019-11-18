package com.cloudsgen.system.core.DataBase;

import com.cloudsgen.system.core.Input;
import com.cloudsgen.system.core.Model;
import com.cloudsgen.system.core.load;
import io.vertx.ext.web.RoutingContext;

public class CGModel extends Model {
    public load load = new load();/**/

    public boolean isLook() {
        return look;
    }

    public void setLook(boolean look) {
        this.look = look;
    }

    private boolean look = true;
    private RoutingContext rx;
    public Input input ;
    public RoutingContext getRx() {
        return rx;
    }

    public void setRx(RoutingContext rx) {
        this.rx = rx;
    }

    public CGModel(RoutingContext rx){
        super(rx);
        this.setRx(rx);


            input =  new Input(rx);




    }


    public void Redirect(String path){
        this.rx.response()
                .putHeader("location","/"+path)
                .setStatusCode(301).end();

        this.rx.response().close();
    }

}
