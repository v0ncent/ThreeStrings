//Vincent Banks
//Play Command Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Music;
import ThreeStrings.Config;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import ThreeStrings.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public final class PlayCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel(); //implement variable to get channel
        if (ctx.getArgs().isEmpty()){ //if only !play is sent
            channel.sendMessage("Correct usage is `!play (youtube link)`").queue();
            return;
        }
        final Member self = ctx.getSelfMember(); //implement variable to get bot
        final GuildVoiceState selfVoiceState = self.getVoiceState(); //gets bot voice state
        if (!selfVoiceState.inVoiceChannel()) { //if bot is not in vc
           channel.sendMessage("I need to be on stage so I can play.").queue();
            return;
        }
        final Member member = ctx.getMember(); //create variable for getting user
        final GuildVoiceState memberVoiceState = member.getVoiceState(); //create variable for getting user voice state
        if (!memberVoiceState.inVoiceChannel()) { //if member is not in vc
            channel.sendMessage("What do you mean? You're not even in the tavern!").queue();
            return;
        }
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){ //if member is not in same voice channel as bot
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;
        }
        Random r = new Random();
        int rngEgg = r.nextInt(251-1) + 1;
        //int rngEgg = 1;
        if(rngEgg == 1){
            String link = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            final AudioManager audioManager = ctx.getGuild().getAudioManager();//implements audiomanager
            final VoiceChannel userChannel = memberVoiceState.getChannel();//checks what voice chat the member is in
            audioManager.openAudioConnection(userChannel); //bot connects to designated user channel
            PlayerManager.getInstance().LoadAndPlayOnce(channel, link); //sends bot to channel and plays song on instance
            channel.sendMessage("That's gotta suck!\n" +
                    ctx.getMessage().getAuthor().getAsMention() + " just got rick rolled!\n" +
                    "At least its not all bad you got a gold star!" + Config.get("GOLD_STAR") +"\n").queue();
            try {
                MemberMethods memberTool = new MemberMethods();
                memberTool.eggFound(ctx.getAuthor().getIdLong());
            } catch (Exception e){
                e.printStackTrace();
                channel.sendMessage("I tried to give you a star but seems there was an error! " + e).queue();
            }
        }
        String link = String.join(" ",ctx.getArgs()); //create link
        if (!isUrl(link)){ //if there is no link lavaplayer will search YouTube for link and play only the top result
            link = "ytsearch:" + link;
            final AudioManager audioManager = ctx.getGuild().getAudioManager();//implements audiomanager
            final VoiceChannel userChannel = memberVoiceState.getChannel();//checks what voice chat the member is in
            audioManager.openAudioConnection(userChannel); //bot connects to designated user channel
            PlayerManager.getInstance().LoadAndPlayOnce(channel, link); //sends bot to channel and plays song on instance
        }else { //else is a playlist
            final AudioManager audioManager = ctx.getGuild().getAudioManager();//implements audiomanager
            final VoiceChannel userChannel = memberVoiceState.getChannel();//checks what voice chat the member is in
            audioManager.openAudioConnection(userChannel); //bot connects to designated user channel
            PlayerManager.getInstance().LoadAndPlay(channel, link); //sends bot to channel and plays the playlist
        }
    }
        @Override
        public String getName () {
            return "play";
        }
        @Override
        public String getHelp () {
            return "Gets me to play a song for you!\n" +
                    "Usage: !play (youtube link) *or* Simply type the song name.\n"+
                    "To play a playlist from youtube use !play (playlist link).";
        }
    @Override
    public String getType() {
        return "music";
    }
    private boolean isUrl(String url){
            try{
                new URL(url); //attempts to create new URL
                return true; //succeeds and returns true
            } catch (FriendlyException | MalformedURLException e){ //if not return false and throw URI syntax exception
                return false;
            }
        }
    }