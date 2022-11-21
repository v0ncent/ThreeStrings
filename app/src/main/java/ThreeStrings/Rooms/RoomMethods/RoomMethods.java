/*Vincent Banks
RoomMethods Class
COPYRIGHT Vincent Banks
 */
package ThreeStrings.Rooms.RoomMethods;
import ThreeStrings.ExtendedMethods.ArrayMethods;
import java.util.List;
@SuppressWarnings("DuplicatedCode")
public class RoomMethods {
    final List<String> userRoom;
    public RoomMethods(List<String> room) {
        this.userRoom = room;
    }
    public String formatRoomAsString(){
        return ArrayMethods.arrayAsString(userRoom);
    }
    public static boolean checkIfValidRoom(String userRequest){
        try {
            int index = Integer.parseInt(userRequest);
            return index > 0 && index < 26;
        }catch (Exception e){
            return false;
        }
    }
    }

