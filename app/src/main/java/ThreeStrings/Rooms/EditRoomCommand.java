//Vincent Banks
//EditRoomCommand Class
//COPYRIGHT Vincent banks
package ThreeStrings.Rooms;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.concurrent.TimeUnit;
public class EditRoomCommand implements ICommand {
    EventWaiter waiter;
    public EditRoomCommand(EventWaiter waiter){ //create constructor to get event waiter
        this.waiter = waiter;
    }
    //
    public static boolean checkIfValidRoom(String userMessage){
        String[] validTiles = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
        for (String validTile : validTiles) {
            if (validTile.equals(userMessage)) {
                return true;
            }
        }
        return false;
    }
    //TODO: this needs to be changed once inventory is actually implemented
    public static boolean checkIfValidInventory(String userMessage){
        String[] validSlots = {"1","2","3","4","5"};
        for (String validSlot : validSlots){
            if(validSlot.equals(userMessage)){
                return true;
            }
        }
        return false;
    }
    //
    int inventorySpot;
    int tileSpot;
    boolean parameterOneMet = false;
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
                "To register please use !createroom !")){
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("What would you like to change about your room?");
            embed.addField("Give a tile # and a tile to swap it with!",roomAsString,true);
            embed.addField("Your inventory","1. " + tiles.plainTile
                    +"\n2. " + tiles.pillowTile1
                    + "\n3. " + tiles.pillowTile2
                    + "\n4. " + tiles.pillowTile3
                    + "\n5. " + tiles.pillowTile4,true);
            embed.setFooter("For more information on usage see !helproom.");
            ctx.getChannel().sendMessageEmbeds(embed.build()).queue();
            ctx.getChannel().sendMessage("Please select the tile # you wish to change.").queue();
            //
            waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> checkIfValidRoom(e.getMessage().getContentRaw())
                            && e.getChannel().equals(ctx.getChannel())
                            && e.getAuthor().getId().equals(ctx.getAuthor().getId()), e -> {
                String tile = e.getMessage().getContentRaw();
                tileSpot = Tiles.getTilePosition(tile);
                if(tileSpot == 999) {
                    channel.sendMessage("I have no idea how you were able to get this message it shouldn't be possible!" +
                            "\nBut since you got it wow good for you this is the easter egg! 1 gold star.").queue();
                }else {
                    channel.sendMessage("You have picked tile " + (tileSpot + 1) + " to be changed.").queue();
                    parameterOneMet = true;
                    channel.sendMessage("Now pick a tile from your inventory to replace it.").queue();
                }
                    }, 30L, TimeUnit.SECONDS,
                    () -> channel.sendMessage("Tsk,took too long I do not have all day.").queue());
            //TODO: needs to be changes once inventory is implemented to db
            waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> checkIfValidInventory(e.getMessage().getContentRaw())
                            && e.getChannel().equals(ctx.getChannel())
                            && e.getAuthor().getId().equals(ctx.getAuthor().getId()) && parameterOneMet, e -> {
                        String inventorySlot = e.getMessage().getContentRaw();
                        switch (inventorySlot) {
                            case "1":
                                roomArray[tileSpot] = tiles.plainTile;
                                break;
                            case "2":
                                roomArray[tileSpot] = tiles.pillowTile1;
                                break;
                            case "3":
                                roomArray[tileSpot] = tiles.pillowTile2;
                                break;
                            case "4":
                                roomArray[tileSpot] = tiles.pillowTile3;
                                break;
                            case "5":
                                roomArray[tileSpot] = tiles.pillowTile4;
                                break;
                        }
                        channel.sendMessage("take a look at your new room!").queue();
                        channel.sendMessage(tiles.formatRoomArrayAsString(roomArray)).queue();
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
