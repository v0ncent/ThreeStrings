//Vincent Banks
//MONGODB Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Database;
import ThreeStrings.Config;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class MONGODB {
    //create mongoclient var to gain connection to mongo database
   private final MongoClient client = MongoClients.create(Config.get("MONGO"));
   private final MongoDatabase db = client.getDatabase("DBStrings");
   public final MongoCollection<Document> collection = db.getCollection("member_items");
    //database insertion method for uploading info
    public void insert(Document items){ //insert into collection method
        collection.insertOne(items);
    }
    public boolean checkIfExists(Document items){ //check if a document is already present method
        return collection.find(items).cursor().hasNext(); //if cursor finds document it returns true
    }
    public Document pull(long id) {
        Document sampleDoc = new Document("id",id);
        if(checkIfExists(sampleDoc)){
            ArrayList<Document> results = collection.find(
                    new Document("id",id)
            ).into(new ArrayList<>());
            return results.get(0);
        }
        return null;
    }
    public void updateField(Document query, Bson update){
        collection.findOneAndUpdate(query,update);
    }
}
