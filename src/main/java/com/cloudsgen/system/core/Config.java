package com.cloudsgen.system.core;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class Config {


    private HashMap<String , String>  ServerConf = new HashMap<>();



    private InputStream inputStream;
    public Config() {

        String applicationconfigfile  = "application.properties";

        File outer = new File("application.properties");
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





            System.out.println(props.getProperty("port"));
            this.ServerConf.put("hostname" , props.getProperty("hostname"));
            this.ServerConf.put("port" , props.getProperty("port"));
            this.ServerConf.put("ReuseAddress" , props.getProperty("ReuseAddress"));
            this.ServerConf.put("ReusePort" , props.getProperty("ReusePort"));
            this.ServerConf.put("Handle100ContinueAutomatically" , props.getProperty("Handle100ContinueAutomatically"));
            this.ServerConf.put("TcpNoDelay" , props.getProperty("TcpNoDelay"));
            this.ServerConf.put("TcpFastOpen" , props.getProperty("TcpFastOpen"));
            this.ServerConf.put("TcpQuickAck" , props.getProperty("TcpQuickAck"));
            this.ServerConf.put("MaxChunkSize" , props.getProperty("MaxChunkSize"));
            this.ServerConf.put("TcpKeepAlive" , props.getProperty("TcpKeepAlive"));
            this.ServerConf.put("disableFileCaching" , props.getProperty("disableFileCaching"));
            this.ServerConf.put("MainController" , props.getProperty("MainController"));


           /* this.ServerConf.put("db.JdbcUrl" , props.getProperty("db.JdbcUrl"));
            this.ServerConf.put("db.DriverClassName" , props.getProperty("db.DriverClassName"));
            this.ServerConf.put("db.Username" , props.getProperty("db.Username"));
            this.ServerConf.put("db.Password" , props.getProperty("db.Password"));
*/


            //




            inputStream.close();

        } catch (FileNotFoundException ex) {
            // file does not exist
        } catch (IOException ex) {
            // I/O error
        }



    }

    public HashMap<String, String> getServerConf() {
        return ServerConf;
    }

    public void setServerConf(HashMap<String, String> serverConf) {
        ServerConf = serverConf;
    }






}
