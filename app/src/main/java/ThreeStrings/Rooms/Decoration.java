//Vincent Banks
//Decoration Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms;
/*This class is the decoration object
Only needed methods should be created within this class
* */
public class Decoration {
    String name;
    String[] directions;
    String unicode;
    public Decoration(String name, String unicode){
        this.name = name;
        this.unicode = unicode;
        this.directions = new String[]{unicode + "n", unicode + "e", unicode + "s", unicode + "w"};
    }
    public String getName(){
        return this.name;
    }
    public String getDirection(int index){
        return directions[index];
    }
    public String getUnicode(){
        return unicode;
    }
}
