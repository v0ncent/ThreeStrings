//Vincent Banks
//Config Class
//COPYRIGHT Vincent Banks
package ThreeStrings;
import io.github.cdimascio.dotenv.Dotenv;
public class Config {
    private static final Dotenv dotenv = Dotenv.load(); //dotenv loads our config
    public static String get(String key){  //create constructor to access config
        return dotenv.get(key);
    } //creates method of getting keys prefixes,owner_id etc...
}
