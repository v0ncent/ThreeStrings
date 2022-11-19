//Vincent Banks
//CommandLineManager Class
//COPYRIGHT Vincent Banks
package ThreeStrings.CommandLine;
import ThreeStrings.CommandLine.Commands.CMDLeave;
import ThreeStrings.CommandLine.Commands.CMDPlay;
import ThreeStrings.CommandLine.Commands.CMDServerlist;
import com.mongodb.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
public final class CommandLineManager extends Thread{
    private static final Class<CommandLineManager> self = CommandLineManager.class;
    private static final Logger LOGGER = LoggerFactory.getLogger(self);
    @Override
    public void run(){
        CommandLineManager.currentThread().setName("CommandLineApp");
        LOGGER.info(String.format("Listening for commands in cmdline on thread: name-%s, id-%d",this.getName(),this.getId()));
        while (this.isAlive()){
            try {
                listen();
            } catch (Exceptions.CommandNotFound ignored){}
            catch (Exception e){
                e.printStackTrace();
                run();
            }
        }
    }
    // method for listening for commands
    private static void listen() throws Exceptions.CommandNotFound {
        System.out.print("c>: ");
        Scanner scnr = new Scanner(System.in);
        String[] args = scnr.nextLine().strip().split(" ");
        handle(args);
    }
    // you can guess what this does (handles the commands)
    private static void handle(@NonNull String[] args) throws Exceptions.CommandNotFound {
        switch (args[0].toLowerCase(Locale.ROOT)){
            case "play":
                try{
                    String link = args[1];
                    String ytPattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
                    if(!link.isEmpty() && link.matches(ytPattern)){
                        CMDPlay.play(link);
                    }else{
                        throw new Exceptions.InvalidYoutubeURL(self);
                    }
                }catch (Exception e){
                    CMLineExceptionHandler.handle(self,e);
                }
                break;
            case "leave":
                try{
                    CMDLeave.leave();
                }catch (Exception e){
                    CMLineExceptionHandler.handle(self,e);
                }
                break;
            case "serverlist":
                try{
                    CMDServerlist.sendList();
                }catch (Exception e){
                    CMLineExceptionHandler.handle(self,e);
                }
                break;
            default: throw new Exceptions.CommandNotFound(self);
        }
    }
    //method for printing menu, so I don't haft to do this every time in commands ;p
    public static void printMenu(List<?> list){
        System.out.println("------------");
        for(int i = 0; i< list.size(); i++){
            System.out.println(i + " " + list.get(i).toString());
        }
        System.out.println("------------");
    }
    // Exception Manager
    static final class CMLineExceptionHandler {
        public static void handle(Class<?> clazz, Exception e ){
            Logger LOGGER = LoggerFactory.getLogger(clazz);
            if(e.getClass().equals(ArrayIndexOutOfBoundsException.class)){
                LOGGER.error("You have not provided a necessary argument!");
                return;
            }
            if(e.getClass().getDeclaringClass().equals(Exceptions.class)){ //if member of custom exceptions ignore they handle themselves
                return;
            }
            LOGGER.error(String.format("UH OH: there was an error @ %s\n%s",clazz.getName(),e));
            e.printStackTrace();
        }
    }
}
