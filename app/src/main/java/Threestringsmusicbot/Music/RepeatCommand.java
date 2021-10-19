//Vincent Banks
//Repeat Command Class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot.Music;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import Threestringsmusicbot.lavaplayer.GuildMusicManager;
import Threestringsmusicbot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
@SuppressWarnings("ConstantConditions")
public class RepeatCommand implements ICommand {
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
        final boolean newRepeating = !musicManager.scheduler.repeating;//copy and paste from skip command  //invert the repeating state boolean and assign to variable
        musicManager.scheduler.repeating = newRepeating; //set var to repeat in scheduler
        channel.sendMessageFormat("Encore! we are **%s**", newRepeating ? "repeating" : "not repeating").queue(); //if repeating is true repeating false not repeating
    }
    @Override
    public String getName() {
        return "repeat";
    }
    @Override
    public String getHelp() {
        return "Encore! Sets me to repeating mode! To stop me repeating just simply type command again!";
    }
}
