package ThreeStrings.Rooms;

import ThreeStrings.Database.MONGODB;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import org.bson.Document;

public class CreateRoomCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        MONGODB mongo = new MONGODB();
        long discordId = ctx.getAuthor().getIdLong();
        Document sampleDoc = new Document("id",discordId).append("room", "default room").append("cash","100");
        if(!mongo.checkIfExists(sampleDoc)){//if document is not already in database
            try{
                mongo.insert(sampleDoc);
                ctx.getChannel().sendMessage("Alright I have just the spot for you!").queue();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            ctx.getChannel().sendMessage("Hmm you cant have multiple rooms sorry buster.").queue();
        }
    }
    @Override
    public String getName() {
        return "createroom";
    }

    @Override
    public String getHelp() {
        return "creates a spot in the tavern for you!";
    }
}
