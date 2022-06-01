/*
Vincent Banks
Imongo Interface Class
COPYRIGHT Vincent Banks
 */
package ThreeStrings.Database;
import org.bson.Document;
import org.bson.conversions.Bson;
public interface Imongo {
    void insert(Document items);
    boolean checkIfExists(Document items);
    Document pull(long memberId);
    void updateField(Document query, Bson update);
}
