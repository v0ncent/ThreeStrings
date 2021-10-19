package Threestringsmusicbot.command.commands;
import java.util.Random;
public class QuestionMethod {
    public int randomAnswer(){ //create randomAnswer method
        Random random = new Random();
        int randomAns = random.nextInt(23);
        return randomAns;
    }
}
