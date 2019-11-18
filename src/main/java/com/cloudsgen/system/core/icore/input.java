package com.cloudsgen.system.core.icore;


import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

class Input extends  RoutingContext {



    private JsonObject _POST = null;
    private HttpServerRequest _GET = null;








    @Override
    public Throwable failure() {
        return super.failure();
    }

    public Input(io.vertx.ext.web.RoutingContext delegate) {
        super(delegate);


        this._GET = delegate.request();
        this._POST = delegate.getBodyAsJson();


    }

    public String GET(String Key)
    {
        return this._GET.getParam(Key);
    }

    public String POST(String Key){

        if(this._POST.containsKey(Key))
        {
            return this._POST.getString(Key);
        }
        return null;
    }



}
