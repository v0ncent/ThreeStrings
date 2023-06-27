//Vincent Banks
//CapybaraCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.Bot.command.commands;

import ThreeStrings.Bot.command.CommandContext;
import ThreeStrings.Bot.command.ICommand;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public final class CapybaraCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        channel.sendMessage("https://www.rainforest-alliance.org/species/capybara/").queue(); //sends message containing capybara link
    }

    @Override
    public String getName() {
        return "capybara";
    }

    @Override
    public String getHelp() {
        return "What? What is this?";
    }

    @Override
    public String getType() {
        return "misc";
    }
}
