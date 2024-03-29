//Vincent Banks
//Command Manager Class
//Copyright Vincent Banks
package ThreeStrings.Bot;

import ThreeStrings.Bot.Inventory.DragonsCommand;
import ThreeStrings.Bot.Inventory.InventoryCommand;
import ThreeStrings.Bot.Music.*;
import ThreeStrings.Bot.Rooms.CreateRoomCommand;
import ThreeStrings.Bot.Rooms.EditRoomCommand;
import ThreeStrings.Bot.Rooms.RoomCommand;
import ThreeStrings.Bot.Rooms.RoomHelpCommand;
import ThreeStrings.Bot.Shop.ShopCommand;
import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import ThreeStrings.Bot.command.commands.*;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager(EventWaiter waiter){
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

    private void addCommand(ICommand cmd){
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound){
            throw new IllegalArgumentException("Command Already Present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands(){
        return commands;
    }

    public ICommand getCommand(String search){
        String searchLower = search.toLowerCase();

        for (ICommand cmd: this.commands){
            if (cmd.getName().equals(searchLower) || cmd.getAlisases().contains(searchLower)){
                return cmd;
            }
        }

        return null;
    }
    void handle(MessageReceivedEvent event){
        String[] split = event.getMessage().getContentRaw()
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
