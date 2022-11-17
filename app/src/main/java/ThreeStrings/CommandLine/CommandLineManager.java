//Vincent Banks
//CommandLineManager Class
//COPYRIGHT Vincent Banks
package ThreeStrings.CommandLine;
import ThreeStrings.CommandLine.Commands.CMDPlay;
import com.mongodb.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CancellationException;
public final class CommandLineManager extends Thread{
    //define codes
    public static final int EXIT_CODE = -999;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineManager.class);
    @Override
    public void run(){
        CommandLineManager.currentThread().setName("CommandLineApp");
        LOGGER.info(String.format("Listening for commands in cmdline on thread: name-%s, id-%d",this.getName(),this.getId()));
        while (this.isAlive()){
            listen();
        }
    }
    private static void listen(){
        Scanner scnr = new Scanner(System.in);
        String[] args = scnr.nextLine().strip().split(" ");
        handle(args);
    }
    private static void handle(@NonNull String[] args){
        if(Objects.equals(args[0], "play")){
            try{
                String link = args[1];
                String ytPattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
                if(!link.isEmpty() && link.matches(ytPattern)){
                    System.out.println(link);
                    CMDPlay.play(link);
                }else{
                    throw new MalformedURLException();
                }
            }catch (IndexOutOfBoundsException e){
             LOGGER.error("You have either have provided no link or picked a invalid menu choice!");
            }catch (MalformedURLException e){
                LOGGER.error("You have provided a invalid youtube link!");
            } catch (CancellationException e){
                LOGGER.error("Exit code " + EXIT_CODE + " has been called!");
            }
        }
    }
}
