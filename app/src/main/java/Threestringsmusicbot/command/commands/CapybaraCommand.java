//Vincent Banks
//CapybaraCommand Class
//COPYRIGHT Vincent Banks
package Threestringsmusicbot.command.commands;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
public class CapybaraCommand implements ICommand {
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
}
