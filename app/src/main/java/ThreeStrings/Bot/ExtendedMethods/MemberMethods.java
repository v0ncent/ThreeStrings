//Vincent Banks
//MemberMethods Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot.ExtendedMethods;

import ThreeStrings.Bot.Database.MemberMongo;
import ThreeStrings.Bot.Rooms.RoomMethods.RoomMethods;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemberMethods {
    final MemberMongo mongodb;

    public MemberMethods(){
        this.mongodb = new MemberMongo();
    }

    public List<String> getRoom(Long id){
        Document sampleDoc = new Document("id",id);

        if(mongodb.checkIfExists(sampleDoc)){ //if user is registered
            ArrayList<Document> results = mongodb.collection.find(
                    sampleDoc
            ).projection( //get cash field
                    new Document("room",1).append("_id",0)
            ).into(new ArrayList<>());

            return results
                    .get(0)
                    .getList("room",String.class);
        }

        return List.of("Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !"
        );
    }

    public String getRoomAsString(Long id){
        Document sampleDoc = new Document("id",id);

        if(mongodb.checkIfExists(sampleDoc)){ //if user is registered
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection( //get cash field
                    new Document("room",1).append("_id",0)
            ).into(new ArrayList<>());

            final RoomMethods roomTool = new RoomMethods(results
                    .get(0)
                    .getList("room",String.class));

            return roomTool.formatRoomAsString();
        }

        return "Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !";
    }

    public Long getDragons(long id){
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db

        if(mongodb.checkIfExists(sampleDoc)){ //if user is registered
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection( //get cash field
                    new Document("dragons",1).append("_id",0)
            ).into(new ArrayList<>());

            try {
                return results
                        .get(0)
                        .getLong("dragons");
            } catch (Exception e){
                e.printStackTrace();
            }

        }

        return 0L;
    }

    public Serializable getGoldStars(long id){
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db

        if (mongodb.checkIfExists(sampleDoc)){ //if document exists
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection( //grab gold stars field
                    new Document("goldstars",1).append("_id",0)
            ).into(new ArrayList<>());

            return results
                    .get(0)
                    .getInteger("goldstars");
        }

        return "Looks like you haven't registered for a room in the tavern yet.\nTo register please use !createroom !";
    }

    public void eggFound(long id){
        Document sampleDoc = new Document("id",id); //sample document for finding user.

        int userStars = (int) getGoldStars(id);
        userStars++; //+1 to stars GOOD JOB

        try {
            Bson updates = Updates.combine(
                    Updates.set("goldstars",userStars));

            mongodb.updateField(sampleDoc,updates);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<String> getInventory(long id){
        Document sampleDoc = new Document("id",id); //create sample document to check if user is registered in db

        if (mongodb.checkIfExists(sampleDoc)){ //if document exists
            ArrayList<Document> results = mongodb.collection.find(
                    new Document("id",id)
            ).projection( //grab gold stars field
                    new Document("inventory",1).append("_id",0)
            ).into(new ArrayList<>());

            return results
                    .get(0)
                    .getList("inventory",String.class);
        }

        return null;
    }

    public void subtractDragons(long id,long dragons,long cost){
        try{
            Document sampleDoc = new Document("id",id);
            Bson updates = Updates.combine(
                    Updates.set("dragons",dragons - cost)
            );

            mongodb.updateField(sampleDoc, updates);
        } catch (Exception e){
            System.out.println("Something happened while trying to subtract money!\n" + e);
            e.printStackTrace();
        }

    }
}
