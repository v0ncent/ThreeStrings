//Vincent Banks
//Tile Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms;
import ThreeStrings.Config;

import java.util.Arrays;
import java.util.Objects;

/*
THIS CLASS DEFINES ALL TILE STRING VARIABLES THAT ARE CONSTANTS
AND NECESSARY METHODS FOR EDITING TILES
* */
public class Tiles {
    final Decoration plain = new Decoration("oak","0","0","0","0");
    final Decoration purplePillow = new Decoration("purplepillow","1n","1e","1s","1w");
    //
    final Decoration[] decorations  = {
            plain,
            purplePillow
    };
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
    public String getDirection(Decoration decoration, String direction){
        switch (direction) {
            case "n":
                return decoration.directions[0];
            case "e":
                System.out.println(decoration.directions[1]);
                return decoration.directions[1];
            case "s":
                System.out.println(decoration.directions[2]);
                return decoration.directions[2];
            case "w":
                System.out.println(decoration.directions[3]);
                return decoration.directions[3];
        }
        return null;
    }
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

    //will be deleted once db can update field
    public String formatRoomArrayAsString(String[] roomArray){
        for (int i = 0; i < roomArray.length; i++) {
            if (Objects.equals(roomArray[i], "0")) {
                roomArray[i] = Config.get("WOOD_TILE");
            } else if (roomArray[i].equals("1n")) {
                roomArray[i] = Config.get("WOOD_TILE_PURPLE_N");
            } else if (roomArray[i].equals("1e")) {
                roomArray[i] = Config.get("WOOD_TILE_PURPLE_E");
            } else if (roomArray[i].equals("1s")) {
                roomArray[i] = Config.get("WOOD_TILE_PURPLE_S");
            } else if (roomArray[i].equals("1w")) {
                roomArray[i] = Config.get("WOOD_TILE_PURPLE_W");
            } else if (roomArray[i].equals("n")) {
                roomArray[i] = "\n";
            } else if (roomArray[i].equals(" ")){
                roomArray[i] = "";
            }
        }
        return Arrays.toString(roomArray)
                .replaceAll("\\[", "")
                .replaceAll(",", "")
                .replaceAll("\\]", "")
                .replaceAll(" ","");
    }
}
