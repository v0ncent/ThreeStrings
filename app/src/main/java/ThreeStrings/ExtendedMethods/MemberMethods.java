//Vincent Banks
//MemberMethods Class
//COPYRIGHT Vincent Banks
package ThreeStrings.ExtendedMethods;
import ThreeStrings.Database.MemberMongo;
import ThreeStrings.Rooms.RoomMethods.RoomMethods;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("SpellCheckingInspection")
public class MemberMethods {
    final MemberMongo mongodb = new MemberMongo(); //create new instance of mongo object
    public String getRoomAsString(long id) { //getRoomAsString method
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db
        if (mongodb.checkIfExists(sampleDoc)){
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection(
                    new Document("room",1).append("_id",0)
            ).into(new ArrayList<>()); //pull user specific entry and room into array list
            RoomMethods roomTool = new RoomMethods(results); //use the roomTool to format the room
            return roomTool.formatRoomAsString();
        }
        return "Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !";
    }
    public String[] getRoomAsArray(long id) { //getRoomAsArray method
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db
        if (mongodb.checkIfExists(sampleDoc)){
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection(
                    new Document("room",1).append("_id",0)
            ).into(new ArrayList<>()); //pull user specific entry and room into array list
            RoomMethods roomTool = new RoomMethods(results); //use the roomTool to format the room
            return roomTool.formatRoomAsArray();
        }
        return null;
    }
    public Serializable getCash(long id, boolean includeSign){
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db
        if(mongodb.checkIfExists(sampleDoc)){
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection(
                    new Document("cash",1).append("_id",0)
            ).into(new ArrayList<>());
            if(includeSign){
                return results.get(0)
                        .getInteger("cash")
                        + "$";
            }else{
                return results
                        .get(0)
                        .getInteger("cash");
            }
        }
        return "Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !";
    }
    public Serializable getGoldStars(long id){
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db
        if (mongodb.checkIfExists(sampleDoc)){
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection(
                    new Document("goldstars",1).append("_id",0)
            ).into(new ArrayList<>()); //pull user specific entry and room into array list
            return results
                    .get(0)
                    .getInteger("goldstars");
        }
        return "Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !";
    }
    public void eggFound(long id){
        Document sampleDoc = new Document("id",id);
        int userStars = (Integer) getGoldStars(id);
        userStars++;
        Bson updates = Updates.combine(
                Updates.set("goldstars",userStars));
        mongodb.updateField(sampleDoc,updates);
    }
    //
}
