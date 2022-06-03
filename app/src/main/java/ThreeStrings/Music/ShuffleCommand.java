//Vincent Banks
//ShuffleCommand Class
//Copyright Vincent Banks
package ThreeStrings.Music;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import ThreeStrings.lavaplayer.GuildMusicManager;
import ThreeStrings.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
@SuppressWarnings("ConstantConditions")
public class ShuffleCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel(); //implement variable to get channel
        final Member self = ctx.getSelfMember(); //implement variable to get bot
        final GuildVoiceState selfVoiceState = self.getVoiceState(); //gets bot voice state
        if (!selfVoiceState.inVoiceChannel()) { //if bot is not in vc
            channel.sendMessage("I need to be on stage so I can play.").queue();
            return;
        }
        final Member member = ctx.getMember(); //create variable for getting user
        final GuildVoiceState memberVoiceState = member.getVoiceState(); //create variable for getting user voice state
        if (!memberVoiceState.inVoiceChannel()) { //if member is not in vc
            channel.sendMessage("What do you mean? You're not even in the tavern!").queue();
            return;
        }
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) { //if member is not in same voice channel as bot
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild()); //get our music manager with our music manager variable
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue; //implement our queue system variable
        final List<AudioTrack> trackList = new ArrayList<>(queue);
        if(queue.isEmpty()){  //if there is no playlist to shuffle
            channel.sendMessage("There is no playlist for me to shuffle!").queue();
        }else{
            Collections.shuffle(trackList); //shuffle the tracklist
            musicManager.scheduler.queue.clear(); //clear the queue
            for(AudioTrack track : trackList){
                musicManager.scheduler.queue(track); //iterate and queue each track
            }
            channel.sendMessage("Shuffled the playlist!").queue(); //send message to discord
        }
    }
    @Override
    public String getName() {
        return "shuffle";
    }
    @Override
    public String getHelp() {
        return "Shuffles the queue if there is one.";
    }

    @Override
    public String getType() {
        return "music";
    }
}