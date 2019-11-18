package com.cloudsgen.system.core;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RouterManager {


    private Map<String , String> RouterMap = new HashMap<>();

    private InputStream inputStream;

    public Map<String, String> getRouterMap() {
        return Collections.unmodifiableMap(RouterMap);
    }

    public void setRouterMap(HashMap<String, String> routerMap) {
        RouterMap = routerMap;
    }

    public RouterManager() {



        String applicationconfigfile  = "route.properties";
        File outer = new File("route.properties");
        boolean exists = outer.exists();

        try {
            if(exists == true)
            {
                inputStream = new FileInputStream(outer);
            }else
            {
                inputStream = getClass().getClassLoader().getResourceAsStream(applicationconfigfile);
            }
            Properties props = new Properties();

            if (inputStream != null) {
                props.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + applicationconfigfile + "' not found in the classpath");
            }




            this.RouterMap.put("syserrors" , "com.cloudsgen.system.core.controller.Error");

            for (final String name: props.stringPropertyNames())
            {
                this.RouterMap.put(name,  props.getProperty(name));
                System.out.println(name + " : " + props.getProperty(name));
            }
            //




            inputStream.close();

        } catch (FileNotFoundException ex) {
            // file does not exist
        } catch (IOException ex) {
            // I/O error
        }



    }
}
