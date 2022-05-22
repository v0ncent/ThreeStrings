//Vincent Banks
//AudioPlayerSendHandler class
//COPYRIGHT Vincent Banks
package ThreeStrings.lavaplayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import java.nio.ByteBuffer;
public class AudioPlayerSendHandler implements AudioSendHandler {
    private final AudioPlayer audioPlayer; //import audioplayer class
    private final ByteBuffer buffer; //import byte buffer class (holds bytes to send to discord)
    private final MutableAudioFrame frame; //this is what the audio is actually sent to
    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024); //lavaplayer will provide this many bytes every 20 millesonds
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }
    @Override
    public boolean canProvide() {
        return this.audioPlayer.provide(this.frame); //writes to our mutable frame which in return speaks to our buffer
    }
    @Override
    public ByteBuffer provide20MsAudio() {
        return this.buffer.flip(); //sets buffer to zero so jda can read from the start
    }
    @Override
    public boolean isOpus() {
        return true;
    }
}
