//Vincent Banks
//Listener class
//COPYRIGHT Vincent Banks
package ThreeStrings;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
public class Listener  extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class); //implement the Logger class to get rid of error messages
    private final CommandManager manager; //implement our command manager class
    public Listener(EventWaiter waiter){
        manager = new CommandManager(waiter);
    }
    @Override
    public void onReady(@NotNull ReadyEvent event) {   //implement the onReady JDA constructor
        LOGGER.info("ThreeStrings ready to play!"); //tells user that bot is ready to play
    }
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) { //implementing discord message received constructor
        User user = event.getAuthor(); //defines user variable
        if (user.isBot() || event.isWebhookMessage() ){ //if user is a bot or webhook message we simply just return
            return;
        }
        String prefix = Config.get("PREFIX"); //setting prefix to what it is in the database
        String raw = event.getMessage().getContentRaw();
        if (raw.equalsIgnoreCase(prefix + "shutdown") && user.getId().equals(Config.get("OWNER_ID"))){ //using boolean we can create a !shutdown command that only the owner can use with owner id in config
            LOGGER.info("Heading in for the night! Thanks for listening!"); //sends message to user that bot is shutting down
            event.getJDA().shutdown();  //bot shuts down
            BotCommons.shutdown(event.getJDA());
            return;
        }
        if (raw.startsWith(prefix)){  //when a command is executed this if statements tells the command manager class to handle it
            manager.handle(event);
        }
    }
    //Disconnect bot if no users is in vc from disconnecting from server
    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event){
        VoiceChannel channel = event.getChannelLeft();
        List<Member> membersInVc = channel.getMembers();
        if(membersInVc.size() == 1 && membersInVc.get(0).getUser().isBot()){
            event.getGuild().getAudioManager().closeAudioConnection();
        }
    }
    //Disconnect bot if no users is in vc from moving to another channel
    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event){
        VoiceChannel channel = event.getChannelLeft();
        List<Member> membersInVc = channel.getMembers();
        if(membersInVc.size() == 1 && membersInVc.get(0).getUser().isBot()){
            event.getGuild().getAudioManager().closeAudioConnection();
        }
    }
    }
