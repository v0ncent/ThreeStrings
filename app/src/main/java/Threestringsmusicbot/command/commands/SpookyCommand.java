//Vincent Banks
//Spooky Command Class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot.command.commands;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import Threestringsmusicbot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
@SuppressWarnings("ConstantConditions")
public class SpookyCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {   //simple command for halloween
        final TextChannel channel = ctx.getChannel(); //import over simple play command
            final Member self = ctx.getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();
            if (!selfVoiceState.inVoiceChannel()) {
                channel.sendMessage("I need to be on a stage in order for me to play!").queue();
                return;
            }
            final Member member = ctx.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();
            if (!memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("You need to be in the same voice channel as me in order to hear me play!").queue();
                return;
            }
            if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
                channel.sendMessage("You need to be near the stage in order to hear me play!").queue();
                return;
            }
            PlayerManager.getInstance().LoadAndPlayOnce(channel, "https://www.youtube.com/watch?v=K2rwxs1gH9w"); //spooky!
            TextChannel channel1 = ctx.getChannel(); //sends message to voice
            channel1.sendMessage("boo!").queue();
        }
    @Override
    public String getName() {
        return "spooky";
    }
    @Override
    public String getHelp() {
        return "Scary " + "<:Spooked:890460639657607188>";
    }
}
