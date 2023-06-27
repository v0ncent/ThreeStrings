//Vincent Banks
//Icommand Interface Class for command manager
//Copyright Vincent Banks
package ThreeStrings.Bot.command;
import java.util.List;
public interface ICommand {
    /**
     * What the command does at time of execution.
     * @param ctx CommandContext object for channel command was executed in, and the arguments given when invoked.
     * @see CommandContext
     */
    void handle(CommandContext ctx);

    /**
     * Name of command.
     * @return the name of specified ICommand type.
     */
    String getName();

    /**
     * Help command description.
     * @return Description of help of specified ICommand type.
     */
    String getHelp();

    /**
     * Type of command grouping.
     * @return The category the specified ICommand type falls under.
     */
    String getType();

    /**
     * Other names for command.
     * @return List of aliases of specified ICommand type.
     */
    default List<String> getAlisases(){
        return List.of();
    }
}
