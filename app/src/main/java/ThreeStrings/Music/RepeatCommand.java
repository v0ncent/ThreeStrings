//Vincent Banks
//Repeat Command Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Music;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import ThreeStrings.lavaplayer.GuildMusicManager;
import ThreeStrings.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
@SuppressWarnings("ConstantConditions")
public final class RepeatCommand implements ICommand {
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
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean newRepeating = !musicManager.scheduler.repeating;//copy and paste from skip command  //invert the repeating state boolean and assign to variable
        musicManager.scheduler.repeating = newRepeating; //set var to repeat in scheduler
        channel.sendMessageFormat("**%s**", newRepeating ? "Alright, I'll keep playing that song." : "Okay, no more of that song.").queue(); //if repeating is true repeating false not repeating
    }
    @Override
    public String getName() {
        return "repeat";
    }
    @Override
    public String getHelp() {
        return "Sets me to repeating mode. To stop me repeating just simply type command again.";
    }

    @Override
    public String getType() {
        return "music";
    }
}
