//Vincent Banks
//Inventory Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Inventory;
import ThreeStrings.Database.MemberMongo;
import ThreeStrings.ExtendedMethods.MemberMethods;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;
public class Inventory {
    final MemberMethods memberTool;
    long userId;
    List<String> playerInventory;
    final MemberMongo mongo;
    public Inventory(long userId){
        this.memberTool = new MemberMethods();
        this.userId = userId;
        this.playerInventory = memberTool.getInventory(userId);
        this.mongo = new MemberMongo();
    }
    public void addToInventory(String decoration){
        playerInventory.add(decoration);
        try{
            Document sampleDoc = new Document("id",userId);
            Bson updates = Updates.combine( //create bson update with field and updated stars
                    Updates.set("inventory",playerInventory));
            mongo.updateField(sampleDoc,updates);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getPlayerInventoryAsString(){
        int index = 0;
        List<String> itemNames = new ArrayList<>();
        for (String s : playerInventory) {
            if (!s.equals(" ")) {
                index++;
                itemNames.add(
                        "**"+
                        index + "**. " + s + "\n"
                );
            }
        }
        return itemNames
                .toString()
                .replaceAll("]","")
                .replaceAll("\\[","")
                .replaceAll(",","");
    }
    public boolean has(String decoration){
        return playerInventory.stream().anyMatch((it) -> it.equals(decoration));
    }
}
