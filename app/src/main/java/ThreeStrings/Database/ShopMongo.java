/*
Vincent Banks
ShopMongo Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Database;
import ThreeStrings.Config;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
public class ShopMongo implements Imongo{
    private final MongoClient client = MongoClients.create(Config.get("MONGO"));
    private final MongoDatabase db = client.getDatabase("Shop");
    public final MongoCollection<Document> collection = db.getCollection("The_Shop");
    @Override
    public void insert(Document items) {
        collection.insertOne(items);
    }
    @Override
    public boolean checkIfExists(Document items) {
        return collection.find(items).cursor().hasNext(); //if cursor finds document it returns true
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
    public void updateField(Document query, Bson update) {
        collection.findOneAndUpdate(query,update);
    }
}
