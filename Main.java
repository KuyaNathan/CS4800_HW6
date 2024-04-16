import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ChatServer messenger = new ChatServer();
        User ross = new User("Ross", messenger);
        User cody = new User("Cody", messenger);
        User angie = new User("Angie", messenger);

        messenger.registerUser(ross);
        messenger.registerUser(cody);
        messenger.registerUser(angie);

        // user can block messages from specific users
        angie.blockUser(ross);

        // send message to one or more users
        ross.sendMessage(Arrays.asList(cody, angie), "hi Cody and Angie");
        ross.sendMessage(Arrays.asList(angie), "did you block me?");
        ross.undoLastMessage();
        cody.sendMessage(Arrays.asList(ross, angie), "hi guys");
        angie.sendMessage(Arrays.asList(cody), "I blocked Ross");
        angie.sendMessage(Arrays.asList(cody), "I don't like him");
        angie.undoLastMessage();


        // view a user's chat history
        System.out.println("\nRoss' chat history:");
        ross.getChatHistory().showHistory();

        System.out.println("\nCody's chat history:");
        cody.getChatHistory().showHistory();

        System.out.println("\nAngie's chat history:");
        angie.getChatHistory().showHistory();
    }
}