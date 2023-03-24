//Vincent Banks
//CMDPlay class
//COPYRIGHT Vincent Banks
package ThreeStrings.CommandLine.Commands;
import ThreeStrings.CommandLine.Codes;
import ThreeStrings.CommandLine.CommandLineManager;
import ThreeStrings.CommandLine.Exceptions;
import ThreeStrings.Listener;
import ThreeStrings.lavaplayer.PlayerManager;
import com.mongodb.lang.NonNull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import java.util.List;
import java.util.Scanner;
public final class CMDPlay {
   private static final List<Guild> guilds = Listener.GUILDS;
   //
    private static Guild getGuildChoice(int choice) throws Exceptions.InvalidMenuChoice, Exceptions.ExitCodeCalled {
        if(choice == Codes.EXIT_CODE){
            throw new Exceptions.ExitCodeCalled(CMDPlay.class);
        }
        if(choice < 0 || choice > guilds.size()){
            throw new Exceptions.InvalidMenuChoice(CMDPlay.class);
        }
        return guilds.get(choice);
    }
    private static VoiceChannel getChannelChoice(int choice, Guild server) throws Exceptions.ExitCodeCalled, Exceptions.InvalidMenuChoice {
        if(choice == Codes.EXIT_CODE){
            throw new Exceptions.ExitCodeCalled(CMDPlay.class);
        }
        if(choice < 0 || choice > server.getVoiceChannels().size()){
            throw new Exceptions.InvalidMenuChoice(CMDPlay.class);
        }
        return server.getVoiceChannels().get(choice);
    }
    private static TextChannel getTextChannelChoice(int choice, Guild server) throws Exceptions.ExitCodeCalled, Exceptions.InvalidMenuChoice {
        if(choice == Codes.EXIT_CODE){
            throw new Exceptions.ExitCodeCalled(CMDPlay.class);
        }
        if(choice < 0 || choice > server.getTextChannels().size()){
            throw new Exceptions.InvalidMenuChoice(CMDPlay.class);
        }
        return server.getTextChannels().get(choice);
    }
    //
    public static void play(@NonNull String link) throws Exceptions.InvalidMenuChoice, Exceptions.ExitCodeCalled {
        Scanner scnr = new Scanner(System.in);
        //
        //determine server endpoint
        CommandLineManager.printMenu(guilds);
        System.out.println("What Guild would you like to play to?");
        int choice = scnr.nextInt();
        Guild server = getGuildChoice(choice);
        //determine channel endpoint
        CommandLineManager.printMenu(server.getVoiceChannels());
        System.out.println("What channel would you like to play to?");
        choice = scnr.nextInt();
        VoiceChannel channel = getChannelChoice(choice,server);
        //determine text channel endpoint
        CommandLineManager.printMenu(server.getTextChannels());
        System.out.println("What text channel would you like to send play message to?");
        choice = scnr.nextInt();
        TextChannel textChannel = getTextChannelChoice(choice,server);
        //connect to endpoints and play link
        final AudioManager audioManager = server.getAudioManager();
        audioManager.openAudioConnection(channel);
        PlayerManager.getInstance().LoadAndPlay(textChannel, link,true);
        System.out.printf("Playing %s to %s",link,server);
    }
}
