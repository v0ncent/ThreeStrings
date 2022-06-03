//Vincent Banks
//ProfileCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.command.commands;
import ThreeStrings.Config;
import ThreeStrings.ExtendedMethods.MemberMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
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
            if(name.getNickname() == null){ // if user does not have a nickname
                embed.addField("**Nickname**", name.getEffectiveName(),true);
            } else {
                embed.addField("**Nickname**", name.getNickname(),true);
            }
            embed.addField("**Game Ranking**", "Ranking goes here", true);
            embed.addField("**Cash**",member.getCash(name.getIdLong(),true).toString(),true);
            if(member.getGoldStars(name.getIdLong()).equals(0)){
                embed.addField("**GoldStars**","Looks like you havnt found an easter egg yet. Get to huntin!" + "<:goldstar:981776534182449202>",true);
            } else {
                embed.addField("**GoldStars**",member.getGoldStars(name.getIdLong()).toString() + "<:goldstar:981776534182449202>",true);
            }
            embed.addField("**GoldStars**",member.getGoldStars(name.getIdLong()).toString() + "<:goldstar:981776534182449202>",true);
            embed.addField("**Room**", member.getRoomAsString(name.getIdLong()), false);
            embed.setFooter("Quite the reputation around here I see!");
            channel.sendMessageEmbeds(embed.build()).queue();
        } // if no mention
        catch(IndexOutOfBoundsException e){
            Member name = ctx.getMember();
            //create embed
            embed.setThumbnail(ctx.getMessage().getAuthor().getAvatarUrl());
            embed.setTitle("Profile for " + ctx.getMessage().getAuthor().getName());
            embed.setColor(name.getColor());
            if(name.getNickname() == null){ // if user does not have a nickname
                embed.addField("**Nickname**", ctx.getAuthor().getName(),true);
            } else {
                embed.addField("**Nickname**", name.getNickname(),true);
            }
            embed.addField("**Game Ranking**", "Ranking goes here", true);
            embed.addField("**Cash**",member.getCash(name.getIdLong(),true).toString(),true);
            if(member.getGoldStars(name.getIdLong()).equals(0)){
                embed.addField("**GoldStars**","Looks like you havnt found an easter egg yet. Get to huntin!" + Config.get("GOLD_STAR"),true);
            } else {
                embed.addField("**GoldStars**",member.getGoldStars(name.getIdLong()).toString() + Config.get("GOLD_STAR"),true);
            }
            embed.addField("**Room**", member.getRoomAsString(name.getIdLong()), false);
            embed.setFooter("Quite the reputation around here I see!");
            channel.sendMessageEmbeds(embed.build()).queue();
        } //this will run if user is bot
        catch (IllegalArgumentException e){
            e.printStackTrace();
            channel.sendMessage("The Tavern staff can afford better housing so nothing here my friend.").queue();
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

    @Override
    public String getType() {
        return "member";
    }
}
