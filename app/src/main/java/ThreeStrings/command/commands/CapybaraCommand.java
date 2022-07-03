//Vincent Banks
//CapybaraCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.command.commands;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
/**
 * This class is an implementation of ICommand Interface
 * @see ICommand
 * */
public final class CapybaraCommand implements ICommand {
    /*
     * Handle in this instance creates a variable called channel which is the channel is equal to the
     * channel in which the command was issued in, it then uses channel.sendMessage() which requires a message
     * String to send to the specified channel, here it simply sends a {@link} of information about the Capybara
     */
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        channel.sendMessage("https://www.rainforest-alliance.org/species/capybara/").queue(); //sends message containing capybara link
    }
    /*
     * getName() in this specific instance ->
     * @return "capybara" returns command name of capybara
     */
    @Override
    public String getName() {
        return "capybara"; //set command to capybara within discord
    }
    /*
     * getHelp() in this specific instance ->
     * @return "What? What is this?"
     */
    @Override
    public String getHelp() {
        return "What? What is this?"; //when help command is executed on command bot replies
    }
    /*
     * getType() in this instance ->
     * @return "misc"
     */
    @Override
    public String getType() {
        return "misc";
    }
}
