package com.cloudsgen.system.core.icore;


import io.vertx.ext.web.RoutingContext;

import java.io.IOException;
import java.util.Map;

public interface iController {


    void inil(RoutingContext rxtx);

    void GlobalLoader(String sessionId);

    Map<String, String> GetExtraheader();

    void setExtraheader(String Key, String Val);

    int GetExtraCode();

    void action();

    void prosses();

    String Result();

    void index() throws IOException;

    void error();

    void finalize();
}