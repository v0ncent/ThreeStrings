//Vincent Banks
//HelpCommand Class
//COPYRIGHT Vincent Banks
package ThreeStrings.command.commands;
import ThreeStrings.CommandManager;
import ThreeStrings.Config;
import ThreeStrings.ExtendedMethods.ArrayMethods;
import ThreeStrings.command.CommandContext;
import ThreeStrings.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is an implementation of Icommand Interface
 * @see ICommand
 * */
public final class HelpCommand implements ICommand {
   private final CommandManager manager; //import command manager class
    /**
     * Constructor for HelpCommand creates an instance of CommandManager
     * @param manager an instance of the CommandManager class, CommandManager is responsible for handling all commands when called and is needed for
     *                getting all ICommand objects.
     * @see CommandManager
     */
    public HelpCommand(CommandManager manager) { //implement constructor for help command
        this.manager = manager; //set manager variable to our already made manager within constructor
    }
    /*
     * Handle in this instance creates multiple variables, args is equal to ctx.getArgs() which is all information typed after a command is issued,
     * see CommandContext for more info on getArgs().
     * -
     * Then is creates the commandList which uses .getCommandList() in CommandManager class.
     * See CommandManager for more info on getCommandList()
     * -
     * Then it creates multiple lists of strings which are ArrayLists() and are named to be specific to their content of their command type names.
     * -
     * Once lists were made, it creates a variable called channel which is equal to ctx.getChannel(), this returns the channel
     * that the command was issued in.
     * -
     * Another variable called embedBuilder is created which is an instantiation of the EmbedBuilder class for it is an object used to crete discord embeds.
     * -
     * Then it checks if args is empty using isEmpty(), if there is no arguments given (meaning no command name after !help is issued)
     * it begins looping through the commandList and streaming it to anyMatch, if ICommand.getType() equals a string which is defined by the
     * different types of commands it adds them to their designated list of the same name and add the bots prefix to the front and a new line to the back.
     * Then it uses the embedBuilder to begin creation of the embed(title,side color, description,thumbnail) to be sent if no specified command is in args.
     * Once basics of embed are made it uses the method .addField() in the EmbedBuilder class, we create multiple fields all equal to a toString() version
     * of each type list with their designated commands in each. 
     * See EmbedBuilder for more info on .addField()
     * Then it sets the footer of embed and sends the embed to the designated channel, then returns; to conclude usage of class.
     * -
     * if there are arguments when command is issued, we set them to a string called search and take the first argument in the args list.
     * Then it creates a variable called command which is an ICommand command object, it then uses CommandManager method getCommand() with search as its query
     * @see CommandManager for more info getCommand()
     * -
     * If command is null (meaning the command does not exist) we send a message to the user channel stating the command doest exist and return to finish class usage,
     * else it sends the command query to the channel command is issued in with ICommands getHelp() method
     * @see ICommand for more info on getHelp()
     */
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();    //create a list of strings called args, then gets arguments
        List<ICommand> commandList = manager.getCommandList();
        List<String> miscComs = new ArrayList<>();
        List<String> utilityComs = new ArrayList<>();
        List<String> memberComs = new ArrayList<>();
        List<String> musicComs = new ArrayList<>();
        List<String> roomsComs = new ArrayList<>();
        List<String> shopComs = new ArrayList<>();
        TextChannel channel = ctx.getChannel(); //implement text channel class and create a new channel variable that then gets channel
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if(args.isEmpty()){  //if there is no command with !help if statement runs
            for (ICommand iCommand : commandList) {
                if (iCommand.getType().equalsIgnoreCase("misc")) {
                    miscComs.add(Config.get("PREFIX") + iCommand.getName()+"\n");
                }
                if (iCommand.getType().equalsIgnoreCase("utility")) {
                    utilityComs.add(Config.get("PREFIX") + iCommand.getName()+"\n");
                }
                if (iCommand.getType().equalsIgnoreCase("member")) {
                    memberComs.add(Config.get("PREFIX") + iCommand.getName()+"\n");
                }
                if (iCommand.getType().equalsIgnoreCase("music")) {
                    musicComs.add(Config.get("PREFIX") + iCommand.getName()+"\n");
                }
                if (iCommand.getType().equalsIgnoreCase("rooms")) {
                    roomsComs.add(Config.get("PREFIX") + iCommand.getName()+"\n");
                }
                if (iCommand.getType().equalsIgnoreCase("shop")) {
                    shopComs.add(Config.get("PREFIX") + iCommand.getName()+"\n");
                }
            }
            embedBuilder.setTitle("List 'o Commands");
            embedBuilder.setDescription("Hi, I'm Mattrim \"Threestrings\" Mereg.\n Here's a list of things I can do!\n **For more info on a command use !help (command name)**");
            embedBuilder.setColor(Color.orange);
            embedBuilder.setThumbnail(ctx
                    .getJDA()
                    .getSelfUser()
                    .getAvatarUrl());
            embedBuilder.addField("Misc","```"
                    + ArrayMethods.arrayAsString(miscComs)
                            + "```"
                    ,true);
            embedBuilder.addField("Utility","```"
                    +ArrayMethods.arrayAsString(utilityComs)
                            + "```"
                    ,true);
            embedBuilder.addField("Member","```"
                    +ArrayMethods.arrayAsString(memberComs)
                            + "```"
                    ,true);
            embedBuilder.addField("Music","```"
                    +ArrayMethods.arrayAsString(musicComs)
                            + "```"
                    ,false);
            embedBuilder.addField("Rooms","```"
                    +ArrayMethods.arrayAsString(roomsComs)
                            + "```"
                    ,true);
            embedBuilder.addField("Shop","```"
                    +ArrayMethods.arrayAsString(shopComs)
                            + "```"
                    ,true);
            embedBuilder.setFooter("Hi I'm Mattrim \"Threestrings\" Mereg, " +
                    "I'm a journeyman instructor at the New College of Olamn and a resident performer at the Yawning Portal Inn." +
                    " \"Anyone can play 'The Three Flambinis' on a lute, but try playing it with just three strings!\"");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }
        String search = args.get(0);  //set search string for if command does not exist
        ICommand command = manager.getCommand(search); //implement Icommand class with variable command, calls for manager and command search
        if (command == null){ //if there is no existing command if statement runs
            channel.sendMessage("Sorry I've got nothing for " + search).queue(); //bot sends out no command exists message
            return;
        }
        channel.sendMessage(command.getHelp()).queue(); //!help command is sent out
    }
    /*
     * getName() in this specific instance ->
     * @return "help" returns string "help" for command name
     */
    @Override
    public String getName() {
        return "help"; //set command to help within discord
    }
    /*
    * getHelp() in this specific instance ->
    * @return "Shows the list with what I can do\n" + "Usage: !help (command)" returns string value of @return for its help description
    */
    @Override
    public String getHelp() { //when !help, help is called this is run
        return "Shows the list with what I can do\n" + "Usage: !help (command)";
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
    * getAlisases() in this specific instance ->
    * @return List<String> of different ways to invoke command
    */
    @Override
    public List<String> getAlisases() {  //set secondary help command called !bard
        return List.of("bard","cmds","commandlist");
    }
}
