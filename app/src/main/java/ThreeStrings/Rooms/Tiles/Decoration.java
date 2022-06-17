//Vincent Banks
//Decoration Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms.Tiles;
import java.util.List;
/*This class is the decoration object
Only needed methods should be created within this class
* */
public class Decoration {
    String name;
    int cost;
    String thumbNail;
    int sellPrice;
    List<String> directions;
    public Decoration(String name,
                      int cost,
                      String thumbNail,
                      String north,
                      String east,
                      String south,
                      String west){
        this.name = name;
        this.cost = cost;
        this.thumbNail = thumbNail;
        this.directions = List.of(north,east,south,west);
    }
    public String getName(){
        return name;
    }
    public int getCost(){
        return cost;
    }
    public String getThumbNail(){
        return thumbNail;
    }
}
