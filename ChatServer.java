import java.util.*;

public class ChatServer {
    private Map<String, User> users;
    private Map<User, List<User>> blockedUsers;

    public ChatServer(){
        users = new HashMap<>();
        blockedUsers = new HashMap<>();
    }

    public void registerUser(User user){
        users.put(user.getName(), user);
    }

    public void unregisterUser(User user){
        users.remove(user.getName());
    }

    public Map<String, User> getUsers(){
        return users;
    }


    public void sendMessage(Message message){
        User sender = message.getSender();
        List<User> recipients = new ArrayList<>(message.getRecipients());

        for(User recipient: recipients){
            if(!checkIfBlocked(sender, recipient)){
                recipient.receiveMessage(message);
            }
        }
    }


    public void blockUser(User userToBlock, User blocker){
        List<User> blocklist = blockedUsers.getOrDefault(blocker, new ArrayList<>());
        if(!blocklist.contains(userToBlock)){
            blocklist.add(userToBlock);
            blockedUsers.put(blocker, blocklist);
        }
    }

    public boolean checkIfBlocked(User sender, User recipient){
        List<User> blocklist = blockedUsers.getOrDefault(recipient, Collections.emptyList());
        return blocklist.contains(sender);
    }
}
