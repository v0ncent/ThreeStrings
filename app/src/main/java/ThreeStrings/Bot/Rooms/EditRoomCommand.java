//Vincent Banks
//EditRoomCommand Class
//COPYRIGHT Vincent banks
package ThreeStrings.Bot.Rooms;
import ThreeStrings.Bot.Database.MemberMongo;
import ThreeStrings.Bot.ExtendedMethods.MemberMethods;
import ThreeStrings.Bot.Inventory.Inventory;
import ThreeStrings.Bot.Rooms.RoomMethods.RoomMethods;
import ThreeStrings.Bot.Rooms.Tiles.Decoration;
import ThreeStrings.Bot.Rooms.Tiles.Tiles;
import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public final class EditRoomCommand implements ICommand {
    EventWaiter waiter;
    MemberMongo mongo;
    public EditRoomCommand(EventWaiter waiter){ //create constructor to get event waiter and mongo object
        this.waiter = waiter;
        this.mongo = new MemberMongo();
    }
    private static boolean isCanceled(String message){
        List<String>aborts = List.of("stop","cancel","abort","nevermind","kill","nomore","no","escape","esc");
        return aborts.stream().anyMatch((it)-> it.equalsIgnoreCase(message));
    }
    int index;
    boolean isParameterOneMet;
    boolean isParameterTwoMet;
    Decoration newDecoration;
    @Override
    public void handle(CommandContext ctx) {
        final MemberMethods memberTool = new MemberMethods();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        final long discordID = ctx.getAuthor().getIdLong();
        final TextChannel channel = ctx.getChannel();
        final List<String> userRoom = memberTool.getRoom(discordID);
        final String userRoomAsString = memberTool.getRoomAsString(discordID);
        final Inventory userInventory = new Inventory(ctx.getAuthor().getIdLong());
        embedBuilder.setTitle("What would you like to do to your room?");
        embedBuilder.setDescription("Pick a tile # (left to right 1-25), and a tile you would like to change it to," +
                "then pick a tile direction!");
        embedBuilder.addField("Your Room",userRoomAsString,true);
        embedBuilder.addField("Your Inventory", userInventory.getPlayerInventoryAsString(),true);
        embedBuilder.setColor(Color.YELLOW);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
        channel.sendMessage("Pick a tile # (left to right 1-25) you wish to edit").queue();
        List<String> randomStopMessages = List.of(
                "Alright, alright, let me know when you've made up your mind.",
                "Put the hammers away boys, it's off.",
                "That'll be 5 dragons for the appraisal still.",
                "For Gonds sake, I got a whole half-ogre here to help with the lumber!",
                "I'm never going to hear the end of this cancellation from the Carpenters', Roofers', and Plasterers' Guild...",
                "Whatever, that chair is going up on stage for me to use now!"
        );
        //
        waiter.waitForEvent(MessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                && RoomMethods.checkIfValidRoom(e.getMessage().getContentRaw()) //and that the user gave a valid room slot
                || isCanceled(e.getMessage().getContentRaw())
                , e -> {
                    //
                    if(!isCanceled(e.getMessage().getContentRaw())){
                        index = Integer.parseInt(e.getMessage().getContentRaw());
                        isParameterOneMet = true;
                        channel.sendMessage("Alright you have chosen tile **" + index +"** To be edited.").queue();
                        channel.sendMessage("Now pick a tile to replace it.").queue();
                    }
                    //
                }, 45L, TimeUnit.SECONDS,
                () -> ctx.getChannel().sendMessage("").queue());
        //
        waiter.waitForEvent(MessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                        && userInventory.has(e.getMessage().getContentRaw())//and that the user gave a valid inventory slot
                        && isParameterOneMet
                        || isCanceled(e.getMessage().getContentRaw())
                , e -> {
                    //
                    if(!isCanceled(e.getMessage().getContentRaw())){
                        newDecoration = Tiles.getDecoration(e.getMessage().getContentRaw());
                        isParameterTwoMet = true;
                        assert newDecoration != null;
                        channel.sendMessage("You have chosen **" + newDecoration.getName() + ".**").queue();
                        channel.sendMessage("Now pick a direction to have it facing (n,e,s,w)").queue();
                    }
                    //
                }, 45L, TimeUnit.SECONDS,
                () -> ctx.getChannel().sendMessage("").queue());
        //
        waiter.waitForEvent(MessageReceivedEvent.class,
                e -> e.getChannel().equals(ctx.getChannel()) // if the channel is the same
                        && e.getAuthor().getId().equals(ctx.getAuthor().getId()) //and the user is the same
                        && Tiles.checkIfValidDirection(e.getMessage().getContentRaw())//and that the user gave a valid direction
                        && isParameterTwoMet
                        || isCanceled(e.getMessage().getContentRaw())
                , e -> {
                    //
                    if(!isCanceled(e.getMessage().getContentRaw())){
                        userRoom.set(Tiles.getRoomIndex(index),Tiles.getEmoji(e.getMessage().getContentRaw(),newDecoration));
                        Document sampleDoc = new Document("id",discordID);
                        Bson updates = Updates.combine(Updates.set("room",userRoom));
                        try {
                            mongo.updateField(sampleDoc,updates);
                        }catch (Exception error){
                            error.printStackTrace();
                            channel.sendMessage("I feel sick...\n"+ error).queue();
                        }
                        channel.sendMessage("Take a look at your new room!\n" + memberTool.getRoomAsString(discordID)).queue();
                    } else {
                        Random random = new Random();
                        channel.sendMessage(randomStopMessages.get(random.nextInt(randomStopMessages.size()))).queue();
                    }
                    //
                }, 45L, TimeUnit.SECONDS,
                () -> ctx.getChannel().sendMessage("Listen I dont have all day the carpenters are busy.").queue());
        //
    }
    @Override
    public String getName() {
        return "editroom";
    }
    @Override
    public String getHelp() {
        return "the command to edit your tavern room.\n For more info on usage please use !roomhelp !";
    }
    @Override
    public String getType() {
        return "rooms";
    }
}
