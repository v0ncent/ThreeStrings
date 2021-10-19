//Vincent Banks
//QueueCommand class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot.Music;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import Threestringsmusicbot.lavaplayer.GuildMusicManager;
import Threestringsmusicbot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
public class QueueCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();//get channel command is executed in through TextChannel variable
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild()); //get our music manager with our music manager variable
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue; //implement our queue system variable
        if (queue.isEmpty()) { //if there is nothing playing this if statement will run
            channel.sendMessage("There is nothing coming up.").queue();
            return;
        }
        final int trackCount = Math.min(queue.size(), 20); //takes the smallest number of tracks and sets them as the trackcount variable (max 20)
        final List<AudioTrack> trackList = new ArrayList<>(queue); //convert the queue into an array list
        final MessageAction messageAction = channel.sendMessage(("**Coming up:**\n")); //create messageAction variable that sends this message
        for (int i = 0; i < trackCount; i++) { //use for statement to itterate each track in queue and get info on the track
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();
            messageAction.append('#')
                    .append(String.valueOf(i + 1))
                    .append(" `")
                    .append(info.title)
                    .append(" by ")
                    .append(info.author)
                    .append("` [`")
                    .append(formatTime(track.getDuration())) //get current track time
                    .append("`]\n");
        }
        if (trackList.size() > trackCount) { //if tracklist size is bigger than 20
            messageAction.append("And `")
                    .append(String.valueOf(trackList.size() - trackCount))
                    .append("` more...");
        }
        messageAction.queue(); //sends messageAction based upon conditions
    }
    private String formatTime(long timeInMillis) { //create formatTime method
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
}