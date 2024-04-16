import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ChatHistory implements IterableByUser{
    private List<Message> history;


    public ChatHistory(){
        history = new ArrayList<>();
    }

    public List<Message> getHistory() {
        return history;
    }

    public void showHistory(){
        for(Message message : history){
            System.out.println(message.getSender().getName() + ", " + message.getTimestamp() +
                    ": " + message.getContent());
        }
    }


    public void addMessage(Message message){
        history.add(message);
    }


    public MessageMemento setLastMessageSent(){
        if(history.isEmpty()){
            return null;
        }
        Message lastMessageSent = history.get(history.size() - 1);
        return new MessageMemento(lastMessageSent.getContent(), lastMessageSent.getTimestamp());
    }


    public void restoreState(MessageMemento messageMemento){
        if (messageMemento != null) {
            for(int i = history.size() - 1; i >= 0; i--){
                Message prevMessage = history.get(i);
                LocalDateTime prevTimestamp = prevMessage.getTimestamp();
                if((prevMessage.getContent().equals(messageMemento.getContent())
                        && (prevTimestamp.isEqual(messageMemento.getTimestamp())))){
                    history.remove(i);
                    for(User recipient : prevMessage.getRecipients()){
                        recipient.getChatHistory().restoreState(messageMemento);
                    }
                }
            }
        }


    }


    @Override
    public Iterator<Message> iterator(User userToSearchBy) {
        return new ChatHistoryIterator(history, userToSearchBy);
    }


}
