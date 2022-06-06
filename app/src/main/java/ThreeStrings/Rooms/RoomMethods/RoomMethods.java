/*Vincent Banks
RoomMethods Class
COPYRIGHT Vincent Banks
 */
package ThreeStrings.Rooms.RoomMethods;
import ThreeStrings.Config;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
@SuppressWarnings("DuplicatedCode")
public class RoomMethods {
    final ArrayList<org.bson.Document> userRoom;
    public RoomMethods(ArrayList<Document> room) {
        this.userRoom = room;
    }
     public String formatRoomAsString() {
        String room = this.userRoom.toString();
        //format room to just content of field
        String formattedRoom = room.replaceAll("Document", "")
                .replaceAll("\\{", "")
                .replaceAll("room=", "")
                .replaceAll("}", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "");
        String[] roomArray = formattedRoom.split(" "); //create string array from characters of room that's split on blank spots
        //iterate through roomArray and create elif chain to format with emojis
        for (int i = 0; i < roomArray.length; i++) {
            switch (roomArray[i]) {
                case "0" :
                case "0n":
                case "0e":
                case "0s":
                case "0w":
                    roomArray[i] = Config.get("WOOD_TILE");
                    break;

                case "1n":
                    roomArray[i] = Config.get("WOOD_TILE_PURPLE_N");
                    break;
                case "1e":
                    roomArray[i] = Config.get("WOOD_TILE_PURPLE_E");
                    break;
                case "1s":
                    roomArray[i] = Config.get("WOOD_TILE_PURPLE_S");
                    break;
                case "1w":
                    roomArray[i] = Config.get("WOOD_TILE_PURPLE_W");
                    break;
                case "n":
                    roomArray[i] = "\n";
                    break;
                case " ":
                    roomArray[i] = "";
                    break;
            }
        }
        //return roomArray and cut off unwanted characters
        return Arrays.toString(roomArray)
                .replaceAll("\\[", "")
                .replaceAll(",", "")
                .replaceAll("]", "")
                .replaceAll(" ","");
    }
    public String[] formatRoomAsArray() {
        String room = this.userRoom.toString();
        //format room to just content of field
        String formattedRoom = room.replaceAll("Document", "")
                .replaceAll("\\{", "")
                .replaceAll("room=", "")
                .replaceAll("}", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "");
        return formattedRoom.split(" ");
    }
    }

