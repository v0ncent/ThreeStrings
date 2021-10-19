//Vincent Banks
//PingCommand Class
//Copyright Vincent Banks
package Threestringsmusicbot.command.commands;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import net.dv8tion.jda.api.JDA;
public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();
        jda.getRestPing().queue(
                (ping) -> ctx.getChannel() //gets rest ping and sends it to designated channel command is executed in
                        .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()); //gets gateway ping and sends it to channel command is executed in
    }
    @Override
    public String getHelp() {
        return "Shows the current ping from the bot to the discord server";
    }
    @Override
    public String getName() {
        return "ping";
    } //returns ping command and sets the actual "ping"  name of command
}
