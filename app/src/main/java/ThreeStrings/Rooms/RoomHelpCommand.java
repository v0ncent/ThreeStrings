//Vincent Banks
//RoomHelpCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;
public final class RoomHelpCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Yawning Portal Room Guide");
        embedBuilder.setThumbnail("" +
                "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/662b7605-925c-498d-9108-bfbc24d3b1fb/dc14arw-e53d01f0-c8fa-4ae1-bf68-e9f19e033d00.jpg/v1/fill/w_800,h_450,q_75,strp/bedroom_of_tavern_by_vui_huynh_dc14arw-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NDUwIiwicGF0aCI6IlwvZlwvNjYyYjc2MDUtOTI1Yy00OThkLTkxMDgtYmZiYzI0ZDNiMWZiXC9kYzE0YXJ3LWU1M2QwMWYwLWM4ZmEtNGFlMS1iZjY4LWU5ZjE5ZTAzM2QwMC5qcGciLCJ3aWR0aCI6Ijw9ODAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.0nY2IP8PKIIQIiaws4NkrnlhTHdg6HP6gaKy70kf2LE"
        );
        embedBuilder.setColor(Color.yellow);
        embedBuilder.setDescription("``Below I have listed some common questions and instructions on how to use the room system here in the tavern!``");
        embedBuilder.addField("How do I get new Tiles for my room?","Simple! You can buy them using the ``!shop`` command.",true);
        embedBuilder.addField("Can I see other peoples inventories or rooms?","Yes! Indeed you can by either using ``!room @user``," +
                "\n or ``!inventory @user`` !",true);
        embedBuilder.addField("How do I edit my room?","With the !editroom command," +
                "rooms are numbered from 1-25 (left to right).\n" +
                "Start off using the command by saying which number tile you would like to edit, " +
                "then what tile you would like to replace it with from your inventory.\n" +
                "Then simply pick which direction you would like the tile to face and voila. \nYour room has been successfully edited!",false);
        embedBuilder.setImage(
                "https://media.discordapp.net/attachments/895782679532945409/990749468422975498/roomHelp.PNG"
        );
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
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
