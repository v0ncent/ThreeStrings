//Vincent Banks
//JoinCommand class
//Copyright Vincent Banks
package ThreeStrings.Bot.Music;

import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

@SuppressWarnings("ConstantConditions")
public final class JoinCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState checkVoiceState = self.getVoiceState();

        if (checkVoiceState.inAudioChannel()){
            channel.sendMessage("Im already playing over here for the night.").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            channel.sendMessage("What do you mean? You're not even in the tavern!").queue();
            return;
        }

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        final VoiceChannel userChannel = memberVoiceState.getChannel().asVoiceChannel();

        audioManager.openAudioConnection(userChannel);
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
