//Vincent Banks
//LeaveCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Music;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import ThreeStrings.lavaplayer.GuildMusicManager;
import ThreeStrings.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;
@SuppressWarnings("ConstantConditions")
public final class LeaveCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be on stage so I can play.").queue();
            return;
        }
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("What do you mean? You're not even in the tavern!").queue();
            return;
        }
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;                                                                                                      //copy and paste skip command
        }
        final Guild guild = ctx.getGuild();    //create a Guild variable
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild); //create guild manager variable
        musicManager.scheduler.repeating = false;     //use repeating boolean and set to false
        musicManager.scheduler.queue.clear(); //bot clears queue
        musicManager.audioPlayer.stopTrack(); //bot stops current track
        final AudioManager audioManager = ctx.getGuild().getAudioManager();//create audio manager variable
        audioManager.closeAudioConnection(); //closes bot audio connection
        channel.sendMessage("Don't have to tell me twice. I'm off for the night.").queue(); //bot sends message when command is executed
    }
    @Override
    public String getName() {
        return "leave";
    }
    @Override
    public String getHelp() {
        return "Gets me to leave the current voice channel.";
    }
    @Override
    public String getType() {
        return "music";
    }
}
