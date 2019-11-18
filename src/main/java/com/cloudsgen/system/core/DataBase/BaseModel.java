package com.cloudsgen.system.core.DataBase;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;

//Singleton Pattern class
public class BaseModel  {

    private volatile static  BaseModel ourInstance = new BaseModel();
    private volatile static DataConMeta DatabaseConfigration = new DataBaseConfig().getDataBaseConf();

    public DSLContext getCreated() {
        return DB;
    }

    public void setCreated(DSLContext created) {
        this.DB = created;
    }

    public  DSLContext DB;

    public static HikariConfig config = new HikariConfig();

    public static HikariDataSource datasource;



    public static  BaseModel getInstance() {

        if (null == ourInstance)
        {
            System.out.println("new ourInstance");
            synchronized(BaseModel.class)
            {
                if (null == ourInstance)
                {
                    System.out.println("new ourInstance");
                    ourInstance = new BaseModel();
                }
            }

        }
        System.out.println("old ourInstance");
        return ourInstance;
    }

    public BaseModel() {


    }






    public void close() {
        try {
            this.datasource.getConnection().close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


}
