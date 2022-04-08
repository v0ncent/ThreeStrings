//Vincent Banks
//MemberMethods Class
//COPYRIGHT Vincent Banks
package ThreeStrings.ExtendedMethods;
import ThreeStrings.Database.MONGODB;
import org.bson.Document;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MemberMethods {
    MONGODB mongodb = new MONGODB();
    public String getRoom(long id) {
        Document userDoc = new Document("id", id);
        try {
            if(mongodb.checkIfExists(userDoc)){
                Pattern pattern = Pattern.compile("<:woodtile:940727196157374506>", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(mongodb.collection.find(userDoc).cursor().next().toString());
                String[] matches = Pattern.compile("<:woodtile:940727196157374506>")
                        .matcher(mongodb.collection.find(userDoc).cursor().next().toString())
                        .results()
                        .map(MatchResult::group)
                        .toArray(String[]::new);
                boolean matchFound = matcher.find();
                if(matchFound){
                    String roomFormatted = Arrays.toString(matches).replaceAll("[\\[]","").replaceAll(",","").replaceAll("\\]","");
                    return roomFormatted;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return "Something went wrong " + e;
        }
        return null;
    }
}
