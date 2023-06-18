//Vincent Banks
//InventoryCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Inventory;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import java.awt.*;
public final class InventoryCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().isEmpty()){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            Inventory inventory = new Inventory(ctx.getAuthor().getIdLong());
            ctx.getChannel().sendMessage("Quite the collection I see.").queue();
            embedBuilder.setTitle(ctx.getAuthor().getName() + "'s Inventory");
            embedBuilder.setColor(Color.YELLOW);
            embedBuilder.setDescription("Quite the collection I see.");
            embedBuilder.addField("",inventory.getPlayerInventoryAsString(),true);
            ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        } else {
            Member mentionedUser = ctx.getMessage().getMentionedMembers().get(0);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            Inventory inventory = new Inventory(mentionedUser.getIdLong());
            ctx.getChannel().sendMessage("Quite the collection I see.").queue();
            embedBuilder.setTitle(mentionedUser.getEffectiveName() + "'s Inventory");
            embedBuilder.setThumbnail(mentionedUser.getUser().getAvatarUrl());
            embedBuilder.setColor(Color.YELLOW);
            embedBuilder.setDescription("Quite the collection I see.");
            embedBuilder.addField("",inventory.getPlayerInventoryAsString(),true);
            ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
    @Override
    public String getName() {
        return "inventory";
    }
    @Override
    public String getHelp() {
        return "Shows your inventory!";
    }
    @Override
    public String getType() {
        return "member";
    }
}
