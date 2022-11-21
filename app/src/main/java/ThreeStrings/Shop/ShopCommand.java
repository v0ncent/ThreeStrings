/*
Vincent Banks
ShopCommand Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Shop;
import ThreeStrings.Config;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.Inventory.Inventory;
import ThreeStrings.Rooms.Tiles.Decoration;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public final class ShopCommand implements ICommand {
    EventWaiter waiter;
    MemberMethods memberTool;
    Shop shop;
    public ShopCommand(EventWaiter waiter){
        this.waiter = waiter;
        this.memberTool = new MemberMethods();
        this.shop = new Shop();
    }
    private static boolean isValidConfirmation(String userMessage){
        List<String> validConfirmations = List.of("yes","no","y","n");
        return validConfirmations.stream().anyMatch((it) -> it.equalsIgnoreCase(userMessage));
    }
    private static boolean isCanceled(String message){
        List<String>aborts = List.of("stop","cancel","abort","nevermind","kill","nomore","s","n","no","escape","esc");
        return aborts.stream().anyMatch((it)-> it.equalsIgnoreCase(message));
    }
    List<String> ifCanceled = List.of(
            "I didn't want to do business with you anyways.",
            "I'll be here... just get your order in before festival season!",
            "Don't wait too long, I've got a coin-raker eying that stock.",
            "Hey it's fine to be a lackcoin, maybe next tenday?",
            "Come on, buy something! Halambar's has a new lute wax I need!"
    );
    private boolean parameterOneMet;
    private long amount;
    private Decoration boughtDecoration;
    @Override
    public void handle(CommandContext ctx) {
        Random r = new Random();
        Inventory inventory = new Inventory(ctx.getAuthor().getIdLong());
        List<String> footers = new ArrayList<>();
        footers.add("Here at the shop we only sells the least worn items we are no longer using!");
        footers.add("I give discounts to those I like!");
        footers.add("Durnin is happy for your business!");
        footers.add("Those who steal are thrown in the portal!");
        int rngFooter = r.nextInt(footers.size());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("The Item Shop");
        embedBuilder.addField("For Sale", shop.getShopListAsString(), true);
        embedBuilder.setDescription("Welcome to the shop!\nWhat can I get for ya?\n"+ "**Dragons: "
                + memberTool.getDragons(ctx.getAuthor().getIdLong()) + "**");
        embedBuilder.setFooter(footers.get(rngFooter));
        embedBuilder.setColor(Color.YELLOW);
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        ctx.getChannel().sendMessage("To purchase an item type buy (listing #).").queue();
        //
        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                        && e.getMessage().getContentRaw().toLowerCase().contains("buy") // and if the message contains said chars
                        &&shop.checkIfValid(e.getMessage().getContentRaw().toLowerCase().replaceAll("buy ","")) //and if it's  a valid index
                || isCanceled(e.getMessage().getContentRaw())
                , e -> {
            //
                    if(!isCanceled(e.getMessage().getContentRaw())){
                        int shopIndex = Integer.parseInt(e.getMessage().getContentRaw()
                                .toLowerCase().replaceAll("buy ",""))-1;
                        boughtDecoration = shop.buy(shopIndex);
                        parameterOneMet = true;
                        amount = boughtDecoration.getCost();
                        ctx.getChannel().sendMessage("You have selected **" + boughtDecoration.getName() + "**.").queue();
                        ctx.getChannel().sendMessage("Are you sure you would like to purchase "+ boughtDecoration.getName()
                                + " for " + boughtDecoration.getCost() + "? (Y/N,YES,NO)").queue();
                    }
            //
                }, 45L, TimeUnit.SECONDS,
                () -> ctx.getChannel().sendMessage("").queue());
        //
        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                        && parameterOneMet
                        && isValidConfirmation(e.getMessage().getContentRaw())//and if it's  a valid index
                        || isCanceled(e.getMessage().getContentRaw())
                , e -> {
                    //
                    if(!isCanceled(e.getMessage().getContentRaw())){
                        if(e.getMessage().getContentRaw().toLowerCase(Locale.ROOT).equals("no")
                                || e.getMessage().getContentRaw().toLowerCase(Locale.ROOT).equals("n")){
                            ctx.getChannel().sendMessage("The deal is off then!").queue();
                        } else {
                            if(!shop.cantAfford(ctx.getAuthor().getIdLong(),amount)){
                                if(!inventory.has(boughtDecoration.getName())){
                                    ctx.getChannel()
                                            .sendMessage("Alright! You have purchased "
                                                    + "**" + boughtDecoration.getName() + "** for **" +
                                                    amount +Config.get("DRAGONS") + " dragons.**").queue();
                                    try {
                                        inventory.addToInventory(boughtDecoration.getName());
                                        memberTool.subtractDragons(
                                                ctx.getAuthor().getIdLong(),
                                                memberTool.getDragons(ctx.getAuthor().getIdLong()),
                                                boughtDecoration.getCost()
                                        );
                                        ctx.getChannel().sendMessage("It has been added to your inventory!").queue();
                                        ctx.getChannel().sendMessage("Your new balance is: **"
                                                + memberTool.getDragons(ctx.getAuthor().getIdLong()) + Config.get("DRAGON") + "**").queue();
                                    }catch (Exception error){
                                        ctx.getChannel().sendMessage("Hmm.. I tried to add this to your inventory but seems a problem has occurred" + error).queue();
                                    }
                                } else {
                                    ctx.getChannel().sendMessage("You already own that!").queue();
                                }
                            } else {
                                ctx.getChannel().sendMessage("You dont have enough dragons to buy that!").queue();
                            }
                        }
                    } else {
                        Random random = new Random();
                        ctx.getChannel().sendMessage(ifCanceled.get(random.nextInt(ifCanceled.size()))).queue();
                    }
                    //
                }, 45L, TimeUnit.SECONDS,
                () -> ctx.getChannel().sendMessage("").queue());
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