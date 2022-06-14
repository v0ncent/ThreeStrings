//Vincent Banks
//RoomHelpCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
public final class RoomHelpCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

    }
    @Override
    public String getName() {
        return "roomhelp";
    }
    @Override
    public String getHelp() {
        return "guides you on how to use the rooms system here at the tavern!";
    }
    @Override
    public String getType() {
        return "rooms";
    }
}
