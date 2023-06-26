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
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class HelpCommand implements ICommand {
    private final CommandManager manager; //import command manager class

    public HelpCommand(CommandManager manager) { //implement constructor for help command
        this.manager = manager; //set manager variable to our already made manager within constructor
    }

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
        if (args.isEmpty()) {  //if there is no command with !help if statement runs
            for (ICommand iCommand : commandList) {
                if (iCommand.getType().equalsIgnoreCase("misc")) {
                    miscComs.add(Config.get("PREFIX") + iCommand.getName() + "\n");
                }
                if (iCommand.getType().equalsIgnoreCase("utility")) {
                    utilityComs.add(Config.get("PREFIX") + iCommand.getName() + "\n");
                }
                if (iCommand.getType().equalsIgnoreCase("member")) {
                    memberComs.add(Config.get("PREFIX") + iCommand.getName() + "\n");
                }
                if (iCommand.getType().equalsIgnoreCase("music")) {
                    musicComs.add(Config.get("PREFIX") + iCommand.getName() + "\n");
                }
                if (iCommand.getType().equalsIgnoreCase("rooms")) {
                    roomsComs.add(Config.get("PREFIX") + iCommand.getName() + "\n");
                }
                if (iCommand.getType().equalsIgnoreCase("shop")) {
                    shopComs.add(Config.get("PREFIX") + iCommand.getName() + "\n");
                }
            }
            embedBuilder.setTitle("List 'o Commands");
            embedBuilder.setDescription("Hi, I'm Mattrim \"Threestrings\" Mereg.\n Here's a list of things I can do!\n **For more info on a command use !help (command name)**");
            embedBuilder.setColor(Color.orange);
            embedBuilder.setThumbnail(ctx
                    .getJDA()
                    .getSelfUser()
                    .getAvatarUrl());
            embedBuilder.addField("Misc", "```"
                            + ArrayMethods.arrayAsString(miscComs)
                            + "```"
                    , true);
            embedBuilder.addField("Utility", "```"
                            + ArrayMethods.arrayAsString(utilityComs)
                            + "```"
                    , true);
            embedBuilder.addField("Member", "```"
                            + ArrayMethods.arrayAsString(memberComs)
                            + "```"
                    , true);
            embedBuilder.addField("Music", "```"
                            + ArrayMethods.arrayAsString(musicComs)
                            + "```"
                    , false);
            embedBuilder.addField("Rooms", "```"
                            + ArrayMethods.arrayAsString(roomsComs)
                            + "```"
                    , true);
            embedBuilder.addField("Shop", "```"
                            + ArrayMethods.arrayAsString(shopComs)
                            + "```"
                    , true);
            embedBuilder.setFooter("Hi I'm Mattrim \"Threestrings\" Mereg, " +
                    "I'm a journeyman instructor at the New College of Olamn and a resident performer at the Yawning Portal Inn." +
                    " \"Anyone can play 'The Three Flambinis' on a lute, but try playing it with just three strings!\"");
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }
        String search = args.get(0);  //set search string for if command does not exist
        ICommand command = manager.getCommand(search); //implement Icommand class with variable command, calls for manager and command search
        if (command == null) { //if there is no existing command if statement runs
            channel.sendMessage("Sorry I've got nothing for " + search).queue(); //bot sends out no command exists message
            return;
        }
        channel.sendMessage(command.getHelp()).queue(); //!help command is sent out
    }

    @Override
    public String getName() {
        return "help"; //set command to help within discord
    }

    @Override
    public String getHelp() { //when !help, help is called this is run
        return "Shows the list with what I can do\n" + "Usage: !help (command)";
    }

    @Override
    public String getType() {
        return "utility";
    }

    @Override
    public List<String> getAlisases() {  //set secondary help command called !bard
        return List.of("bard", "cmds", "commandlist");
    }
}
