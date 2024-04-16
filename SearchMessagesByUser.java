import com.sun.source.tree.LiteralTree;

import java.util.Iterator;
import java.util.List;

public class SearchMessagesByUser implements Iterator<Message> {
    private Iterator<Message> iterator;

    public SearchMessagesByUser(Iterator<Message> iterator){
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext(){
        return iterator.hasNext();
    }

    @Override
    public Message next(){
        return iterator.next();
    }
}
