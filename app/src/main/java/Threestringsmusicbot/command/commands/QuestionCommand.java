package Threestringsmusicbot.command.commands;
import Threestringsmusicbot.command.CommandContext;
import Threestringsmusicbot.command.ICommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
public class QuestionCommand implements ICommand {
   EventWaiter waiter; //define waiter
   public QuestionCommand(EventWaiter waiter){ //import over constructor for eventWaiter
       this.waiter = waiter;
   }
    ArrayList<String> positiveResponses = new ArrayList<>(Arrays.asList( //create array list of answers
            "Hmm yes.", "Hmm absolutely!", "YES!", "Most likely", "Seems good.",
            "My lute thinks yes!", "Without a doubt.", "You may rely on it", "Yes definitely.", "Absolutely."));
    ArrayList<String> negativeResponses = new ArrayList<>(Arrays.asList(
            "Hmm no.", "hmm absolutely not.","No!","Probably not.","hmm not likely.", "Huh? Whats that lute?...He says no."
    ));
    ArrayList<String> vagueResponses = new ArrayList<>(Arrays.asList(
            "Hmm im not sure.", "hmm im not certain.", "Tsk, I dont think you want to know what I think about that.", "I cant say for certain sorry.","Why are you asking me? All I know is music!",
            "Cant tell you sorry", "Its best if we leave that unanswered."
    ));
    @Override
    public void handle(CommandContext ctx){
        final TextChannel channel = ctx.getChannel(); //Get text channel command is used in
        QuestionMethod roll = new QuestionMethod(); //implement roll method
        ArrayList<String> allAnswers = new ArrayList<>();//create new string array list with all answers
        allAnswers.addAll(negativeResponses); //add array lists to allAnswers
        allAnswers.addAll(positiveResponses);
        allAnswers.addAll(vagueResponses);
        int answer = roll.randomAnswer(); //create new integer answer that rolls a random answer with method
        channel.sendMessage("Whats your question?").queue(); //send message to chat
        waiter.waitForEvent(GuildMessageReceivedEvent.class, //wait for event message received
                e -> e.getMessage().getContentRaw().equals("remy") && e.getChannel().equals(ctx.getChannel()), e -> {
            channel.sendMessage("Hmm...").queue();
            channel.sendMessageFormat(allAnswers.get(answer)).queue();
                }, 1L, TimeUnit.MINUTES,
                () -> channel.sendMessage("").queue()); //add timeout);
    }
    @Override
    public String getName() {
        return "ask";
    }
    @Override
    public String getHelp() {
        return "Ask me a question!";
    }
}
