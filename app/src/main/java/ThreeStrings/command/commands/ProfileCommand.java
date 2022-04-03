//Vincent Banks
//ProfileCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.command.commands;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Objects;

public class ProfileCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        EmbedBuilder embed = new EmbedBuilder(); //implement EmbedBuilder
        MemberMethods member = new MemberMethods();
        try{
            Member name = ctx.getMessage().getMentionedMembers().get(0);
            //create embed
            embed.setThumbnail(name.getUser().getAvatarUrl());
            embed.setTitle("Profile for " + name.getUser().getName());
            embed.setColor(name.getColor());
            embed.addField("**Nickname**",name.getNickname(),true);
            embed.addField("**Game Ranking**", "Ranking goes here", true);
            embed.addField("**Room**", member.getRoom(name.getIdLong()), true);
            embed.setFooter("Quite the reputation around here I see!");
            channel.sendMessageEmbeds(embed.build()).queue();
        } // if no mention
        catch(IndexOutOfBoundsException e){
            Member name = ctx.getMember();
            //create embed
            embed.setThumbnail(ctx.getMessage().getAuthor().getAvatarUrl());
            embed.setTitle("Profile for " + ctx.getMessage().getAuthor().getName());
            embed.setColor(name.getColor());
            embed.addField("**Nickname**", name.getNickname(),true);
            embed.addField("**Game Ranking**", "Ranking goes here", true);
            embed.addField("**Room**", member.getRoom(name.getIdLong()), true);
            embed.setFooter("Quite the reputation around here I see!");
            channel.sendMessageEmbeds(embed.build()).queue();
        }
        catch (IllegalArgumentException e){
            channel.sendMessage("Sorry tavern staff dont get any stats.").queue();
        }
    }
    @Override
    public String getName() { //command name
        return "profile";
    }
    @Override
    public String getHelp() { // help description
        return "Shows you your tavern profile!";
    }
}
