//Vincent Banks
//Icommand Interface Class for command manager
//Copyright Vincent Banks
package ThreeStrings.command;
import java.util.List;
public interface ICommand {
    void handle(CommandContext ctx); //create a method called handle that implements our command context class and names it ctx
    String getName(); //define a string as getName(); to get names of the commands
    String getHelp();
    default List<String> getAlisases(){ //create a list with String variables called getAliases();  //set it to default because not every command needs a variable
        return List.of();
    }
}
