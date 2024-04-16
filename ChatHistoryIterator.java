import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ChatHistoryIterator implements Iterator<Message>{
    private User userToSearchBy;
    private List<Message> history;
    private int messageIndex;

    public ChatHistoryIterator(List<Message> history, User userToSearchBy){
        this.history = history;
        this.userToSearchBy = userToSearchBy;
        this.messageIndex = 0;
    }

    @Override
    public boolean hasNext(){
        while(messageIndex < history.size()){
            Message currentMessage = history.get(messageIndex);
            if((currentMessage.getSender().equals(userToSearchBy)) ||
                    currentMessage.getRecipients().contains(userToSearchBy)){
                return true;
            }
            messageIndex++;
        }
        return false;
    }

    @Override
    public Message next(){
        if(!hasNext()){
            throw new NoSuchElementException("");
        }
        return history.get(messageIndex++);
    }
}
