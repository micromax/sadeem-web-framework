package com.cloudsgen.system.core.DataBase;

import java.io.*;

import java.util.Enumeration;
import java.util.Properties;

public class DataBaseConfig {
    private DataConMeta DataBaseConf  = new DataConMeta();
    private InputStream inputStream;

    public DataConMeta getDataBaseConf() {
        return DataBaseConf;
    }

    public void setDataBaseConf(DataConMeta dataBaseConf) {
        DataBaseConf = dataBaseConf;
    }

    public DataBaseConfig() {

        String applicationconfigfile  = "database.properties";
        File outer = new File("database.properties");
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

                Enumeration<String> enums = (Enumeration<String>) props.propertyNames();


                while (enums.hasMoreElements()) {
                    String key = enums.nextElement();
                    String value = props.getProperty(key);
                    System.out.println(key + " : " + value);
                    switch (key)
                    {
                        case "DBUrl":
                            DataBaseConf.setUrl(value);
                            break;
                        case "DriverClassName":
                            DataBaseConf.setDriver(value);
                            break;
                        case "DBUserName":
                            DataBaseConf.setUsername(value);
                            break;
                        case "DBPassWord":
                            DataBaseConf.setPassword(value);
                            break;

                    }
                }




                //




                inputStream.close();

            } else {
                throw new FileNotFoundException("property file '" + applicationconfigfile + "' not found in the classpath");
            }


        } catch (FileNotFoundException ex) {
            // file does not exist
        } catch (IOException ex) {
            // I/O error
        }


    }
}



