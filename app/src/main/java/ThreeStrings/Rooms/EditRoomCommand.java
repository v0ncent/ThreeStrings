//Vincent Banks
//EditRoomCommand Class
//COPYRIGHT Vincent banks
package ThreeStrings.Rooms;

import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class EditRoomCommand implements ICommand {
    EventWaiter waiter;
    public EditRoomCommand(EventWaiter waiter){ //create constructor to get event waiter
        this.waiter = waiter;
    }
    public static boolean checkIfValid(String userMessage){
        String[] validTiles = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
        for (String validTile : validTiles) {
            if (validTile.equals(userMessage)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void handle(CommandContext ctx) {
        final long memberId = ctx.getAuthor().getIdLong(); //get user id as long value
        final MemberMethods memberTool = new MemberMethods(); // implement our MemberMethods
        final String[] roomArrray = memberTool.getRoomAsArray(memberId);
        final String roomAsString = memberTool.getRoomAsString(memberId);
        final TextChannel channel = ctx.getChannel();
        if(!roomAsString
                .equals("Looks like you haven't registered for a room in the tavern yet.\n" +
                "To register please use !createroom !")){
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("What would you like to change about your room?");
            embed.addField("Give a tile # and a tile to swap it with!",roomAsString,true);
            embed.addField("Your inventory","inventory",true);
            embed.setFooter("For more information on usage see !helproom.");
            ctx.getChannel().sendMessageEmbeds(embed.build()).queue();
            ctx.getChannel().sendMessage("Give a tile # and a tile to swap it with!").queue();
            waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> checkIfValid(e.getMessage().getContentRaw()) && e.getChannel().equals(ctx.getChannel()), e -> {
                String message = e.getMessage().getContentRaw();
                int arraySpot;
                if(message.equals("1")){
                    arraySpot = 0;
                    roomArrray[arraySpot] = "1";
                }else if(message.equals("2")){
                    arraySpot = 0;

                }
                    }, 1L, TimeUnit.MINUTES,
                    () -> channel.sendMessage("Tsk,took too long I do not have all day.").queue()); //add timeout););

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
