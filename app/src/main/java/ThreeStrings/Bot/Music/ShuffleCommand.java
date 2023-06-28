//Vincent Banks
//ShuffleCommand Class
//Copyright Vincent Banks
package ThreeStrings.Bot.Music;

import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import ThreeStrings.Bot.lavaplayer.GuildMusicManager;
import ThreeStrings.Bot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("ConstantConditions")
public final class ShuffleCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            channel.sendMessage("I need to be on stage so I can play.").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage("What do you mean? You're not even in the tavern!").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) { //if member is not in same voice channel as bot
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
        final List<AudioTrack> trackList = new ArrayList<>(queue);

        if(queue.isEmpty()){
            channel.sendMessage("There is no playlist for me to shuffle!").queue();

        }else{

            Collections.shuffle(trackList);
            musicManager.scheduler.queue.clear();

            for(AudioTrack track : trackList){
                musicManager.scheduler.queue(track);
            }

            channel.sendMessage("Shuffled the playlist!").queue();
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