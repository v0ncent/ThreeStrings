//Vincent Banks
//Tile Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot.Rooms.Tiles;

import ThreeStrings.Bot.Config;
import java.util.List;

/*
THIS CLASS DEFINES ALL DECORATION VARIABLES THAT ARE CONSTANTS
AND NECESSARY METHODS FOR EDITING TILES
* */
public class Tiles {
    //constant decoration objects
    // decorations direction array in constructor is formatted n,e,s,w
    public static final Decoration PLAIN = new Decoration(
            "oak",
            50,
            Config.get("WOOD_TILE"), //thumbnail
            Config.get("WOOD_TILE"),
            Config.get("WOOD_TILE"),
            Config.get("WOOD_TILE"),
            Config.get("WOOD_TILE")
    );

    public static final Decoration PURPLE_PILLOW = new Decoration(
            "purple pillow",
            150,
            Config.get("WOOD_TILE_PURPLE_N"), //thumbnail
            Config.get("WOOD_TILE_PURPLE_N"),
            Config.get("WOOD_TILE_PURPLE_E"),
            Config.get("WOOD_TILE_PURPLE_S"),
            Config.get("WOOD_TILE_PURPLE_W")
            );

    //array of decorations
    public static final List<Decoration> DECORATIONS  = List.of(
            PLAIN,
            PURPLE_PILLOW
    );

    public static Decoration getDecoration(String tile){
        Decoration decorationContext;

        for (Decoration decoration : DECORATIONS) {
            if (tile.equals(decoration.getName())) {
                decorationContext = decoration;

                return decorationContext;
            }

        }

        return null;
    }

    public static String getEmoji(String userRequest,Decoration decoration){
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

    //im quite happy with this algorithm :)
    public static int getRoomIndex(int index){
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

    public static boolean checkIfValidDirection(String userRequest){
        List<String> directions = List.of("n","e","s","w");
        return directions.stream().anyMatch((it) -> it.equalsIgnoreCase(userRequest));
    }
}
