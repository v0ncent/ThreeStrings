//Vincent Banks
//EditRoomCommand Class
//COPYRIGHT Vincent banks
package ThreeStrings.Rooms;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
public class EditRoomCommand implements ICommand {
    EventWaiter waiter;
    public EditRoomCommand(EventWaiter waiter){ //create constructor to get event waiter
        this.waiter = waiter;
    }
    @Override
    public void handle(CommandContext ctx) {
        final long memberId = ctx.getAuthor().getIdLong(); //get user id as long value
        final MemberMethods memberTool = new MemberMethods(); // implement our MemberMethods
        final String[] roomArrray = memberTool.getRoomAsArray(memberId);
        final String roomAsString = memberTool.getRoomAsString(memberId);
        if(!roomAsString
                .equals("Looks like you haven't registered for a room in the tavern yet.\n" +
                "To register please use !createroom !")){
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("What would you like to change about your room?");
            embed.addField("Give a tile # and a tile to swap it with!",roomAsString,true);
            embed.addField("Your inventory","inventory",true);
            embed.setFooter("For more information on usage see !helproom.");
            ctx.getChannel().sendMessage(embed.build()).queue();
        }else {
            ctx.getChannel().sendMessage(roomAsString).queue();
        }
    }
    @Override
    public String getName() {
        return "editroom";
    }
    @Override
    public String getHelp() {
        return "the command to edit your tavern room.\n For more info on usage please use !roomhelp !";
    }
}
