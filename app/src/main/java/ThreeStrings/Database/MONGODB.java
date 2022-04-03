//Vincent Banks
//MONGODB Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Database;

import ThreeStrings.Config;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

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
        collection.find(items);
        return true;
    }
}
