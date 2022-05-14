/*Vincent Banks
RoomMethods Class
COPYRIGHT Vincent Banks
 */
package ThreeStrings.Rooms;
import ThreeStrings.Config;
import ThreeStrings.command.CommandContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
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
     final String plainTile = tiles.plainTile;
     final String pillowTile1 = tiles.pillowTile1;
     final String pillowTile2 = tiles.pillowTile2;
     final String pillowTile3 = tiles.pillowTile3;
     final String pillowTile4 = tiles.pillowTile4;
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
                roomArray[i] = plainTile;
            } else if (roomArray[i].equals("1")) {
                roomArray[i] = pillowTile1;
            } else if (roomArray[i].equals("2")) {
                roomArray[i] = pillowTile2;
            } else if (roomArray[i].equals("3")) {
                roomArray[i] = pillowTile3;
            } else if (roomArray[i].equals("4")) {
                roomArray[i] = pillowTile4;
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
                .replaceAll("n","")
                .replaceAll(" ","");
        return formattedRoom.split("");
    }
    }

