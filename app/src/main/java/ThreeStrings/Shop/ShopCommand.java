/*
Vincent Banks
ShopCommand Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Shop;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import java.util.List;
public class ShopCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Shop shop = new Shop();
        embedBuilder.setTitle("The Item Shop");
        embedBuilder.addField("Listings", shop.getShopList(), true);
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
    @Override
    public String getName() {
        return "shop";
    }
    @Override
    public String getHelp() {
        return "Command to access the item shop!";
    }
    @Override
    public List<String> getAlisases() {
        return List.of("store");
    }
}
