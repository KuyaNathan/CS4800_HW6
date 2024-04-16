import java.util.Iterator;
import java.util.List;

public class User implements IterableByUser{
    private String name;
    private ChatServer chatServer;
    private ChatHistory chatHistory;
    private MessageMemento lastMessageSent;

    public User(String name, ChatServer chatServer){
        this.name = name;
        this.chatServer = chatServer;
        this.chatHistory = new ChatHistory();
    }

    public String getName(){
        return name;
    }

    public ChatHistory getChatHistory(){
        return chatHistory;
    }


    public void sendMessage(List<User> recipients, String content){
        Message message = new Message(this, recipients, content);
        chatServer.sendMessage(message);
        chatHistory.addMessage(message);
        lastMessageSent = chatHistory.setLastMessageSent();
    }

    public void receiveMessage(Message message){
        chatHistory.addMessage(message);
    }

    public void undoLastMessage(){
        if(lastMessageSent != null){
            chatHistory.restoreState(lastMessageSent);
            lastMessageSent = null;
        }
    }

    public void blockUser(User userToBlock){
        chatServer.blockUser(userToBlock, this);
    }

    @Override
    public Iterator<Message> iterator(User userToSearchBy){
        return chatHistory.iterator(userToSearchBy);
    }

}
