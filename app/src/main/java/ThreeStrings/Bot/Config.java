//Vincent Banks
//Config Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.load(); //dotenv loads our config

    public static String get(String key){
        return dotenv.get(key);
    }
}
