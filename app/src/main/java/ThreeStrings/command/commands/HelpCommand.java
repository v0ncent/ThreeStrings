//Vincent Banks
//HelpCommand Class
//COPYRIGHT Vincet Banks
package ThreeStrings.command.commands;
import ThreeStrings.CommandManager;
import ThreeStrings.Config;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import java.util.List;
public class HelpCommand implements ICommand {
   private final CommandManager manager; //import command manager class
    public HelpCommand(CommandManager manager) { //implement constructor for help command
        this.manager = manager; //set manager variable to our already made manager within constructor
    }
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();    //create a list of strings called args, then gets arguments
        TextChannel channel = ctx.getChannel(); //implement textchannel class and create a new channel variable that then gets channel
        if(args.isEmpty()){  //if there is no command with !help if statement runs
            StringBuilder builder = new StringBuilder(); //implement new string builder with variable builder
            builder.append("Hi, I'm Mattrim \"Threestrings\" Mereg. Here's a list of things I can do:\n"); //append string, and set what bot says at the top of help command
            manager.getCommands().stream().map(ICommand::getName).forEach( //gets the list of commands withing the command manager class
                    (it) -> builder.append('`').append(Config.get("PREFIX")).append(it).append("`\n")
            );
            channel.sendMessage(builder.toString()).queue(); //bot then sends out message
            return;
        }
        String search = args.get(0);  //set search string for if command does not exist
        ICommand command = manager.getCommand(search); //implement Icommand class with variable command, calls for manager and command search

        if (command == null){ //if there is no existing command if statementt runs
            channel.sendMessage("Sorry I've got nothing for" + search).queue(); //bot sends out no command exists message
            return;
        }
        channel.sendMessage(command.getHelp()).queue(); //!help command is sent out
    }
    @Override
    public String getName() {
        return "help"; //set command to help within discord
    }
    @Override
    public String getHelp() { //when !help help is called this is ran
        return "Shows the list with what I can do\n" + "Usage: !help (command)";
    }
    @Override
    public List<String> getAlisases() {  //set secondary help commmand called !bard
        return List.of("bard","cmds","commandlist");
    }
}
