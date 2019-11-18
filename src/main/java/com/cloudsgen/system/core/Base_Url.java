package com.cloudsgen.system.core;

import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.RandomStringUtils;

import static com.cloudsgen.system.utils.Security.SHA225;

public class Base_Url {

    private RoutingContext rx;
    private String burl;

    public Base_Url(RoutingContext rx)
    {
        this.rx = rx;
        if(this.rx.request().isSSL()) {
            this.burl = "https://"+this.rx.request().host().trim();
        }else {
            this.burl = "http://"+this.rx.request().host().trim();
        }
    }
    public String b(){

        return this.burl;
    }
    public String b(String p){

        return this.burl+"/"+p;
    }

    public  String getRandomNumberInRange() {
        int min = 100000;
        int max = 999999999;
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return SHA225(((Math.random() * ((max - min) + 1)) + min)+"") ;
    }


    public  String RandomString() {



        return  RandomStringUtils.randomAlphanumeric(8);
    }


}
