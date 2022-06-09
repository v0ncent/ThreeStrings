/*
Vincent Banks
ShopCommand Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Shop;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.Rooms.Tiles.Decoration;
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
    private static boolean isInt(String userMessage){
        try{
            Integer.parseInt(userMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private static boolean isValidConfirmation(String userMessage){
        List<String> validConfirmations = List.of("yes","no","y","n");
        for (String validConfirmation : validConfirmations) {
            if (userMessage.equals(validConfirmation)) {
                return true;
            }
        }
        return false;
    }
    boolean parameterOneMet;
    boolean parameterTwoMet;
    Decoration boughtDecoration;
    int amount;
    @Override
    public void handle(CommandContext ctx) {
        Random r = new Random();
        MemberMethods memberTool = new MemberMethods();
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
        System.out.println(memberTool.getInventory(ctx.getAuthor().getIdLong()));
        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                && e.getMessage().getContentRaw().toLowerCase().contains("buy") // and if the message contains said chars
                        &&shop.checkIfValid(e.getMessage().getContentRaw().toLowerCase().replaceAll("buy ","")) //and if it's  a valid index
                , e -> {
            //
            int shopIndex = Integer.parseInt(e.getMessage().getContentRaw()
                    .toLowerCase().replaceAll("buy ",""))-1;
            boughtDecoration = shop.buy(shopIndex);
            parameterOneMet = true;
            ctx.getChannel().sendMessage("You have selected **" + boughtDecoration.getName() + "**.").queue();
            ctx.getChannel().sendMessage("How many of **"+ boughtDecoration.getName() + "** would you like to buy?").queue();
            //
                }, 45L, TimeUnit.SECONDS,
                () -> ctx.getChannel().sendMessage("").queue());
        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                        && parameterOneMet
                && isInt(e.getMessage().getContentRaw())//and if it's  a valid index
                , e -> {
                    //
                    amount = Integer.parseInt(e.getMessage().getContentRaw());
                    parameterTwoMet = true;
                    ctx.getChannel().sendMessage("You are about to purchase **" + amount + "** **" + boughtDecoration.getName() + "s.**").queue();
                    ctx.getChannel().sendMessage("Are you sure you would like to purchase? (Y/N,YES,NO)").queue();
                    //
                }, 45L, TimeUnit.SECONDS,
                () -> ctx.getChannel().sendMessage("").queue());
        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                        && parameterTwoMet
                        && isValidConfirmation(e.getMessage().getContentRaw().toLowerCase())//and if it's  a valid index
                , e -> {
                    //
                    if(e.getMessage().getContentRaw().toLowerCase(Locale.ROOT).equals("no")
                            || e.getMessage().getContentRaw().toLowerCase(Locale.ROOT).equals("n")){
                        ctx.getChannel().sendMessage("The deal is off then!").queue();
                    } else {
                        ctx.getChannel()
                                .sendMessage("Alright! You have purchased **"
                                        + amount + "** **" + boughtDecoration.getName() + "s.**" ).queue();
                    }
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
    public String getType() {
        return "shop";
    }

    @Override
    public List<String> getAlisases() {
        return List.of("store");
    }
}
