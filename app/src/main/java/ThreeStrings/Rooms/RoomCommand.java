//Vincent Banks
//RoomCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import java.util.Random;
public class RoomCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        //define needed variables
        final long memberId = ctx.getAuthor().getIdLong();
        final TextChannel channel = ctx.getChannel();
        final MemberMethods memberTool = new MemberMethods();
        final Random random = new Random();
        final String message = memberTool.getRoomAsString(memberId);
        //create string array of quiqs
        final String[] quips = {"I like what you've done with the place!","Its just like my room, just not in a dirty tavern!",
        "Your humble abode.","It looks nice and clean, im surprised!", "Life is better with a tavern room I say!",
                "Appreciate what you have because if you dont pay rent its gone!","This gets rid of the yawn in YawningPortal!"
        ,"I see this is very you!","Our tavern rooms are built to last! Just take care of it."};
        //create random integer
        int randomQuiq = random.nextInt(quips.length);
        if(message.equals("Looks like you haven't registered for a room in the tavern yet.\n" +
                "To register please use !createroom !")){ //if user is not registered in db
            channel.sendMessage(message).queue();
        }else {
            channel.sendMessage(quips[randomQuiq]).queue();
            channel.sendMessage(memberTool.getRoomAsString(memberId)).queue();
        }
    }
    @Override
    public String getName() {
        return "room";
    }
    @Override
    public String getHelp() {
        return "Displays your tavern room!";
    }

    @Override
    public String getType() {
        return "rooms";
    }
}
