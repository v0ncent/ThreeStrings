//Vincent Banks
//LeaveCommand Class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot.Music;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import Threestringsmusicbot.lavaplayer.GuildMusicManager;
import Threestringsmusicbot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;
@SuppressWarnings("ConstantConditions")
public class LeaveCommand implements ICommand {
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
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be near the stage in order to hear me play!").queue();
            return;                                                                                                      //copy and paste skip command
        }
        final Guild guild = ctx.getGuild();    //create a Guild variable
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild); //create guildmanager variable
        musicManager.scheduler.repeating = false;     //use repeating boolean and set to false
        musicManager.scheduler.queue.clear(); //bot clears queue
        musicManager.audioPlayer.stopTrack(); //bot stops current track
        final AudioManager audioManager = ctx.getGuild().getAudioManager();//create audio manager variable
        audioManager.closeAudioConnection(); //closes bot audio connection
        channel.sendMessage("Alright! Ill get off the stage!").queue(); //bot sends message when command is executed
    }
    @Override
    public String getName() {
        return "leave";
    }
    @Override
    public String getHelp() {
        return "Gets me off the stage.";
    }
}
