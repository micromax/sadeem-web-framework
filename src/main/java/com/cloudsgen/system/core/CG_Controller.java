package com.cloudsgen.system.core;

import com.cloudsgen.system.core.icore.iController;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CG_Controller implements iController {

    public load load = new load();/**/
    //public HttpServerResponse response;


    public boolean isRunError() {
        return RunError;
    }

    public void setRunError(boolean runError) {
        RunError = runError;
    }

    private boolean RunError = false;

    public RoutingContext getRxtx() {
        return rxtx;
    }

    public void setRxtx(RoutingContext rxtx) {
        this.rxtx = rxtx;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private RoutingContext rxtx;
    private String URL;
    public Session session ;

    public Input input ;

    public CG_Controller(RoutingContext rxtx) {
        this.inil(rxtx);
        input =  new Input(getRxtx());
    }



    @Override
    public void inil(RoutingContext rxtx) {
        this.rxtx = rxtx;
        this.URL = this.rxtx.request().absoluteURI();
        this.session = rxtx.session();
        this.rxtx.put("base" , new Base_Url(this.rxtx));


    }

    @Override
    public void GlobalLoader(String sessionId) {

    }

    @Override
    public Map<String, String> GetExtraheader() {
        return null;
    }

    @Override
    public void setExtraheader(String Key, String Val) {

    }

    @Override
    public int GetExtraCode() {
        return 0;
    }

    @Override
    public void action() {

    }

    @Override
    public void prosses() {

    }

    @Override
    public String Result() {
        return null;
    }

    @Override
    public void index() throws IOException {

    }


    public void invoker(String MethodName) throws IllegalAccessException, InstantiationException, InvocationTargetException {


        try {
            Method method = this.getClass().getDeclaredMethod(MethodName);
            method.invoke(this);
        } catch (NoSuchMethodException e) {

            try {
                Method method = this.getClass().getSuperclass().getDeclaredMethod("error");
                method.invoke(this);
            } catch (NoSuchMethodException e2) {

            }
        }


    }

    @Override
    public void error() {
            Render(this.load.View("templates/lost.ftl", this.getRxtx()));
    }

    public void Render(String s , HashMap<String,String> header) {
        if(this.getRxtx().response().headWritten() == false) {

        HttpServerResponse response = this.getRxtx().response();
        if(header != null)
        {
            for(Map.Entry<String, String> hea : header.entrySet())
            {
                    response.putHeader(hea.getKey() , hea.getValue());
            }
        }
        response.end(s);
            //response.end();
        }
    }

    public void Render(String s ) {
        if(this.getRxtx().response().headWritten() == false && this.getRxtx().response().closed() == false) {
            HttpServerResponse response = this.getRxtx().response();
            response.end(s);
            response.close();
            //response.end();
        }
    }


    public void Redirect(String path){
        this.getRxtx().response().putHeader("location","/"+path).setStatusCode(301).end();
       // this.getRxtx().response().close();

    }



    @Override
    public void finalize() {
        //      DatabaseController db = this.getRxtx().get("db");
        //        db.close();

        if(this.getRxtx().response().closed() == false){
//            this.getRxtx().response().end();

          //  this.getRxtx().response().close();
          //  System.out.println("Finalized closed");
        }

    }
}
