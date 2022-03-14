//Vincent Banks
//GuildMusicManager Class
//COPYRIGHT Vincent Banks
package ThreeStrings.lavaplayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
public class GuildMusicManager {
    public final AudioPlayer audioPlayer; //create audio player variable
    public final TrackScheduler scheduler; //create new variable with out Trackscheduler class
    private final AudioPlayerSendHandler sendHandler;
    public GuildMusicManager(AudioPlayerManager manager){ //create audio manager constructor that creates or audio manager
        this.audioPlayer = manager.createPlayer(); //create audio player
        this.scheduler = new TrackScheduler(this.audioPlayer); //implement our trackScheduler class and give it the arguments to our audioPlayer
        this.audioPlayer.addListener(this.scheduler); //add a listener to our audio player giving it args of scheduler
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer); //create a new send handler class with the args of the audioplayer
    }
    public AudioPlayerSendHandler getSendHandler(){ //getter for sendHandler
        return sendHandler;
    }
}
