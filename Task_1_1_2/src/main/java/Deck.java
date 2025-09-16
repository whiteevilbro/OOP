import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

public abstract class Deck<T extends Card> {
    protected ArrayList<T> cards;
    protected int position;

    public T getNext() throws NoSuchElementException {
        T card = peekNext();
        position++;
        return card;
    }

    public T peekNext() {
        if (position >= cards.size()) {
            throw new NoSuchElementException("Deck is empty.");
        }
        return cards.get(position);
    }

    public void shuffle() {
        Collections.shuffle(cards.subList(position, cards.size()));
    }

    public int size() {
        return cards.size() - position;
    }
}
