//Vincent Banks
//Tile Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Rooms.Tiles;
import ThreeStrings.Config;
import java.util.List;
import java.util.stream.Collectors;

/*
THIS CLASS DEFINES ALL DECORATION VARIABLES THAT ARE CONSTANTS
AND NECESSARY METHODS FOR EDITING TILES
* */
public class Tiles {
    //constant decoration objects
    // decorations direction array in constructor is formatted n,e,s,w
    public final Decoration plain = new Decoration(
            "oak",
            50,
            Config.get("WOOD_TILE"),
            Config.get("WOOD_TILE"),
            Config.get("WOOD_TILE"),
            Config.get("WOOD_TILE"),
            Config.get("WOOD_TILE")
    );
    public final Decoration purplePillow =
            new Decoration(
                    "purple pillow",
                    100,
                    Config.get("WOOD_TILE_PURPLE_N"),
                    Config.get("WOOD_TILE_PURPLE_N"),
                    Config.get("WOOD_TILE_PURPLE_E"),
                    Config.get("WOOD_TILE_PURPLE_S"),
                    Config.get("WOOD_TILE_PURPLE_W")
            );
    //array of decorations
    public final List<Decoration> decorations  = List.of(
            plain,
            purplePillow
    );
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
    public String getEmoji(String userRequest,Decoration decoration){
        switch (userRequest.toLowerCase()) {
            case "n":
                return decoration.directions.get(0);
            case "e":
                return decoration.directions.get(1);

            case "s":
                return decoration.directions.get(2);

            case "w":
                return decoration.directions.get(3);
        }
        return null;
    }
    /////
    public int getRoomIndex(int index){
        if (index <= 5) {
            return index-1;
        }
        else if(index <= 10){
            return index;
        } else if(index <= 15){
            return index + 1;
        } else if(index <= 20){
            return index + 2;
        } else {
            return index + 3;
        }
    }
    /////
}
