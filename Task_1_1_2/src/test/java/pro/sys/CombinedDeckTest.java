package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CombinedDeckTest {

    @Test
    void testAddDeck() {
        PokerDeck[] decks = new PokerDeck[]{new PokerDeck(), new PokerDeck()};
        decks[0].shuffle();
        decks[1].shuffle();

        CombinedDeck<PokerCard> combinedDeck = new CombinedDeck<>();
        combinedDeck.addDeck(decks[0]);
        assertEquals(decks[0].cards, combinedDeck.cards);
        combinedDeck.addDeck(decks[1]);
        assertEquals(decks[0].cards, combinedDeck.cards.subList(0, 52));
        assertEquals(decks[1].cards, combinedDeck.cards.subList(52, 104));
    }

    @Test
    void testAddDecks() {
        PokerDeck[] decks = new PokerDeck[]{new PokerDeck(), new PokerDeck()};
        decks[0].shuffle();
        decks[1].shuffle();

        CombinedDeck<PokerCard> combinedDeck = new CombinedDeck<>();
        combinedDeck.addDecks(Arrays.asList(decks));
        assertEquals(decks[0].cards, combinedDeck.cards.subList(0, 52));
        assertEquals(decks[1].cards, combinedDeck.cards.subList(52, 104));
    }

    @Test
    void testConstructor() {
        PokerDeck[] decks = new PokerDeck[]{new PokerDeck(), new PokerDeck()};
        decks[0].shuffle();
        decks[1].shuffle();

        CombinedDeck<PokerCard> combinedDeck = new CombinedDeck<>(Arrays.asList(decks));
        assertEquals(decks[0].cards, combinedDeck.cards.subList(0, 52));
        assertEquals(decks[1].cards, combinedDeck.cards.subList(52, 104));
    }
}