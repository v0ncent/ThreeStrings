//Vincent Banks
//DmCommand Class
//Copyright Vincent Banks
package Threestringsmusicbot.Music;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import Threestringsmusicbot.lavaplayer.GuildMusicManager;
import Threestringsmusicbot.lavaplayer.PlayerManager;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.awt.*;
import java.util.concurrent.TimeUnit;
@SuppressWarnings("ConstantConditions")
public class DmCommand implements ICommand {
    EventWaiter waiter; //implement eventwaiter class into dmcommand under variable waiter
    public DmCommand(EventWaiter waiter){ //create waiter constructor
        this.waiter = waiter;
    }
    @Override
    public void handle(CommandContext ctx) {
        final Member self = ctx.getSelfMember(); //implement variable to get bot
        final GuildVoiceState selfVoiceState = self.getVoiceState(); //gets bot voice state
        final TextChannel channel = ctx.getChannel(); //implement variable to get channel
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        if (!selfVoiceState.inVoiceChannel()) { //if bot is not in vc
            channel.sendMessage("I cant play unless im on the stage!").queue();
            return;
        }
        final Member member = ctx.getMember(); //create variable for getting user
        final GuildVoiceState memberVoiceState = member.getVoiceState(); //create variable for getting user voice state
        if (!memberVoiceState.inVoiceChannel()) { //if member is not in vc
            channel.sendMessage("You cant hear me play if you are not in a voice channel!").queue();
            return;

        }
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){ //if member is not in same voice channel as bot
            channel.sendMessage("You cant hear me play if you arent at the stage!").queue();
            return;
        }
        //create embed for user
        ctx.getChannel().sendMessage("```Reply with 1,2,3,4,5,6 within a minute to select a playlist```").queue();
        EmbedBuilder dmEmbed = new EmbedBuilder(); //implement EmbedBuilder
        dmEmbed.setTitle("Select A Playlist");
        dmEmbed.setColor(Color.pink);
        dmEmbed.setImage("https://pbs.twimg.com/media/Ds7gz-0V4AAwcJn.jpg");
        dmEmbed.addField("**Option 1** ","penis music playlist",true);
        dmEmbed.addField("**option 2** ","dragostea din tei",true);
        dmEmbed.addField("**option 3** ","GAMMA KNIFE",true);
        dmEmbed.addField("**option 4** ","Pee Is Stored In The Balls",true);
        dmEmbed.addField("**option 5** ","Dark Souls Soundtrack",true);
        dmEmbed.addField("**option 6** ","Modjo - Chillin",true);
        dmEmbed.addBlankField(true);
        dmEmbed.addBlankField(true);
        dmEmbed.addBlankField(true);
        dmEmbed.setFooter("For your roleplaying needs! Just ask Threestrings!");
        ctx.getChannel().sendMessageEmbeds(dmEmbed.build()).queue(); //sends embed to chat
        ctx.getChannel().sendMessage("How can I help god?").queue();
        //add event waiters when option is picked designated playlist is played, when anotther option is picked it clears queue and stops any current tracks
            waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    e -> e.getMessage().getContentRaw().equals("1") && e.getChannel().equals(ctx.getChannel()), e -> {
                        musicManager.scheduler.player.stopTrack();
                        musicManager.scheduler.queue.clear();
                        PlayerManager.getInstance().LoadAndPlay(channel, "https://www.youtube.com/watch?v=xHby_3c03gU&list=PLM9oxU_q3nqvds3LhMHvPNwMD_wX0xcld"); //sends bot to channel and plays song on instance
                    }, 1L, TimeUnit.MINUTES,
                    () -> channel.sendMessage("").queue()); //add timeout);

        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getMessage().getContentRaw().equals("2") && e.getChannel().equals(ctx.getChannel()), e -> {
                    musicManager.scheduler.player.stopTrack();
                    musicManager.scheduler.queue.clear();
                    PlayerManager.getInstance().LoadAndPlay(channel, "https://www.youtube.com/watch?v=YnopHCL1Jk8"); //sends bot to channel and plays song on instance
                }, 1L, TimeUnit.MINUTES,
                () -> channel.sendMessage("").queue()); //add timeout);

        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getMessage().getContentRaw().equals("3") && e.getChannel().equals(ctx.getChannel()), e -> {
                    musicManager.scheduler.player.stopTrack();
                    musicManager.scheduler.queue.clear();
                    PlayerManager.getInstance().LoadAndPlay(channel, "https://www.youtube.com/watch?v=nC7ii3Ir-no"); //sends bot to channel and plays song on instance
                }, 1L, TimeUnit.MINUTES,
                () -> channel.sendMessage("").queue()); //add timeout);

        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getMessage().getContentRaw().equals("4") && e.getChannel().equals(ctx.getChannel()), e -> {
                    musicManager.scheduler.player.stopTrack();
                    musicManager.scheduler.queue.clear();
                    PlayerManager.getInstance().LoadAndPlay(channel, "https://www.youtube.com/watch?v=pKQp61e94VE"); //sends bot to channel and plays song on instance
                }, 1L, TimeUnit.MINUTES,
                () -> channel.sendMessage("").queue()); //add timeout);

        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getMessage().getContentRaw().equals("5") && e.getChannel().equals(ctx.getChannel()), e -> {
                    musicManager.scheduler.player.stopTrack();
                    musicManager.scheduler.queue.clear();
                    PlayerManager.getInstance().LoadAndPlay(channel, "https://www.youtube.com/watch?v=S7XfKAx9jGQ&list=PLCLeSTzz6trYB89ZYFswkVKoQWPZ6e7_1"); //sends bot to channel and plays song on instance
                }, 1L, TimeUnit.MINUTES,
                () -> channel.sendMessage("").queue()); //add timeout);

        waiter.waitForEvent(GuildMessageReceivedEvent.class,
                e -> e.getMessage().getContentRaw().equals("6") && e.getChannel().equals(ctx.getChannel()), e -> {
                    musicManager.scheduler.player.stopTrack();
                    musicManager.scheduler.queue.clear();
                    PlayerManager.getInstance().LoadAndPlay(channel, "https://www.youtube.com/watch?v=hTl0F2cKzLk"); //sends bot to channel and plays song on instance
                }, 1L, TimeUnit.MINUTES,
                () -> channel.sendMessage("").queue()); //add timeout);
    }
    @Override
    public String getName() {
        return "dm";
    }
    @Override
    public String getHelp() {
        return "A command for the dungeon Master to play select music playlist for the roleplaying moment!";
    }
}
