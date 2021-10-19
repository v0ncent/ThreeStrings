//Vincent Banks
//SkipCommand Class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot.Music;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import Threestringsmusicbot.lavaplayer.GuildMusicManager;
import Threestringsmusicbot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
public class SkipCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
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
            channel.sendMessage("You need to be in a voice channel to hear me play!").queue();
            return;
        }
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be near the stage in order to hear me play!").queue();
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer; //create new variable with audioPlayer class

        if (audioPlayer.getPlayingTrack() == null){  //if no track is playing this statement runs
            channel.sendMessage("I cant skip if im not playing anything!").queue();
            return;
        }
        musicManager.scheduler.nextTrack();      //skips track and sends message
        channel.sendMessage("Skipped! No more of this song.").queue();
    }
    @Override
    public String getName() {
        return "skip";
    }
    @Override
    public String getHelp() {
        return "Skips the current song im playing";
    }
}
