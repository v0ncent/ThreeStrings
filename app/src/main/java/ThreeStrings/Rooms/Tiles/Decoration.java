//Vincent Banks
//Decoration Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms.Tiles;
/*This class is the decoration object
Only needed methods should be created within this class
* */
public class Decoration {
    String name;
    String[] directions;
    String unicode;
    int cost;
    String thumbNail;
    public Decoration(String name, String unicode,int cost,String thumbNail){
        this.name = name;
        this.unicode = unicode;
        this.directions = new String[]{unicode + "n", unicode + "e", unicode + "s", unicode + "w"};
        this.cost = cost;
        this.thumbNail = thumbNail;
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
