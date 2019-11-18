package com.cloudsgen.system.core.controller;

import com.cloudsgen.system.core.CG_Controller;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;

public class Error extends CG_Controller {



    public void index() throws IOException {
        super.index();

    //  this.getRxtx().response().setStatusCode(404).end("Not founde");
        Render(this.load.View("templates/lost.ftl", this.getRxtx()));
    }
    public void misconf(){

        Render(this.load.View("templates/mis.ftl", this.getRxtx()));
    }
    public Error(RoutingContext rxtx) {
        super(rxtx);

        Render(this.load.View("templates/lost.ftl", rxtx));
    }

}
