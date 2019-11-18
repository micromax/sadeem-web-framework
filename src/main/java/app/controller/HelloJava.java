package app.controller;


import com.cloudsgen.system.core.CG_Controller;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloJava extends CG_Controller {



    public HelloJava(RoutingContext rxtx) {
        super(rxtx);
    }

    /**
     *   index : this default method for every controller
     *   Render :  will Print any thing to response
     *   simply this will print $String "Hello world ! from java"
     *
     * @return void
     * @throws IOException
     */
    @Override
    public void index() throws IOException
    {
        super.index();
        Render("Hello world ! from java");
    }




    public void templetwithdata(){


        //push List to view :-) you can push any type of data this just demonstration
        List<String> data = new ArrayList<>();

        data.add("free");
        data.add("open source");
        data.add("Framework from sadeem-egypt.com");
        data.add("Support Kotlin , java , Scala in same project ");
        data.add("Actor and Vertical from AKKA and vertx.io are supported");
        data.add("35,0000 thousand message per second , high performance concurrent built-in TCP server");
        //push simple String var to view
        this.getRxtx().put("title", "This clouds-gen.com Framework  from <a href='http://sadeem-egypt.com'>Sadeem-egypt.com </a>");
        this.getRxtx().put("features", data);

        //hellomaster.ftl is master view include tiny view's and partial views

        //this.load.View( viwe path , we pass always this.getRxtx() to view this will make all data available

        Render(this.load.View("views/hellomaster.ftl", this.getRxtx()));

    }



    public void simpleform(){


        /**
         * you could use this.input.POST or this.input.GET or even this.input.REQUEST() to get data
         * REQUEST will work for both GET and POST :-)
         */

        String username = this.input.POST("username");
        String password = this.input.POST("password");
        this.input.setCookie("username", username);
        this.input.setCookie("password", password);


        String Cusername = this.input.GetCookie("username");
        String Cpassword = this.input.GetCookie("password");

        Render("You Post user name : " + Cusername
                +" and password : "+Cpassword +"this data from Cookie's ");




    }





}
