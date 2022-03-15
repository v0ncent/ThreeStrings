//Vincent Banks
//SQLiteDataSource Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Database;
import ThreeStrings.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class SQLiteDataSource {
    //create logger for class
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteDataSource.class);
    //create Hikari Config
    private static final HikariConfig config = new HikariConfig();
    //create data source
    private static final HikariDataSource ds;
    static{
        try{
            //create database file
            final File dbFile = new File("database.db");
            if(!dbFile.exists()){//if file does not exist
                if(dbFile.createNewFile()){
                    LOGGER.info("Created DB File");//create file
                }else{
                    //if file cannot be made
                    LOGGER.info("Could Not Create DB File");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        config.setJdbcUrl("jdbc:sqlite:database.db"); //set file path
        config.setConnectionTestQuery("SELECT 1"); //test
        config.addDataSourceProperty("cachePrepStmts",true);
        config.addDataSourceProperty("prepStmtCacheSize",250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit","2048");
        ds = new HikariDataSource(config);
        try(final Statement statement = getConnection().createStatement()){
            final String defaultPrefix = Config.get("PREFIX");
            // language=SQLite
            statement.execute("CREATE TABLE IF NOT EXISTS guild_settings (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "guild_id VARCHAR(20) NOT NULL,"+
                    "prefix VARCHAR(255) NOT NULL DEFAULT '"+defaultPrefix+"'" +
                    ");");
            LOGGER.info("Table initialized");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    //class constructor (private so no new instances of this class are created)
    private SQLiteDataSource(){
    }
    //getConnection method
    public static Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
}
