//Vincent Banks
//Command Manager Class
//Copyright Vincent Banks
package ThreeStrings;
import ThreeStrings.Inventory.DragonsCommand;
import ThreeStrings.Inventory.InventoryCommand;
import ThreeStrings.Music.*;
import ThreeStrings.Rooms.CreateRoomCommand;
import ThreeStrings.Rooms.EditRoomCommand;
import ThreeStrings.Rooms.RoomCommand;
import ThreeStrings.Rooms.RoomHelpCommand;
import ThreeStrings.Shop.ShopCommand;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import ThreeStrings.command.commands.*;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>(); //implement our Icommand interface
    public CommandManager(EventWaiter waiter){    //register commands into manager
        //utility commands
        addCommand(new HelpCommand(this));
        addCommand(new PingCommand());
        //music commands
        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new PlaylistCommand());
        addCommand(new ShuffleCommand());
        addCommand(new ClearCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
        addCommand(new LeaveCommand());
        addCommand(new PauseCommand());
        addCommand(new UnpauseCommand());
        addCommand(new DmCommand(waiter));
        //Commands
        addCommand(new CapybaraCommand());
        addCommand(new ProfileCommand());
        //room commands
        addCommand(new CreateRoomCommand());
        addCommand(new RoomCommand());
        addCommand(new EditRoomCommand(waiter));
        addCommand(new RoomHelpCommand());
        addCommand(new InventoryCommand());
        //shop commands
        addCommand(new ShopCommand(waiter));
        addCommand(new DragonsCommand());
    }
    private void addCommand(ICommand cmd){    //create a addCommand method
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName())); //create boolean method if a command is already found
        if (nameFound){
            throw new IllegalArgumentException("Command Already Present");
        }
        commands.add(cmd);
    }
    public List<ICommand> getCommands(){
        return commands;
    }
    @Nullable
    public ICommand getCommand(String search){    //create command search method
        String searchLower = search.toLowerCase(); //create a string called searchLower that uses search
        for (ICommand cmd: this.commands){
            if (cmd.getName().equals(searchLower) || cmd.getAlisases().contains(searchLower)){
                return cmd;
            }
        }
        return null;
    }
    void handle(GuildMessageReceivedEvent event){
        String[] split = event.getMessage().getContentRaw() //creates string array called split and gets raw content of message
                .replaceFirst("(?i)" + Pattern.quote(Config.get("PREFIX")),"") //takes off prefix
                .split("\\s+"); //splits it on white space
        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);
        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length); //take off first index and start at 1
            CommandContext ctx = new CommandContext(event, args); //creates command context to be later imported when creating commands
            cmd.handle(ctx);
        }else{
            event.getChannel().sendMessage("Hey sorry, I can only do so much with the Lute. Use !Help for a list of my many talents!").queue(); //bot tells user that command does not exist
        }
    }
    public List<ICommand> getCommandList(){
        return commands;
    }
}
