//Vincent Banks
//PauseCommand Class
//Copyright Vincent Banks
package ThreeStrings.Bot.Music;

import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import ThreeStrings.Bot.lavaplayer.GuildMusicManager;
import ThreeStrings.Bot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@SuppressWarnings("ConstantConditions")
public final class PauseCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            channel.sendMessage("I need to be on stage so I can play.").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage("What do you mean? You're not even in the tavern!").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){ //if member is not in same voice channel as bot
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null){
            channel.sendMessage("Uhhh, Im not even playing anything.").queue();
            return;
        }

        if(audioPlayer.isPaused()){
            channel.sendMessage("I've already stopped.").queue();
        }else{
            audioPlayer.setPaused(true);
            channel.sendMessage("Alright I'll take a break.").queue();
        }

    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getHelp() {
        return "Pauses the current song I am playing.";
    }

    @Override
    public String getType() {
        return "music";
    }
}
