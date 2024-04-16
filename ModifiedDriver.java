import java.util.Arrays;
import java.util.Iterator;

public class ModifiedDriver {
    public static void main(String[] args){
        ChatServer messenger = new ChatServer();

        User joe = new User("Joe", messenger);
        User rhea = new User("Rhea", messenger);
        User tracy = new User("Tracy", messenger);

        messenger.registerUser(joe);
        messenger.registerUser(rhea);
        messenger.registerUser(tracy);

        joe.sendMessage(Arrays.asList(rhea, tracy), "do yall want to hang out this weekend");
        rhea.sendMessage(Arrays.asList(joe, tracy), "yeah sure");
        tracy.sendMessage(Arrays.asList(joe, rhea), "i don't feel like it");
        tracy.undoLastMessage();
        tracy.sendMessage(Arrays.asList(joe, rhea), "i can't, i already have plans. what about next week?");

        joe.blockUser(tracy);
        tracy.sendMessage(Arrays.asList(joe, rhea), "guys?");
        joe.sendMessage(Arrays.asList(rhea), "i blocked tracy");
        rhea.sendMessage(Arrays.asList(joe), "why?");
        joe.sendMessage(Arrays.asList(rhea), "she is not being a good friend");
        rhea.sendMessage(Arrays.asList(joe), "you're being dramatic");


        System.out.println("Joe's chat history with rhea");
        Iterator<Message> joeIterator = joe.iterator(rhea);
        while(joeIterator.hasNext()){
            Message msg = joeIterator.next();
            System.out.println(msg.getSender().getName() + ", " + msg.getTimestamp() +
                    ": " + msg.getContent());
        }
    }
}
