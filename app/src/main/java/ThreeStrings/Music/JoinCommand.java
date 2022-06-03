//Vincent Banks
//JoinCommand class
//Copyright Vincent Banks
package ThreeStrings.Music;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
@SuppressWarnings("ConstantConditions")
public class JoinCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel(); //create a text channel variable to get the text channel
        final Member self = ctx.getSelfMember(); //allows bot to get itself
        final GuildVoiceState checkVoiceState = self.getVoiceState(); //checks if bot is already in voice chat
        if (checkVoiceState.inVoiceChannel()){ //if bot is in voicechannel this if statement runs
            channel.sendMessage("Im already playing over here for the night.").queue(); //bot tells server that they are already in a voice channel
            return;
        }
        final Member member = ctx.getMember();   //checking if member is not in a voice channel
        final GuildVoiceState memberVoiceState = member.getVoiceState(); //checks if member is in a voice channel or not
        if(!memberVoiceState.inVoiceChannel()){      //invert boolean statement if member is not in voice channel this message will play
            channel.sendMessage("What do you mean? You're not even in the tavern!").queue();
            return;
        }
        final AudioManager audioManager = ctx.getGuild().getAudioManager();//implements audiomanager
        final VoiceChannel userChannel = memberVoiceState.getChannel();//checks what voice chat the member is in
        audioManager.openAudioConnection(userChannel); //bot connects to designated user channel
        }
    @Override
    public String getName() {
        return "join";
    }
    @Override
    public String getHelp() {
        return "Command to get me to join the chat you are currently in.";
    }

    @Override
    public String getType() {
        return "music";
    }
}
