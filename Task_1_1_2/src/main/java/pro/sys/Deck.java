package pro.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;


/**
 * Deck of T-type cards.
 *
 * @param <T> type of card
 */
public abstract class Deck<T extends Card> {

    protected ArrayList<T> cards;
    protected int position;

    /**
     * Pop upper card of the deck.
     *
     * @return upper card of the deck
     * @throws NoSuchElementException if deck is empty
     */
    public T getNext() throws NoSuchElementException {
        T card = peekNext();
        position++;
        return card;
    }

    /**
     * Get upper card of the deck.
     *
     * @return upper card of the deck
     * @throws NoSuchElementException if deck is empty
     */
    public T peekNext() throws NoSuchElementException {
        if (position >= cards.size()) {
            throw new NoSuchElementException("Deck is empty.");
        }
        return cards.get(position);
    }

    /**
     * Return all cards to the deck in the same order.
     */
    public void reset() {
        position = 0;
    }

    /**
     * Shuffles cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards.subList(position, cards.size()));
    }

    /**
     * Evaluates and return number of cards left in the game.
     *
     * @return int number of cards left in the deck
     */
    public int size() {
        return cards.size() - position;
    }
}
