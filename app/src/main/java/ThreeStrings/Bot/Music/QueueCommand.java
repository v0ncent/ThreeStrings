//Vincent Banks
//QueueCommand class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot.Music;

import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import ThreeStrings.Bot.lavaplayer.GuildMusicManager;
import ThreeStrings.Bot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public final class QueueCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if (queue.isEmpty()) { //if there is nothing playing
            channel.sendMessage("Hey im on break, there is nothing coming up.").queue();
            return;
        }

        final int trackCount = Math.min(queue.size(), 20); //takes the smallest number of tracks and sets them as the track count variable (max 20)
        final List<AudioTrack> trackList = new ArrayList<>(queue);

        final MessageCreateAction messageAction = channel.sendMessage(("**Coming up:**\n"));

        for (int i = 0; i < trackCount; i++) {
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            messageAction.applyData(MessageCreateData.fromContent("#"))
                    .applyData(MessageCreateData.fromContent(String.valueOf(i + 1)))
                    .applyData(MessageCreateData.fromContent(" `"))
                    .applyData(MessageCreateData.fromContent(info.title))
                    .applyData(MessageCreateData.fromContent(" by "))
                    .applyData(MessageCreateData.fromContent(info.author))
                    .applyData(MessageCreateData.fromContent("` [`"))
                    .applyData(MessageCreateData.fromContent(formatTime(track.getDuration())))
                    .applyData(MessageCreateData.fromContent("`]\n"));

        }

        if (trackList.size() > trackCount) { //if track list size is bigger than 20
            messageAction.applyData(MessageCreateData.fromContent("And `"))
                    .applyData(MessageCreateData.fromContent(String.valueOf(trackList.size() - trackCount)))
                    .applyData(MessageCreateData.fromContent("` more..."));
        }

        messageAction.queue(); //sends messageAction based upon conditions
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1)/ TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp() {
        return "Shows what is playing next.";
    }

    @Override
    public String getType() {
        return "music";
    }
}