//Vincent Banks
//Command context class
//Copyright Vincent Banks
package ThreeStrings.Bot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.sharding.ShardManager;
import java.util.List;

public class CommandContext {
    private final MessageReceivedEvent event;
    private final List<String> args;

    public CommandContext(MessageReceivedEvent event, List<String> args) {
        this.args = args;
        this.event = event;
    }

    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    public MessageReceivedEvent getEvent() {
        return this.event;
    }

    public JDA getJDA() {
        return this.event.getJDA();
    }

    public List<String> getArgs() {
        return this.args;
    }

    public TextChannel getChannel() {
        return this.getEvent().getChannel().asTextChannel();
    }

    public Message getMessage() {
        return this.getEvent().getMessage();
    }

    public User getAuthor() {
        return this.getEvent().getAuthor();
    }

    public Member getMember() {
        return this.getEvent().getMember();
    }

    public ShardManager getShardManager() {
        return this.getJDA().getShardManager();
    }

    public User getSelfUser() {
        return this.getJDA().getSelfUser();
    }

    public Member getSelfMember() {
        return this.getGuild().getSelfMember();
    }
}
