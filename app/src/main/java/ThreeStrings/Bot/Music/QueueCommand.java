//Vincent Banks
//QueueCommand class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot.Music;

import ThreeStrings.Bot.ExtendedMethods.ArrayMethods;
import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import ThreeStrings.Bot.lavaplayer.GuildMusicManager;
import ThreeStrings.Bot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
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

        ArrayList<String> queueList = new ArrayList<>();

        for (int i = 0; i < trackCount; i++) {
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            queueList.add(String.format("%d.  `%s` by `%s` - `%s`  \n",i+1, info.title, info.author, formatTime(track.getDuration() ) ) );

        }

        if (trackList.size() > trackCount) { //if track list size is bigger than 20
            queueList.add(String.format("and `%s` more...",trackList.size() - trackCount));
        }

        channel.sendMessageFormat("**Coming up:**\n%s", ArrayMethods.arrayAsString(queueList)).queue();
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