//Vincent Banks
//Exceptions Class
//COPYRIGHT Vincent Banks
package ThreeStrings.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class Exceptions {
    //Defines all custom exceptions for commandline commands
    // I couldn't figure out a better way to call the logger so its repeated a million times
    public static final class InvalidMenuChoice extends Exception{
        public InvalidMenuChoice(Class<?> clazz){
            Logger LOGGER = LoggerFactory.getLogger(clazz);
            LOGGER.error("You have picked a invalid menu choice!");
        }
    }
    public static final class ExitCodeCalled extends Exception{
        public ExitCodeCalled(Class<?> clazz){
            Logger LOGGER = LoggerFactory.getLogger(clazz);
            LOGGER.error("Exit code " + Codes.EXIT_CODE + " has been called!");
        }
    }
    public static final class InvalidYoutubeURL extends Exception{
        public InvalidYoutubeURL(Class<?> clazz){
            Logger LOGGER = LoggerFactory.getLogger(clazz);
            LOGGER.error("Invalid Youtube URL!");
        }
    }
    public static final class CommandNotFound extends Exception{
        public CommandNotFound(Class<?> clazz){
            Logger LOGGER = LoggerFactory.getLogger(clazz);
            LOGGER.error("Command does not exist!");
        }
    }
}
