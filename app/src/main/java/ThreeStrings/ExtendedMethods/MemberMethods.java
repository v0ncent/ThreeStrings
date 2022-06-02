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
    public Serializable getCash(long id, boolean includeSign){ //getCash method
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db
        if(mongodb.checkIfExists(sampleDoc)){ //if user is registered
            ArrayList<Document> results = mongodb.collection.find( //push result into arraylist
                    new Document("id",id)
            ).projection( //get cash field
                    new Document("cash",1).append("_id",0)
            ).into(new ArrayList<>());
            if(includeSign){ //if true
                return results.get(0) //return cash with a dollar sign appended
                        .getInteger("cash")
                        + "$";
            }else{ //just return cash integer
                return results
                        .get(0)
                        .getInteger("cash");
            }
        } //if none of above is applicable send this message
        return "Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !";
    }
    public Serializable getGoldStars(long id){ //getGoldStars method
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db
        if (mongodb.checkIfExists(sampleDoc)){ //if document exists
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection( //grab gold stars field
                    new Document("goldstars",1).append("_id",0)
            ).into(new ArrayList<>()); //pull user specific entry and room into array list
            return results //return field as integer
                    .get(0)
                    .getInteger("goldstars");
        } //if none of above is applicable send this message
        return "Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !";
    }
    public void eggFound(long id){ //eggFound Method
        Document sampleDoc = new Document("id",id); //sample document for finding user.
        int userStars = (Integer) getGoldStars(id); //getGoldStars method and cast it to a integer
        userStars++; //+1 to stars GOOD JOB
        try {
            Bson updates = Updates.combine( //create bson update with field and updated stars
                    Updates.set("goldstars",userStars));
            mongodb.updateField(sampleDoc,updates); //update field
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //
}
