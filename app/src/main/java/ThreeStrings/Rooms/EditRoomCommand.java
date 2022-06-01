//Vincent Banks
//EditRoomCommand Class
//COPYRIGHT Vincent banks
package ThreeStrings.Rooms;
import ThreeStrings.Database.MemberMongo;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
public class EditRoomCommand implements ICommand {
    EventWaiter waiter;
    MemberMongo mongo;
    public EditRoomCommand(EventWaiter waiter){ //create constructor to get event waiter and mongo object
        this.waiter = waiter;
        this.mongo = new MemberMongo();
    }
    //
    public static boolean checkIfValidRoom(String userMessage){ //static method to check if user picked valid room tile
        String[] validTiles = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
        for (String validTile : validTiles) {
            if (validTile.equals(userMessage)) {
                return true;
            }
        }
        return false;
    }
    //TODO: this needs to be changed once inventory is actually implemented
    public static boolean checkIfValidInventory(String userMessage){ // static method for checking if user picked a valid inventory slot
        Tiles tiles = new Tiles();
        Decoration[] validSlots = tiles.decorations;
        for (Decoration validSlot : validSlots) {
            if (validSlot.getName().equals(userMessage)) {
                return true;
            }
        }
        return false;
    }
    public static boolean checkIfValidDirection(String userMessage){ //static method for checking if user picked valid direction
        String[] validDirections = {"n","e","s","w"};
        for (String validDirection : validDirections) {
            if (validDirection.equals(userMessage)) {
                return true;
            }
        }
        return false;
    }
    //create needed variables for editing room
    int tileSpot;
    String tile;
    String direction;
    boolean parameterOneMet = false;
    boolean parameterTwoMet = false;
    Decoration decoration;
    @Override
    public void handle(CommandContext ctx) {
        final long memberId = ctx.getAuthor().getIdLong(); //get user id as long value
        final MemberMethods memberTool = new MemberMethods(); // implement our MemberMethods
        final String[] roomArray = memberTool.getRoomAsArray(memberId); // get room as array
        final String roomAsString = memberTool.getRoomAsString(memberId); //get room as string
        final TextChannel channel = ctx.getChannel(); // create a text channel variable
        final Tiles tiles = new Tiles(); // instantiate tiles object
        //
        if(!roomAsString
                .equals("Looks like you haven't registered for a room in the tavern yet.\n" +
                "To register please use !createroom !")){ //if user is registered in db
            EmbedBuilder embed = new EmbedBuilder(); //create embed object
            //set embed info
            embed.setTitle("What would you like to change about your room?");
            embed.addField("Give a tile # and a tile to swap it with!",roomAsString,true);
            embed.addField("Your inventory", tiles.plain.getName()
                    +"\n" + tiles.purplePillow.getName(), true);
            embed.setFooter("For more information on usage see !helproom.");
            ctx.getChannel().sendMessageEmbeds(embed.build()).queue();
            ctx.getChannel().sendMessage("Please select the tile # you wish to change.").queue();
            //get tile #
            waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> checkIfValidRoom(e.getMessage().getContentRaw())
                            && e.getChannel().equals(ctx.getChannel())
                            && e.getAuthor().getId().equals(ctx.getAuthor().getId()), e -> {
                //
                String tile = e.getMessage().getContentRaw();
                tileSpot = Tiles.getTilePosition(tile);
                if(tileSpot == 9999) {
                    channel.sendMessage("@everyone I have no idea how you were able to get this message it shouldn't be possible!" +
                            "\nBut since you got it wow good for you this is the easter egg! 1 gold star.").queue();
                    Document sampleDoc = new Document("id",memberId);
                    Bson updates = Updates.combine(
                            Updates.set("goldstar",1)
                    );
                    mongo.updateField(sampleDoc,updates);
                }else {
                    channel.sendMessage("You have picked tile " + tile + " to be changed.").queue();
                    parameterOneMet = true;
                    channel.sendMessage("Now pick a tile from your inventory to replace it.").queue();
                }
                //
                    }, 30L, TimeUnit.SECONDS,
                    () -> channel.sendMessage("").queue());
            //get tile name
            //TODO: needs to be changes once inventory is implemented to db
            waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> checkIfValidInventory(e.getMessage().getContentRaw())
                            && e.getChannel().equals(ctx.getChannel())
                            && e.getAuthor().getId().equals(ctx.getAuthor().getId()) && parameterOneMet, e -> {
                //
                        tile = e.getMessage().getContentRaw().toLowerCase(Locale.ROOT);
                        parameterTwoMet = true;
                        decoration = tiles.getDecoration(tile);
                        channel.sendMessage("You have picked " + decoration.getName()
                                + ". Now pick a direction to have it facing. (ex: n,e,s,w").queue();
                        //
                    }, 30L, TimeUnit.SECONDS,
                    () -> channel.sendMessage("").queue()); //add
            //get tile direction and update roomArray
            waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> checkIfValidDirection(e.getMessage().getContentRaw())
                            && e.getChannel().equals(ctx.getChannel())
                            && e.getAuthor().getId().equals(ctx.getAuthor().getId()) && parameterOneMet && parameterTwoMet, e -> {
                        //
                        direction = e.getMessage().getContentRaw().toLowerCase();
                        roomArray[tileSpot] = tiles.getDirection(decoration,direction);
                        Document sampleDoc = new Document("id",memberId);
                        Bson updates = Updates.combine(
                                Updates.set("room", Arrays.toString(roomArray)
                                        .replaceAll(",","")
                                        .replaceAll("\\[","")
                                        .replaceAll("\\]",""))
                        );
                        try{
                            mongo.updateField(sampleDoc,updates);
                            channel.sendMessage("take a look at your new room!").queue();
                            channel.sendMessage(memberTool.getRoomAsString(memberId)).queue();
                        }catch (Exception error){
                            error.printStackTrace();
                            channel.sendMessage("I dont feel so good " + error).queue();
                        }
                        //
                    }, 30L, TimeUnit.SECONDS,
                    () -> channel.sendMessage("Tsk,took too long I do not have all day.").queue()); //add

        }else {
            ctx.getChannel().sendMessage(roomAsString).queue();
        }
    }
    @Override
    public String getName() {
        return "editroom";
    }
    @Override
    public String getHelp() {
        return "the command to edit your tavern room.\n For more info on usage please use !roomhelp !";
    }
}
