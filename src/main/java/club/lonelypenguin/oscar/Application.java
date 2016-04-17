package club.lonelypenguin.oscar;

import club.lonelypenguin.oscar.util.ImportAccount;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import java.io.File;
/**
 * Created by dbundgaard on 2016-04-15.
 */
public class Application {

    public static final Logger log = Logger.getLogger(Application.class);

    public static void main(String args[]){
        BasicConfigurator.configure();
        String filename = args[0];
        if(new File(filename).isFile()){
          ImportAccount account = new ImportAccount();
          if(account.Import()){
              log.info("Weehaw imported successfully");
          } else {
              log.error("Darn it, nothing imported");
          }
        }
    }
}
