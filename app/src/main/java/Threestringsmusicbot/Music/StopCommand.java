//Vincnet Banks
//StopCommand Class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot.Music;

import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import Threestringsmusicbot.lavaplayer.GuildMusicManager;
import Threestringsmusicbot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
public class StopCommand implements ICommand {
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
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be near the stage in order to hear me play!").queue();
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();
        channel.sendMessage("Alright Ill shut up! No more upcoming songs too!").queue();
    }
    @Override
    public String getName() {
        return "stop";
    }
    @Override
    public String getHelp() {
        return "Stops me from playing the current song and clears my queue";
    }
}