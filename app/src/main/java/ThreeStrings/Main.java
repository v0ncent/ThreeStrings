//Vincent Banks
//Main Class
//COPYRIGHT Vincent Banks
package ThreeStrings;
import javax.security.auth.login.LoginException;
public final class Main {
    /*
    -------THREESTRINGS MAIN METHOD--------
    This class must never be touched nor modified unless needed,
    all JDABuilder manipulation must be done in Runner class
    ----------------------------------------
     */
    private Main(){} //create private constructor so no new instances can be made
    public static void main(String[] args) throws LoginException {
        Runner.run(); //use Runner's run() method to run bot
    }
}