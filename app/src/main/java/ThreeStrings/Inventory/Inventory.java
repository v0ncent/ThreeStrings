//Vincent Banks
//Inventory Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Inventory;
import ThreeStrings.ExtendedMethods.MemberMethods;
import java.util.List;
public class Inventory {
    MemberMethods memberTool;
    long userId;
    private final List<String> playerInventory;
    public Inventory(long userId){
        this.memberTool = new MemberMethods();
        this.userId = userId;
        this.playerInventory = memberTool.getInventory(userId);
    }
    public void addToInventory(String decoration, long amount){
        for (int i = 0; i <=amount; i++){
            playerInventory.add(decoration); //add into listing number of times player asks
        }
    }
}
