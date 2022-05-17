/*Vincent Banks
RoomMethods Class
COPYRIGHT Vincent Banks
 */
package ThreeStrings.Rooms;
import ThreeStrings.Config;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
@SuppressWarnings("DuplicatedCode")
public class RoomMethods {
    final ArrayList<org.bson.Document> userRoom;
    public RoomMethods(ArrayList<Document> room) {
        this.userRoom = room;
    }
    Tiles tiles = new Tiles();
    //plain 1
    //purple 2 2n 2e 2s 2w
     public String formatRoomAsString() {
        //grab emoji codes from config
        String room = this.userRoom.toString();
        //format room to just content of field
        String formattedRoom = room.replaceAll("Document", "")
                .replaceAll("\\{", "")
                .replaceAll("room=", "")
                .replaceAll("\\}", "")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "");
        String[] roomArray = formattedRoom.split(" "); //create string array from characters of room thats split on blank spots
        //iterate through roomArray and create elif chain to format with emojis
        for (int i = 0; i < roomArray.length; i++) {
            if (Objects.equals(roomArray[i], "0")) {
                roomArray[i] = Config.get("WOOD_TILE");
            } else if (roomArray[i].equals("1n")) {
                roomArray[i] = Config.get("WOOD_TILE_PURPLE_N");
            } else if (roomArray[i].equals("1e")) {
                roomArray[i] = Config.get("WOOD_TILE_E");
            } else if (roomArray[i].equals("1s")) {
                roomArray[i] = Config.get("WOOD_TILE_S");
            } else if (roomArray[i].equals("1w")) {
                roomArray[i] = Config.get("WOOD_TILE_W");
            } else if (roomArray[i].equals("n")) {
                roomArray[i] = "\n";
            } else if (roomArray[i].equals(" ")){
                roomArray[i] = "";
            }
        }
        //return roomArray and cut off unwanted characters
        return Arrays.toString(roomArray)
                .replaceAll("\\[", "")
                .replaceAll(",", "")
                .replaceAll("\\]", "")
                .replaceAll(" ","");
    }
    public String[] formatRoomAsArray() {
        String room = this.userRoom.toString();
        //format room to just content of field
        String formattedRoom = room.replaceAll("Document", "")
                .replaceAll("\\{", "")
                .replaceAll("room=", "")
                .replaceAll("\\}", "")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll(" ","");
        return formattedRoom.split("");
    }
    }

