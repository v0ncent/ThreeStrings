//Vincent Banks
//DataBase Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
public class DataBase {
    public static void createRoomsTable() throws Exception{
        try{
            Connection connect = MYSQLConnection.getConnection();
            assert connect != null;
            PreparedStatement create = connect.prepareStatement
                    ("CREATE TABLE IF NOT EXISTS rooms" +
                            "(id int NOT NULL AUTO_INCREMENT, user_id varchar(255), user_room varchar(255), PRIMARY KEY(id))");
            create.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void post()throws Exception{
        final String var1 = "penis";
        final String var2 = "cock";
        try{
            Connection connect = MYSQLConnection.getConnection();
            PreparedStatement posted = connect.prepareStatement("INSERT INTO rooms (user_id, user_room) " +
                    "VALUES ('"+var1+"', '"+var2+"') ");
            posted.executeUpdate();
            System.out.println("Ive inserted " +var1 + ","+ var2 + " into table!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
