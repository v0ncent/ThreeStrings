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
    public Decoration(String name, String unicode,int cost){
        this.name = name;
        this.unicode = unicode;
        this.directions = new String[]{unicode + "n", unicode + "e", unicode + "s", unicode + "w"};
        this.cost = cost;
    }
    public String getName(){
        return name;
    }
}
