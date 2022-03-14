//Vincent Banks
//Commandontextt class
//Copyright Vincent Banks
package ThreeStrings.command;
import me.duncte123.botcommons.commands.ICommandContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.List;
public class CommandContext implements ICommandContext {
    private final GuildMessageReceivedEvent event;
    private final List<String> args;
    public CommandContext(GuildMessageReceivedEvent event, List<String> args) {
        this.args = args;
        this.event = event;
    }
    @Override
    public Guild getGuild(){
        return this.getEvent().getGuild();
    }
    @Override
    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }
    public List<String> getArgs() {
        return this.args;
    }
}
