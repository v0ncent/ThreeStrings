/*
Vincent Banks
ShopCommand Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Shop;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class ShopCommand implements ICommand {
    EventWaiter waiter;
    public ShopCommand(EventWaiter waiter){
        this.waiter = waiter;
    }
    @Override
    public void handle(CommandContext ctx) {
        Random r = new Random();
        List<String> footers = new ArrayList<>();
        footers.add("Here at the shop we only sells the least worn items we are no longer using!");
        footers.add("I give discounts to those I like!");
        footers.add("Durnin is happy for your business!");
        footers.add("Those who steal are thrown in the portal!");
        int rngFooter = r.nextInt(footers.size());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Shop shop = new Shop();
        embedBuilder.setTitle("The Item Shop");
        embedBuilder.addField("For Sale", shop.getShopListAsString(), true);
        embedBuilder.addField("Your inventory", "When inventory is complete it will show lol",true);
        embedBuilder.setDescription("Welcome to the shop!\nWhat can I get for ya?");
        embedBuilder.setFooter(footers.get(rngFooter));
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        ctx.getChannel().sendMessage("To purchase an item type buy (listing #).\n Or to sell type sell (inventory slot #).").queue();
        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                && e.getMessage().getContentRaw().toLowerCase().contains("buy ") // and if the message contains said chars
                        &&shop.checkIfValid(e.getMessage().getContentRaw().toLowerCase().replaceAll("buy ","")) //and if its  a valid index
                , e -> {
            //
            int shopIndex = Integer.parseInt(e.getMessage().getContentRaw()
                    .toLowerCase().replaceAll("buy ",""));
            ctx.getChannel().sendMessage(shop.buy(shopIndex-1).getName()).queue();
            //
                }, 45L, TimeUnit.SECONDS,
                () -> ctx.getChannel().sendMessage("Listen you're holding up the line buddy.").queue());
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
