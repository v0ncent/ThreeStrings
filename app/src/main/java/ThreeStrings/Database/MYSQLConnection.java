//Vincent Banks
//MYSQLConnection CLass
//COPYRIGHT Vincent Banks
package ThreeStrings.Database;
import ThreeStrings.Config;
import ThreeStrings.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MYSQLConnection {
    public static Connection getConnection() throws Exception{
        final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
        try{
            //Create Login Information
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = Config.get("DATABASE");
            String userName = Config.get("DB_USERNAME");
            String password = Config.get("PASSWORD");
            //Access Driver
            Class.forName(driver);
            //Connect to database
            Connection connect = DriverManager.getConnection(url,userName,password);
            LOGGER.info("Database connection secured.");
            return connect;
        }catch (Exception e){ //if you cannot connect
            e.printStackTrace();
        }
        return null;
    }
}
