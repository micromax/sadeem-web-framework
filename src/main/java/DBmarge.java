
import io.ebean.annotation.Platform;
import io.ebean.dbmigration.DbMigration;

import java.io.IOException;

public class DBmarge {


    public static void main(String arg[]){



        try {
            DbMigration dbMigration = DbMigration.create();
            dbMigration.setPlatform(Platform.MYSQL);
            dbMigration.generateMigration();
        } catch (IOException e) {
            e.printStackTrace();
        }


          }
}

