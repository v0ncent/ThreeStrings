/*
Vincent Banks
DragonsCommand Final Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Bot.Inventory;

import ThreeStrings.Bot.Config;
import ThreeStrings.Bot.ExtendedMethods.MemberMethods;
import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;
import java.util.List;

public final class DragonsCommand implements ICommand {
    MemberMethods memberTool;

    public DragonsCommand(){
        memberTool = new MemberMethods();
    }

    @Override
    public void handle(CommandContext ctx) {
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
