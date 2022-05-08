//Vincent Banks
//MemberMethods Class
//COPYRIGHT Vincent Banks
package ThreeStrings.ExtendedMethods;
import ThreeStrings.Database.MONGODB;
import ThreeStrings.Rooms.RoomMethods;
import org.bson.Document;
import java.util.ArrayList;
@SuppressWarnings("SpellCheckingInspection")
public class MemberMethods {
    final MONGODB mongodb = new MONGODB(); //create new instance of mongo object
    public String getRoom(long id) { //getRoom method
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db
        if (mongodb.checkIfExists(sampleDoc)){
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection(
                    new Document("room",1).append("_id",0)
            ).into(new ArrayList<>()); //pull user specific entry and room into array list
            RoomMethods roomTool = new RoomMethods(results); //use the roomTool to format the room
            return roomTool.formatRoom();
        }
        return "Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !";
    }
    //
}
