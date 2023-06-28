 //Vincent Banks
//NowPlayingCommand
//Copyright Vincent Banks
package ThreeStrings.Bot.Music;

import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import ThreeStrings.Bot.lavaplayer.GuildMusicManager;
import ThreeStrings.Bot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public final class NowPlayingCommand implements ICommand {
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

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (audioPlayer.getPlayingTrack() == null){
            channel.sendMessage("Uhhh, Im not even playing anything.").queue();
            return;
        }

        final long timeLeft = track.getDuration() - track.getPosition();
        final AudioTrackInfo info = track.getInfo();

        channel.sendMessageFormat("Currently playing `%s` by `%s`(Link: <%s>)", info.title, info.author, info.uri).queue();
        channel.sendMessage("Time left: " + formatTime(timeLeft)).queue();
    }

    private String formatTime(long timeInMillis) { //create formatTime method
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1)/ TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String getHelp() {
        return "Shows what Im currently playing.";
    }

    @Override
    public String getType() {
        return "music";
    }
}
