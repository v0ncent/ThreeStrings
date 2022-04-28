//Vincent Banks
//CreateRoomCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms;
import ThreeStrings.Database.MONGODB;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import org.bson.Document;
public class CreateRoomCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        int roomNum = 0;
        MONGODB mongo = new MONGODB(); //get mongo class
        long discordId = ctx.getAuthor().getIdLong(); //pull user's discord id as long value
        @SuppressWarnings("SpellCheckingInspection")
        String defaultRoom = "<:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506>" +
                "<:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506>" +
                "<:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506>" +
                "<:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506>" +
                "<:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506><:woodtile:940727196157374506>"; //create default room
        Document defaultDoc = new Document("id", discordId).append("room", defaultRoom).append("cash", "100"); // create default document to check if user has already been registered
        if (!mongo.checkIfExists(defaultDoc)) { // if document doest exist in database
            try {
                mongo.insert(defaultDoc);
                roomNum++;
                ctx.getChannel().sendMessage("Alright I have a spot open for ya!\n" +
                        "I have registered " + ctx.getAuthor().getName() + " to room number " + roomNum).queue();
            } catch (Exception e) {
                ctx.getChannel().sendMessage("I uhh dont feel to good...\n" + e).queue();
                e.printStackTrace();
            }
        } else {
            ctx.getChannel().sendMessage("You already have a spot in the tavern!").queue();
        }
    }
        @Override
        public String getName () {
            //noinspection SpellCheckingInspection
            return "createroom";
        }
        @Override
        public String getHelp () {
            return "Creates a tavern room for you to customize";
        }
    }

