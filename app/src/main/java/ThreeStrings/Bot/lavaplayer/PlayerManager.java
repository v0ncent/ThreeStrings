//Vincent Banks, Remy Amel
//PlayerManager Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.container.MediaContainerRegistry;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    public final Map<Long, GuildMusicManager> musicManagers;
    public final AudioPlayerManager audioPlayerManager;
    private final Logger LOGGER = LoggerFactory.getLogger(PlayerManager.class);

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        GuildMusicManager.registerRemoteSources(this.audioPlayerManager, MediaContainerRegistry.DEFAULT_REGISTRY);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {   // tells JDA what to use when sending audio
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        }
        );
    }

    public void LoadAndPlay(TextChannel channel, String trackUrl, boolean isOnce) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        // ANON INTERFACE INCOMINGGGGG !!!!!!!!!!!!!!!!!!!!
        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() { // <- start -
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track); // load track into queue or play it if none is currently playing

                channel.sendMessage("Adding to queue: `" + track.getInfo().title +
                                 "` by `" +
                                track.getInfo().author)
                        .queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();

                if (isOnce){ // true if only playing one song, else make playlist from search
                    final AudioTrack track = tracks.get(0); // takes the first result

                    musicManager.scheduler.queue(track);

                    channel.sendMessage("Adding to queue: `" + track.getInfo().title +
                                    "` by `" +
                                    track.getInfo().author +
                                    "`")
                            .queue();

                    final AudioTrackInfo info = track.getInfo();
                    if (musicManager.scheduler.queue.isEmpty()){ // if there is nothing in queue
                        channel.sendMessageFormat("Now playing: `%s` by `%s`" + " - Duration: " + formatTime(track.getDuration()) +
                                " (Link: <%s>)", info.title, info.author, info.uri).queue();
                    }

                } else {
                    channel.sendMessage("Adding to queue: `" + tracks.size() +
                                    "` tracks from playlist `" +
                                    playlist.getName())
                            .queue();

                    for (final AudioTrack track : tracks){
                        musicManager.scheduler.queue(track);
                        final AudioTrackInfo info = track.getInfo();

                        if (musicManager.scheduler.queue.isEmpty()){ // if there is nothing in queue
                            channel.sendMessageFormat("Now playing: `%s` by `%s`" + " - Duration: " + formatTime(track.getDuration()) +
                                    " Link: <%s>)", info.title, info.author, info.uri).queue();
                        }

                    }

                }
            }

            private String formatTime(long timeInMillis) { //create formatTime method
                final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
                final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
                final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1)/ TimeUnit.SECONDS.toMillis(1);

                return String.format("%02d:%02d:%02d", hours, minutes, seconds);
            }

            @Override
            public void noMatches() {
                LOGGER.error("No matches for link");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                LOGGER.error("Failed to load song!\n" + exception);
            }

        }
        ); // <- end (holy fuck) -

    }

    public static PlayerManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

}
