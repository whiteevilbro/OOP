package pro.sys;

import java.util.ArrayList;

/**
 * Combined deck of T-type cards.
 *
 * @param <T> type of card
 */
public class CombinedDeck<T extends Card> extends Deck<T> {

    CombinedDeck() {
        cards = new ArrayList<>();
    }

    CombinedDeck(Iterable<? extends Deck<T>> decks) {
        this();
        addDecks(decks);
    }

    /**
     * Append given deck to the bottom of this deck.
     *
     * @param deck deck to append
     */
    public void addDeck(Deck<T> deck) {
        cards.addAll(deck.cards);
    }

    /**
     * Append given decks to the bottom of this deck.
     *
     * @param decks decks to append
     */
    public void addDecks(Iterable<? extends Deck<T>> decks) {
        for (Deck<T> deck : decks) {
            addDeck(deck);
        }
    }
}
