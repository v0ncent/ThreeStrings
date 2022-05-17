package ThreeStrings.Rooms;

public class Decoration {
    String name;
    String[] directions;
    public Decoration(String name, String north,String east,String south, String west){
        this.name = name;
        this.directions = new String[]{north, east, south, west};
    }
    public String getName(){
        return this.name;
    }
    public String getDirection(int index){
        return directions[index];
    }
}
