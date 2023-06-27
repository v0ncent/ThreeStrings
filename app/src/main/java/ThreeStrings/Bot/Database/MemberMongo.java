//Vincent Banks
//MONGODB Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot.Database;

import ThreeStrings.Bot.Config;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;

public final class MemberMongo implements Imongo{
    private final MongoClient client = MongoClients.create(Config.get("MONGO"));
    private final MongoDatabase db = client.getDatabase("DBStrings");
    public final MongoCollection<Document> collection = db.getCollection("member_items");

    @Override
    public void insert(Document items){
        collection.insertOne(items);
    }

    @Override
    public boolean checkIfExists(Document items){
        return collection.find(items).cursor().hasNext();
    }

    @Override
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

    @Override
    public void updateField(Document query, Bson update){
        collection.findOneAndUpdate(query,update);
    }
}