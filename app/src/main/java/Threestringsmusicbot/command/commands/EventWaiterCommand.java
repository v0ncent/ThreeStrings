//Vincent Banks
//EventWaiterCommmand Class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot.command.commands;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import java.util.concurrent.TimeUnit;
public class EventWaiterCommand implements ICommand {
    private static final String EMOTE = "✔"; //define string variable to green check
    private final EventWaiter waiter;
    public EventWaiterCommand(EventWaiter waiter){ //implement constructor for EventWaiter
        this.waiter = waiter;
    }
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        channel.sendMessage("React with ")
                .append(EMOTE)
                .queue((message -> {
                    message.addReaction(EMOTE).queue();
                    this.waiter.waitForEvent(
                            GuildMessageReactionAddEvent.class, //take in class
                            (e) -> e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot(), //do checks to see if event is to be handled
                            (e) -> {
                                channel.sendMessageFormat("%#s was the first to react", e.getUser()).queue();            //handle event
                            },
                            5L, TimeUnit.SECONDS,
                            () -> channel.sendMessage("Tsk Tsk took too long.").queue() //add timeout
                    );
                }));
    }
    @Override
    public String getName() {
        return "eventwaiter";
    }
    @Override
    public String getHelp() {
        return "an EventWaiter OwO";
    }
}
