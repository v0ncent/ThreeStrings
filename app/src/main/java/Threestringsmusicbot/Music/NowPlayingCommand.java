//Vincent Banks
//NowPlayingCommand
//Copyright Vincent Banks
package Threestringsmusicbot.Music;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import Threestringsmusicbot.lavaplayer.GuildMusicManager;
import Threestringsmusicbot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
@SuppressWarnings("ConstantConditions")
public class NowPlayingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be on a stage in order for this to work!").queue();
            return;
        }
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in a voice channel in order for me to do that!").queue();
            return;
        }
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be near the stage in order for me to do that!").queue();
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer; //create new variable with audioPlayer class
        final AudioTrack track = audioPlayer.getPlayingTrack(); //create new variable for current playing audio track
        if (audioPlayer.getPlayingTrack() == null){  //if no track is playing this statement runs
            channel.sendMessage("Uhhhh, im not playing anything.").queue();
            return;
        }
        final AudioTrackInfo info = track.getInfo(); //create variable for getting track info
        channel.sendMessageFormat("Currently playing `%s` by `%s`(Link: <%s>)", info.title, info.author, info.uri).queue(); //sends message from recieved info
    }
    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String getHelp() {
        return "Shows what im currently playing";
    }
}
