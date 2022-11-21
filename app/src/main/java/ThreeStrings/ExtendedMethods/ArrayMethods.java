//Vincent Banks
//ArrayMethods Class
//COPYRIGHT Vincent Banks
package ThreeStrings.ExtendedMethods;
import java.util.List;
public class ArrayMethods {
    public static String arrayAsString(List<?> toParse ){
        return toParse.toString()
                .replaceAll("]","")
                .replaceAll("\\[","")
                .replaceAll(",","")
                .replaceAll(" ","");
    }
}
