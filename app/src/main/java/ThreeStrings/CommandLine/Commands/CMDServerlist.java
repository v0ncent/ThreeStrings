//Vincent Banks
//CMDServerlist Class
//COPYRIGHT Vincent Banks
package ThreeStrings.CommandLine.Commands;
import ThreeStrings.CommandLine.CommandLineManager;
import ThreeStrings.CommandLine.Exceptions;
import ThreeStrings.Bot.Listener;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import java.util.Scanner;
public final class CMDServerlist {
    // determine if user wants to continue with this method
    private static boolean cont(@NotNull String choice) throws Exceptions.ExitCodeCalled {
        if(choice.toLowerCase(Locale.ROOT).equals("-999")){
            throw new Exceptions.ExitCodeCalled(CMDServerlist.class);
        }
        return choice.toLowerCase(Locale.ROOT).equals("y");
    }
    public static void sendList() throws Exceptions.ExitCodeCalled {
        Scanner scnr = new Scanner(System.in);
        System.out.println("-All servers that threestrings is currently in-");
        CommandLineManager.printMenu(Listener.GUILDS);
        System.out.println("Would you like to get info on servers? (y/n)");
        String choice = scnr.nextLine();
        if(cont(choice)){
            System.out.println("What would you like to get?");
        }
    }
}
