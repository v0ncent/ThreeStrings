/*Vincent Banks
RoomMethods Class
COPYRIGHT Vincent Banks
 */
package ThreeStrings.Rooms.RoomMethods;
import java.util.List;
@SuppressWarnings("DuplicatedCode")
public class RoomMethods {
    final List<String> userRoom;
    public RoomMethods(List<String> room) {
        this.userRoom = room;
    }
    public String formatRoomAsString(){
        return userRoom
                .toString()
                .replaceAll("]","")
                .replaceAll("\\[","")
                .replaceAll(",","")
                .replaceAll(" ","");
    }
    }

