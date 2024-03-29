//Vincent Banks
//PlaylistCommand Class
//Copyright Vincent Banks
package ThreeStrings.Bot.Music;

import ThreeStrings.Bot.Config;
import ThreeStrings.Bot.ExtendedMethods.MemberMethods;
import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import ThreeStrings.Bot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

@SuppressWarnings({"ConstantConditions", "DuplicatedCode"})
public final class PlaylistCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        if (ctx.getArgs().isEmpty()) { //if only !play is sent
            channel.sendMessage("Correct usage is `!play (youtube link)`").queue();
            return;
        }

        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) { //if bot is not in vc
            channel.sendMessage("I need to be on stage so I can play.").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) { //if member is not in vc
            channel.sendMessage("What do you mean? You're not even in the tavern!").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) { //if member is not in same voice channel as bot
            channel.sendMessage("You've got to be by the stage so you can hear me play!").queue();
            return;
        }

        Random r = new Random();
        int rngEgg = r.nextInt(251-1) + 1;

        if(rngEgg == 57){
            String link = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";

            final AudioManager audioManager = ctx.getGuild().getAudioManager();
            final VoiceChannel userChannel = memberVoiceState.getChannel().asVoiceChannel();

            audioManager.openAudioConnection(userChannel);
            PlayerManager.getInstance().LoadAndPlay(channel, link,true);

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

        String link = String.join(" ", ctx.getArgs());

        if (!isUrl(link)) { //if there is no link lavaplayer will search YouTube for link and play only the top result
            link = "ytsearch:" + link;

            final AudioManager audioManager = ctx.getGuild().getAudioManager();
            final VoiceChannel userChannel = memberVoiceState.getChannel().asVoiceChannel();

            audioManager.openAudioConnection(userChannel);
            PlayerManager.getInstance().LoadAndPlay(channel, link,false);

        } else {
            final AudioManager audioManager = ctx.getGuild().getAudioManager();
            final VoiceChannel userChannel = memberVoiceState.getChannel().asVoiceChannel();

            audioManager.openAudioConnection(userChannel);
            PlayerManager.getInstance().LoadAndPlay(channel, link,false);
        }

    }

    @Override
    public String getName() {
        return "playlist";
    }

    @Override
    public String getHelp() {
        return "Gets me to make and play a playlist for you based off of your search.\n" + "Usage: !playlist (search term)." +
                "\nTo play a playlist from youtube use !playlist (playlist link).\n";
    }

    @Override
    public String getType() {
        return "music";
    }

    private boolean isUrl(String url){
        try{
            new URL(url);
            return true;
        } catch (FriendlyException | MalformedURLException e){
            return false;
        }
    }

}

