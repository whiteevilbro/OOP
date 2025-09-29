package pro.sys;

import java.util.ArrayList;

/**
 * Hand of T-type cards class.
 *
 * @param <T> type of card
 */
public abstract class Hand<T extends Card> {

    protected final ArrayList<T> cards;

    Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Adds card to the bottom of the deck.
     *
     * @param card Card to add.
     */
    public void addCard(T card) {
        cards.add(card);
    }

    /**
     * Clears deck.
     */
    public void clear() {
        cards.clear();
    }
}