package com.cloudsgen.system.core;


import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.DecodeException;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;

public class Input {



    private MultiMap _POST = null;
    private HttpServerRequest _GET = null;

    private String AgentIp;

    private  RoutingContext rx ;








    public Input(RoutingContext delegate) {

        try {

            this.rx = delegate;
            this._GET = delegate.request();
            this._POST = delegate.request().formAttributes();
            this.AgentIp = delegate.request().remoteAddress().host().toString();
            //System.out.println(this._POST);
            System.out.println("RX : "+this.rx.toString());
        }catch (DecodeException e)
        {
            e.printStackTrace();
        }

    }

    public String GET(String Key)
    {
        return this._GET.getParam(Key);
    }

    public String POST(String Key){

        if(this._POST.contains(Key))
        {
            return this._POST.get(Key);
        }
        return null;
    }

        public String REQUEST(String Key){

                String _rr = null;
                    _rr = this.POST(Key);
                if(_rr == null )
                {
                    _rr = this.GET(Key);
                }
            return _rr;
        }
    public String Signal(int n){
        String p = this.rx.request().path();

        String[] pa = p.split("/");
        if(pa.length > n && pa[n] != null)
        {
            return pa[n];
        }else {
            return null;
        }
    }


    public String getAgentIp() {
        return AgentIp;
    }
    public void setCookie(String CookieName , String CookieVal ){


        Cookie cookie = Cookie.cookie(CookieName, CookieVal);
        cookie.setPath("/");
        cookie.setMaxAge(18000);
        this.rx.addCookie(cookie);
    }


    public String GetCookie(String CookieName)
    {

        String r = null;
        try {
            r = this.rx.getCookie(CookieName).getValue();
        }catch (NullPointerException e){

        }
        return r;
    }



    public boolean RemoveCookie(String CookieName)
    {
        Boolean r = false;

        try {

            Cookie rk = this.rx.removeCookie(CookieName);
            if(rk != null)
            {


                rk.setMaxAge(0);
                r = true;
            }else {
                r = false;
            }
        }catch (Exception e)
        {
            r = false;
        }


        return  r;
    }

    public void setSession(String name , String Value){






    }
    public void setAgentIp(String agentIp) {
        AgentIp = agentIp;
    }
}
