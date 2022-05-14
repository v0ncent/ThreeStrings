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
    public final String plainTile = Config.get("WOOD_TILE"); //0
    public final String pillowTile1 = Config.get("WOOD_TILE_PURPLE"); //1
    public final String pillowTile2 = Config.get("WOOD_TILE_PURPLE2"); //2
    public final String pillowTile3 = Config.get("WOOD_TILE_PURPLE3"); //3
    public final String pillowTile4 = Config.get("WOOD_TILE_PURPLE4"); //4  n is NewLine
    //
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
                tileSpot = 5;
                return tileSpot;
            case "7":
                tileSpot = 6;
                return tileSpot;
            case "8":
                tileSpot = 7;
                return tileSpot;
            case "9":
                tileSpot = 8;
                return tileSpot;
            case "10":
                tileSpot = 9;
                return tileSpot;
            case "11":
                tileSpot = 10;
                return tileSpot;
            case "12":
                tileSpot = 11;
                return tileSpot;
            case "13":
                tileSpot = 12;
                return tileSpot;
            case "14":
                tileSpot = 13;
                return tileSpot;
            case "15":
                tileSpot = 14;
                return tileSpot;
            case "16":
                tileSpot = 15;
                return tileSpot;
            case "17":
                tileSpot = 16;
                return tileSpot;
            case "18":
                tileSpot = 17;
                return tileSpot;
            case "19":
                tileSpot = 18;
                return tileSpot;
            case "20":
                tileSpot = 19;
                return tileSpot;
            case "21":
                tileSpot = 20;
                return tileSpot;
            case "22":
                tileSpot = 21;
                return tileSpot;
            case "23":
                tileSpot = 22;
                return tileSpot;
            case "24":
                tileSpot = 23;
                return tileSpot;
            case "25":
                tileSpot = 24;
                return tileSpot;
        }
        return 9999;
    }
    //will be deleted once db can update field
    public String formatRoomArrayAsString(String[] roomArray){
        for (int i = 0; i < roomArray.length; i++) {
            if (Objects.equals(roomArray[i], "0")) {
                roomArray[i] = plainTile;
            } else if (roomArray[i].equals("1")) {
                roomArray[i] = pillowTile1;
            } else if (roomArray[i].equals("2")) {
                roomArray[i] = pillowTile2;
            } else if (roomArray[i].equals("3")) {
                roomArray[i] = pillowTile3;
            } else if (roomArray[i].equals("4")) {
                roomArray[i] = pillowTile4;
            } else if (roomArray[i].equals("n")) {
                roomArray[i] = "\n";
            } else if (roomArray[i].equals(" ")) {
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
