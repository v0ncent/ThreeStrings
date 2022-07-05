//Vincent Banks
//PingCommand Class
//Copyright Vincent Banks
package ThreeStrings.command.commands;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.JDA;
/**
 * This class is an implementation of Icommand Interface
 * @see ICommand
 * */
public final class PingCommand implements ICommand {
    /*
    * Handle in this instance creates a JDA object variable called JDA which is equal to ctx.getJDA().
    * getJDA() is an implemented method from BotCommon's ICommandContext interface. (Thanks @Duncte123)
    * @see ICommandContext for more info on getJDA()
    * then using jda variable, we then use a method called getRestPing() from .getJDA(), we then queue a lambda expression
    * to get channel that command is issued in and use a discord TextChannel method called sendMessageFormat() to send a formatted
    * message of ping, and bot gateway ping
    * @see ICommandContext for more info on getChannel()
    * @see TextChannel for more info on sendMessageFormat()
    */
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();
        jda.getRestPing().queue(
                (ping) -> ctx.getChannel() //gets rest ping and sends it to designated channel command is executed in
                        .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()); //gets gateway ping and sends it to channel command is executed in
    }
    /*
     * getHelp() in this specific instance ->
     * @return "Shows the current ping from the bot to the discord server." returns string value of @return for its help description
     */
    @Override
    public String getHelp() {
        return "Shows the current ping from the bot to the discord server.";
    }
    /*
     * getType() in this specific instance ->
     * @return "utility" returns string value of @return and sets its command type
     */
    @Override
    public String getType() {
        return "utility";
    }
    /*
     * getName() in this specific instance ->
     * @return ping" returns string "ping" for command name
     */
    @Override
    public String getName() {
        return "ping";
    } //returns ping command and sets the actual "ping"  name of command
}
