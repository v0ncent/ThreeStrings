//Vincent Banks
//PingCommand Class
//Copyright Vincent Banks
package ThreeStrings.Bot.command.commands;

import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import net.dv8tion.jda.api.JDA;

public final class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        //gets rest ping
        jda.getRestPing().queue(
                (ping) -> ctx.getChannel()
                        .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue());
    }

    @Override
    public String getHelp() {
        return "Shows the current ping from the bot to the discord server.";
    }

    @Override
    public String getType() {
        return "utility";
    }

    @Override
    public String getName() {
        return "ping";
    }
}
