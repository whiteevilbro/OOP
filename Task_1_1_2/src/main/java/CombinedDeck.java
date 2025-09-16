import java.util.ArrayList;

public class CombinedDeck<T extends Card> extends Deck<T> {
    CombinedDeck() {
        cards = new ArrayList<T>();
    }

    CombinedDeck(Iterable<? extends Deck<T>> decks) {
        this();
        addDecks(decks);
    }

    public void addDeck(Deck<T> deck) {
        cards.addAll(deck.cards);
    }

    public void addDecks(Iterable<? extends Deck<T>> decks) {
        for (Deck<T> deck : decks) {
            addDeck(deck);
        }
    }
}
