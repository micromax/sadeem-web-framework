package com.cloudsgen.system.core.icore;


import com.cloudsgen.system.core.Session;

import java.util.ArrayList;

public interface iSessionStorge {

    ArrayList<Session> Sessions = new ArrayList<>();

    Session getSessionById(int id);

    ArrayList<Session> getSessionStorge();

    boolean addSession(Session sessions);

    boolean deleteSessionByid(Session session);


}