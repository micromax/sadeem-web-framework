package com.cloudsgen.system.core;


import com.cloudsgen.system.core.icore.iSession;

public class Session implements iSession {


    /*
        @id Session id
        @data Session data
     */

    String id = null;
    Object data = null;


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public void setData(Object data) {

        this.data = data;
    }
}
