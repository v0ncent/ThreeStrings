/*
Vincent Banks
ShopMongo Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Database;
import org.bson.Document;
import org.bson.conversions.Bson;
public class ShopMongo implements Imongo{
    @Override
    public void insert(Document items) {

    }

    @Override
    public boolean checkIfExists(Document items) {
        return false;
    }

    @Override
    public Document pull(long memberId) {
        return null;
    }

    @Override
    public void updateField(Document query, Bson update) {

    }
}
