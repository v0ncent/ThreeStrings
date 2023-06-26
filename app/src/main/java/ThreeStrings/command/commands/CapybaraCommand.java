//Vincent Banks
//CapybaraCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.command.commands;

import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public final class CapybaraCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        channel.sendMessage("https://www.rainforest-alliance.org/species/capybara/").queue(); //sends message containing capybara link
    }

    @Override
    public String getName() {
        return "capybara"; //set command to capybara within discord
    }

    @Override
    public String getHelp() {
        return "What? What is this?"; //when help command is executed on command bot replies
    }

    @Override
    public String getType() {
        return "misc";
    }
}
