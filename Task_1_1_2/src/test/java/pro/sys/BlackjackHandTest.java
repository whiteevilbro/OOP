package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import pro.sys.PokerCard.Face;
import pro.sys.PokerCard.Suit;

class BlackjackHandTest {

    @Test
    void testEvaluateNoArg() {
        BlackjackHand blackjackHand = new BlackjackHand();
        blackjackHand.addCard(new PokerCard(Face.EIGHT, Suit.DIAMONDS));
        assertEquals(8, blackjackHand.evaluate());
        blackjackHand.addCard(new PokerCard(Face.TEN, Suit.DIAMONDS));
        assertEquals(18, blackjackHand.evaluate());
        blackjackHand.addCard(new PokerCard(Face.KING, Suit.DIAMONDS));
        assertEquals(28, blackjackHand.evaluate());
    }

    @Test
    void testEvaluateSkip() {
        BlackjackHand blackjackHand = new BlackjackHand();
        blackjackHand.addCard(new PokerCard(Face.KING, Suit.DIAMONDS));
        blackjackHand.addCard(new PokerCard(Face.TEN, Suit.DIAMONDS));
        blackjackHand.addCard(new PokerCard(Face.EIGHT, Suit.DIAMONDS));
        assertEquals(8, blackjackHand.evaluate(2));
        assertEquals(18, blackjackHand.evaluate(1));
        assertEquals(28, blackjackHand.evaluate(0));
    }

    @Test
    void testToStringNoArg() {
        BlackjackHand blackjackHand = new BlackjackHand();
        blackjackHand.addCard(new PokerCard(Face.KING, Suit.DIAMONDS));
        blackjackHand.addCard(new PokerCard(Face.TEN, Suit.DIAMONDS));
        assertEquals("[король бубен, десятка бубен]", blackjackHand.toString());
    }

    @Test
    void testToStringSkip() {
        BlackjackHand blackjackHand = new BlackjackHand();
        blackjackHand.addCard(new PokerCard(Face.KING, Suit.DIAMONDS));
        blackjackHand.addCard(new PokerCard(Face.TEN, Suit.DIAMONDS));
        blackjackHand.addCard(new PokerCard(Face.EIGHT, Suit.DIAMONDS));
        assertEquals("[ph, десятка бубен, восьмерка бубен]", blackjackHand.toString(1, "ph"));
        assertEquals("[Ph, Ph, восьмерка бубен]", blackjackHand.toString(2, "Ph"));
        assertEquals("[PH, PH, PH]", blackjackHand.toString(3, "PH"));
    }

}