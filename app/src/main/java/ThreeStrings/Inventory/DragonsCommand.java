/*
Vincent Banks
DragonsCommand Final Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Inventory;
import ThreeStrings.Config;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;
import java.util.List;
public final class DragonsCommand implements ICommand {
    // A simple display command
    @Override
    public void handle(CommandContext ctx) {
        MemberMethods memberTool = new MemberMethods();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(ctx.getAuthor().getName() + "'s Coin Pouch");
        embedBuilder.setColor(Color.yellow);
        embedBuilder.addField(memberTool.getDragons(ctx.getAuthor().getIdLong()) + Config.get("DRAGON")," ",true);
        if(memberTool.getDragons(ctx.getAuthor().getIdLong()) < 20){
            embedBuilder.setFooter("A lack coin I see!");
        } else {
            embedBuilder.setFooter("Not a lack coin I see!");
        }
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
    @Override
    public String getName() {
        return "coinpouch";
    }
    @Override
    public String getHelp() {
        return "Shows you how many dragons you currently have!";
    }
    @Override
    public String getType() {
        return "member";
    }
    @Override
    public List<String> getAlisases() {
        return List.of("money","dragons","coins","coin");
    }
}
