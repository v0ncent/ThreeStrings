/*
Vincent Banks
Imongo Interface
COPYRIGHT Vincent Banks
 */
package ThreeStrings.Bot.Database;

import org.bson.Document;
import org.bson.conversions.Bson;

public interface Imongo {
    /**
     * Inserts a document into a MongoDB Cluster.
     * @param items Document POJO to be inserted into cluster.
     */
    void insert(Document items);

    /**
     * Checks if the passed Document exists within a MongoDB Cluster.
     * @param items Document POJO to be checked.
     * @return Wether or not the Document was found in Cluster.
     */
    boolean checkIfExists(Document items);

    /**
     * Retrieves a member's unique Document from a MongoDB Cluster
     * @param memberId Discord Member id to pull Document of from Cluster.
     * @return The member's Document as in Cluster.
     */
    Document pull(long memberId);

    /**
     * Updates a Document within a MongoDB Cluster.
     * @param query Document to be updated.
     * @param update Changes to be given to Queried Document.
     */
    void updateField(Document query, Bson update);
}
