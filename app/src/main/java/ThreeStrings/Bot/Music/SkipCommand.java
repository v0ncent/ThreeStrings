//Vincent Banks
//SkipCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot.Music;

import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import ThreeStrings.Bot.lavaplayer.GuildMusicManager;
import ThreeStrings.Bot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public final class SkipCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
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

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null){  //if no track is playing
            channel.sendMessage("Stop booing! Im not playing even anything!").queue();
            return;
        }

        musicManager.scheduler.nextTrack();
        channel.sendMessage("I hear ya, movin' on.").queue();
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "Skips the current song im playing.";
    }

    @Override
    public String getType() {
        return "music";
    }
}
