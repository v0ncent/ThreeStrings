/*
Vincent Banks
Shop Class
COPYRIGHT Vincent Banks
*/
package ThreeStrings.Bot.Shop;
import ThreeStrings.Bot.Config;
import ThreeStrings.Bot.ExtendedMethods.ArrayMethods;
import ThreeStrings.Bot.ExtendedMethods.MemberMethods;
import ThreeStrings.Bot.Rooms.Tiles.Decoration;
import ThreeStrings.Bot.Rooms.Tiles.Tiles;
import java.util.ArrayList;
import java.util.List;
public class Shop {
    private final List<Decoration> shopList = new ArrayList<>();
    MemberMethods memberTool;
    public Shop(){
        memberTool = new MemberMethods();
        addListing(Tiles.PLAIN);
        addListing(Tiles.PURPLE_PILLOW);
    }
    private void addListing(Decoration decoration){
        boolean nameFound = this.shopList.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(decoration.getName()));
        if(nameFound){
            throw new IllegalArgumentException("Listing already present in shop!");
        }
        shopList.add(decoration);
    }
    public String getShopListAsString(){
        int index = 0;
        List<String> itemNames = new ArrayList<>();
        for (Decoration decoration : this.shopList) {
            index++;
            itemNames
                    .add(index + ". " + decoration.getName()
                            + " **" + decoration.getCost() + Config.get("DRAGON") + "**"
                    + " " + decoration.getThumbNail());
            itemNames.add("\n");
        }
        return ArrayMethods.arrayAsString(itemNames);
    }
    public Decoration buy(int index){
        return shopList.get(index);
    }
    public boolean checkIfValid(String userRequest){
        try {
            return shopList.get(Integer.parseInt(userRequest) - 1) != null;
        } catch (Exception e){
            return false;
        }
    }
    public boolean cantAfford(long discordID, long amount){
        return memberTool.getDragons(discordID) < amount;
    }
}
