//Vincent Banks
//Runner Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot;

import ThreeStrings.CommandLine.CommandLineManager;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

//----Runner Class----
/*
This class instantiates all basic needs for ThreeStrings
Such as JDABuilder Manipulation (status,prefix, gateway intents,etc..)
And sets event listener
And then tells bot to log in
 */
//---------------------
public final class Runner {

    private Runner(){
        //make constructor private so no instances of runner class can be made
    }

    private static final JDABuilder ThreeStrings = JDABuilder.createDefault(Config.get("TOKEN"));

    private static void enableAllIntents() {
        ThreeStrings.setChunkingFilter(ChunkingFilter.ALL);
        ThreeStrings.setMemberCachePolicy(MemberCachePolicy.ALL);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_MEMBERS);
        ThreeStrings.enableCache(CacheFlag.VOICE_STATE);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_MESSAGES);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_VOICE_STATES);
        ThreeStrings.enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS);
        ThreeStrings.enableIntents(GatewayIntent.DIRECT_MESSAGES);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_EMOJIS_AND_STICKERS);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_MESSAGE_TYPING);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_PRESENCES);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_INVITES);
        ThreeStrings.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        ThreeStrings.enableIntents(GatewayIntent.GUILD_MODERATION);
        ThreeStrings.enableIntents(GatewayIntent.SCHEDULED_EVENTS);
    }

    public static void run() {
        ThreeStrings.setActivity(Activity.playing("the Lute!"));
        ThreeStrings.setStatus(OnlineStatus.ONLINE);

        EventWaiter waiter = new EventWaiter();
        ThreeStrings.addEventListeners(new Listener(waiter), waiter);

        enableAllIntents();

        ThreeStrings.build();

        CommandLineManager cmManager = new CommandLineManager();
        cmManager.start();
    }
}