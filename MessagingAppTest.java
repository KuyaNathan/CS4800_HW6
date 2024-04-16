import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;


public class MessagingAppTest {
    @Test
    public void testRegisterUser(){
        ChatServer dms = new ChatServer();
        User adam = new User("Adam", dms);
        dms.registerUser(adam);

        assertTrue(dms.getUsers().containsKey("Adam"));
        assertEquals(adam, dms.getUsers().get("Adam"));
    }

    @Test
    public void testSendMessage(){
        ChatServer dms = new ChatServer();
        User sender = new User("sender", dms);
        User recipient = new User("recipient", dms);
        Message msg = new Message(sender, List.of(recipient), "test, 123");
        dms.sendMessage(msg);

        assertEquals("test, 123", recipient.getChatHistory().getHistory().get(0).getContent());
        assertEquals(1, recipient.getChatHistory().getHistory().size());
    }

    @Test
    public void testBlockUser(){
        ChatServer dms = new ChatServer();
        User one = new User("one", dms);
        User two = new User("two", dms);
        User three = new User("three", dms);

        one.blockUser(two);

        two.sendMessage(List.of(one), "hi");
        two.sendMessage(List.of(three), "hello");

        assertEquals(0, one.getChatHistory().getHistory().size());
        assertEquals("hello", three.getChatHistory().getHistory().get(0).getContent());
    }

    @Test
    public void testUndoLastSentMessage(){
        ChatServer dms = new ChatServer();
        User sender = new User("sender", dms);
        User receiver = new User("receiver", dms);

        sender.sendMessage(List.of(receiver), "hi");
        sender.sendMessage(List.of(receiver), "hey");
        sender.undoLastMessage();

        assertEquals(1, receiver.getChatHistory().getHistory().size());
    }

    @Test
    public void testShowChatHistory(){
        ChatServer dms = new ChatServer();
        User one = new User("one", dms);
        User two = new User("two", dms);

        one.sendMessage(List.of(two), "hi");
        two.sendMessage(List.of(one), "hey");

        assertEquals(2, one.getChatHistory().getHistory().size());
        assertEquals("hi", one.getChatHistory().getHistory().get(0).getContent());
        assertEquals("hey", one.getChatHistory().getHistory().get(1).getContent());
        assertEquals(2, two.getChatHistory().getHistory().size());
        assertEquals("hi", two.getChatHistory().getHistory().get(0).getContent());
        assertEquals("hey", two.getChatHistory().getHistory().get(1).getContent());
    }

    @Test
    public void testSearchMessagesByUser(){
        ChatServer dms = new ChatServer();
        User one = new User("one", dms);
        User two = new User("two", dms);
        User three = new User("three", dms);

        Message msg1 = new Message(one, List.of(two), "hi");
        Message msg2 = new Message(two, List.of(one), "hey");

        one.sendMessage(List.of(two), "hi");
        two.sendMessage(List.of(one), "hey");
        three.sendMessage(List.of(one), "yooo");

        Iterator<Message> oneAndTwoChatHistory = new SearchMessagesByUser(one.iterator(two));
        assertTrue(oneAndTwoChatHistory.hasNext());
        assertEquals("hi", oneAndTwoChatHistory.next().getContent());
        assertTrue(oneAndTwoChatHistory.hasNext());
        assertEquals("hey", oneAndTwoChatHistory.next().getContent());
        assertFalse(oneAndTwoChatHistory.hasNext());
    }
}
