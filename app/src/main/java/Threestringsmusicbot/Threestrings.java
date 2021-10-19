//Vincent Banks
//ThreeStrings Bot Main Class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot;
import javax.security.auth.login.LoginException;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDABuilder;       //importing necessary JDA Classes
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
public class Threestrings {
    public static void main(String[] args) throws LoginException {  //LoginException allows for bot to log into account
        JDABuilder threeStrings = JDABuilder.createDefault(Config.get("TOKEN")); //create new bot with token in .env config file
        threeStrings.setActivity(Activity.playing("the Lute!")); //set activity status
        threeStrings.setStatus(OnlineStatus.ONLINE); //set online status to online
        EventWaiter waiter = new EventWaiter(); //add event waiter class
        threeStrings.addEventListeners(new Listener(waiter), waiter); //allows for the commands class to function and lets bot listen for commands
        threeStrings.setChunkingFilter(ChunkingFilter.ALL); //allows for ThreeStrings to see all members of a discord
        threeStrings.setMemberCachePolicy(MemberCachePolicy.ALL);
        threeStrings.enableIntents(GatewayIntent.GUILD_VOICE_STATES); //giving bot permission to view voice states
        threeStrings.enableCache(CacheFlag.VOICE_STATE); //enable voice state cache
        threeStrings.enableIntents(GatewayIntent.GUILD_MEMBERS); //giving self permission to view members
        threeStrings.build(); //tells bot to log in
    }
}