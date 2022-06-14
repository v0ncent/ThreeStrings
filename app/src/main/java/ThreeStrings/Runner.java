//Vincent Banks
//Runner Class
//COPYRIGHT Vincent Banks
package ThreeStrings;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import javax.security.auth.login.LoginException;
public final class Runner {
    private Runner(){} //make constructor private so no instances of runner class can be made
    public static void run() throws LoginException {
        JDABuilder ThreeStrings = JDABuilder.createDefault(Config.get("TOKEN")); //create new bot with token in config file
        ThreeStrings.setActivity(Activity.playing("the Lute!")); //set activity status
        ThreeStrings.setStatus(OnlineStatus.ONLINE); //set online status to online
        EventWaiter waiter = new EventWaiter(); //add event waiter class
        ThreeStrings.addEventListeners(new Listener(waiter), waiter); //allows for the commands class to function and lets bot listen for commands
        ThreeStrings.setChunkingFilter(ChunkingFilter.ALL); //allows for ThreeStrings to see all members of a discord
        ThreeStrings.setMemberCachePolicy(MemberCachePolicy.ALL);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_VOICE_STATES); //giving bot permission to view voice states
        ThreeStrings.enableCache(CacheFlag.VOICE_STATE); //enable voice state cache
        ThreeStrings.enableIntents(GatewayIntent.GUILD_MEMBERS); //giving self permission to view members
        ThreeStrings.build(); //tells bot to log in
    }
}
