package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import pro.sys.Blackjack.Result;
import pro.sys.PokerCard.Face;
import pro.sys.PokerCard.Suit;

class BlackjackTest {

    @Test
    public void testDealerBlackjack() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.ACE, Face.TEN, Face.SEVEN));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.LOSE, blackjack.newRound());
        assertEquals(1, blackjack.getScore().dealer);
    }

    @Test
    public void testDealerOverdraw() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.SIX, Face.TEN, Face.TEN, Face.TEN));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.UNDEF, blackjack.newRound());
        assertEquals(Result.WIN, blackjack.pass());
        assertEquals(1, blackjack.getScore().player);
    }

    @Test
    public void testGetters() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.TEN, Face.TEN, Face.SEVEN));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.UNDEF, blackjack.newRound());
        assertEquals(10, blackjack.getDealerValue());
        assertEquals(17, blackjack.getPlayerValue());
        assertEquals("[Закрыто, десятка треф]", blackjack.getDealersHand());
        assertEquals("[десятка треф, семерка треф]", blackjack.getPlayersHand());
        assertTrue(blackjack.isInProgress());
        assertEquals(Result.LOSE, blackjack.pass());
        assertEquals(20, blackjack.getDealerValue());
        assertEquals("[десятка треф, десятка треф]", blackjack.getDealersHand());
        assertFalse(blackjack.isInProgress());
    }

    @Test
    public void testLose() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.TEN, Face.TEN, Face.SEVEN));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.UNDEF, blackjack.newRound());
        assertEquals(Result.LOSE, blackjack.pass());
        assertEquals(1, blackjack.getScore().dealer);
    }

    @Test
    public void testPlayerBlackjack() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.SEVEN, Face.TEN, Face.ACE));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.WIN, blackjack.newRound());
        assertEquals(1, blackjack.getScore().player);
    }

    @Test
    public void testPlayerDealerBlackjack() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.ACE, Face.TEN, Face.ACE));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.DRAW, blackjack.newRound());
        assertEquals(0, blackjack.getScore().player);
        assertEquals(0, blackjack.getScore().dealer);
    }

    @Test
    public void testPlayerOverdraw() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.SEVEN, Face.TEN, Face.TEN, Face.TEN));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.UNDEF, blackjack.newRound());
        assertEquals(Result.LOSE, blackjack.hit());
        assertEquals(1, blackjack.getScore().dealer);
    }

    @Test
    public void testWin() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.SEVEN, Face.TEN, Face.FOUR, Face.FOUR));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.UNDEF, blackjack.newRound());
        assertEquals(Result.UNDEF, blackjack.hit());
        assertEquals(Result.WIN, blackjack.pass());
        assertEquals(1, blackjack.getScore().player);
    }

    @Test
    public void testWrongUsage() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.TEN, Face.TEN, Face.SEVEN));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.UNDEF, blackjack.pass());
        assertEquals(Result.UNDEF, blackjack.hit());

        deck = new PredeterminedPokerDeck(new ArrayList<>());
        blackjack = new Blackjack(deck);
        assertThrows(NoSuchElementException.class, blackjack::newRound);

    }

    @Test
    public void testaLotAndAce() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.SEVEN, Face.TEN, Face.TEN, Face.ACE));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.UNDEF, blackjack.newRound());
        assertEquals(Result.UNDEF, blackjack.hit());
        assertEquals(Result.WIN, blackjack.pass());
        assertEquals(1, blackjack.getScore().player);
    }

    @Test
    public void testaLotOfAces() {
        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(
            Arrays.asList(Face.TEN, Face.SEVEN, Face.ACE, Face.ACE, Face.ACE));
        Blackjack blackjack = new Blackjack(deck);
        assertEquals(Result.UNDEF, blackjack.newRound());
        assertEquals(Result.UNDEF, blackjack.hit());
        assertEquals(Result.LOSE, blackjack.pass());
        assertEquals(1, blackjack.getScore().dealer);
    }

    /**
     * Predetermined Pocker Deck class.
     */
    public static class PredeterminedPokerDeck extends PokerDeck {

        PredeterminedPokerDeck(List<Face> faces) {
            cards = new ArrayList<>(faces.size());
            for (Face face : faces) {
                cards.add(new PokerCard(face, Suit.CLUBS));
            }
        }

        @Override
        public void reset() {
        }

        @Override
        public void shuffle() {
        }
    }
}