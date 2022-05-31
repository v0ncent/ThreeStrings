//Vincent Banks
//Tile Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms;
/*
THIS CLASS DEFINES ALL DECORATION VARIABLES THAT ARE CONSTANTS
AND NECESSARY METHODS FOR EDITING TILES
* */
public class Tiles {
    //constant decoration objects
    final Decoration plain = new Decoration("oak","0");
    final Decoration purplePillow = new Decoration("purple pillow","1");
    //array of decorations
    final Decoration[] decorations  = {
            plain,
            purplePillow
    };
    //get decoration method
    public Decoration getDecoration(String tile){
        Decoration decorationContext;
        for (Decoration decoration : decorations) {
            if (tile.equals(decoration.getName())) {
                decorationContext = decoration;
                return decorationContext;
            }
        }
        return null;
    }
    //get direction method
    public String getDirection(Decoration decoration, String direction){
        switch (direction) {
            case "n":
                return decoration.directions[0];
            case "e":
                return decoration.directions[1];
            case "s":
                return decoration.directions[2];
            case "w":
                return decoration.directions[3];
        }
        return null;
    }
    //get tile position method
    public static int getTilePosition(String message){
        int tileSpot;
        switch (message) {
            case "1":
                tileSpot = 0;
                return tileSpot;
            case "2":
                tileSpot = 1;
                return tileSpot;
            case "3":
                tileSpot = 2;
                return tileSpot;
            case "4":
                tileSpot = 3;
                return tileSpot;
            case "5":
                tileSpot = 4;
                return tileSpot;
            case "6":
                tileSpot = 6;
                return tileSpot;
            case "7":
                tileSpot = 7;
                return tileSpot;
            case "8":
                tileSpot = 8;
                return tileSpot;
            case "9":
                tileSpot = 9;
                return tileSpot;
            case "10":
                tileSpot = 10;
                return tileSpot;
            case "11":
                tileSpot = 12;
                return tileSpot;
            case "12":
                tileSpot = 13;
                return tileSpot;
            case "13":
                tileSpot = 14;
                return tileSpot;
            case "14":
                tileSpot = 15;
                return tileSpot;
            case "15":
                tileSpot = 16;
                return tileSpot;
            case "16":
                tileSpot = 18;
                return tileSpot;
            case "17":
                tileSpot = 19;
                return tileSpot;
            case "18":
                tileSpot = 20;
                return tileSpot;
            case "19":
                tileSpot = 21;
                return tileSpot;
            case "20":
                tileSpot = 22;
                return tileSpot;
            case "21":
                tileSpot = 24;
                return tileSpot;
            case "22":
                tileSpot = 25;
                return tileSpot;
            case "23":
                tileSpot = 26;
                return tileSpot;
            case "24":
                tileSpot = 27;
                return tileSpot;
            case "25":
                tileSpot = 28;
                return tileSpot;
        }
        return 9999;
    }
}
