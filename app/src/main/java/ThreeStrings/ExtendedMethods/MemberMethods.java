//Vincent Banks
//MemberMethods Class
//COPYRIGHT Vincent Banks
package ThreeStrings.ExtendedMethods;
import ThreeStrings.Database.MONGODB;
import org.bson.Document;
public class MemberMethods {
    MONGODB mongodb = new MONGODB();
    public String getRoom(long id) {
        Document userDoc = new Document("id", id);
        try {
            if(mongodb.checkIfExists(userDoc)){
                return mongodb.collection.find(userDoc).cursor().next().toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            return "Something went wrong " + e;
        }
        return null;
    }
}
