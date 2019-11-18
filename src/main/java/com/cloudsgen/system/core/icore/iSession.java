package com.cloudsgen.system.core.icore;

public interface iSession {

    /*
        @id Session id
        @data Session data
     */

    String id = null;
    Object data = null;


    String getId();

    void setId(String id);

    Object getData();

    void setData(Object data);
}
