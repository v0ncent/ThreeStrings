/*
Vincent Banks
Shop Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Shop;
import ThreeStrings.Rooms.Tiles.Decoration;
import ThreeStrings.Rooms.Tiles.Tiles;
import java.util.ArrayList;
import java.util.List;
public class Shop {
    private final List<Decoration> shopList = new ArrayList<>();
    Tiles tiles = new Tiles();
    public Shop(){
        addListing(tiles.plain);
        addListing(tiles.purplePillow);
    }
    private void addListing(Decoration decoration){
        boolean nameFound = this.shopList.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(decoration.getName()));
        if(nameFound){
            throw new IllegalArgumentException("Listing already present!");
        }
        shopList.add(decoration);
    }
    public String getShopList(){
        List<String> itemNames = new ArrayList<>();
        for (Decoration decoration : this.shopList) {
            itemNames.add(decoration.getName() + " " + decoration.getCost() + "$");
            itemNames.add("\n");
        }
        return itemNames
                .toString()
                .replaceAll("\\]","")
                .replaceAll("\\[","")
                .replaceAll(",","");
    }

}
