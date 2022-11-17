package ThreeStrings.CommandLine.Commands;
import ThreeStrings.Listener;
import ThreeStrings.lavaplayer.PlayerManager;
import com.mongodb.lang.NonNull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import java.util.List;
import java.util.Scanner;
public final class CMDPlay {
   private static final List<Guild> guilds = Listener.GUILDS;
   //
    private static Guild getGuildChoice(int choice){
        if(choice < 0 || choice > guilds.size()){
            throw new IndexOutOfBoundsException();
        }
        return guilds.get(choice);
    }
    private static VoiceChannel getChannelChoice(int choice,Guild server){
        if(choice < 0 || choice > server.getVoiceChannels().size()){
            throw new IndexOutOfBoundsException();
        }
        return server.getVoiceChannels().get(choice);
    }
    private static TextChannel getTextChannelChoice(int choice,Guild server){
        if(choice < 0 || choice > server.getTextChannels().size()){
            throw new IndexOutOfBoundsException();
        }
        return server.getTextChannels().get(choice);
    }
    //
    public static void play(@NonNull String link){
        Scanner scnr = new Scanner(System.in);
        //
        //determine server endpoint
        System.out.println("------------");
        for(int i =0; i<guilds.size(); i++){
            System.out.println(i + " " + guilds.get(i).toString());
        }
        System.out.println("------------");
        System.out.println("What Guild would you like to play to?");
        int choice = scnr.nextInt();
        Guild server = getGuildChoice(choice);
        //determine channel endpoint
        System.out.println("------------");
        for(int i = 0; i< server.getVoiceChannels().size(); i++){
            System.out.println(i + " " + server.getVoiceChannels().get(i).toString());
        }
        System.out.println("------------");
        System.out.println("What channel would you like to play to?");
        choice = scnr.nextInt();
        VoiceChannel channel = getChannelChoice(choice,server);
        //determine text channel endpoint
        System.out.println("------------");
        for(int i = 0; i< server.getTextChannels().size(); i++){
            System.out.println(i + " " + server.getTextChannels().get(i).toString());
        }
        System.out.println("------------");
        System.out.println("What text channel would you like to send play message to?");
        choice = scnr.nextInt();
        TextChannel textChannel = getTextChannelChoice(choice,server);
        //connect to endpoints and play link
        final AudioManager audioManager = server.getAudioManager();
        audioManager.openAudioConnection(channel);
        PlayerManager.getInstance().LoadAndPlayOnce(textChannel, link);
    }
    //
}
