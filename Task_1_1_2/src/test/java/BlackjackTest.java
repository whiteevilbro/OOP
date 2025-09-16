import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BlackjackTest {

    @Test
    void getDealersHand() {
        Blackjack blackjack = new Blackjack(1);
        assertThrows(NullPointerException.class, blackjack::getDealersHand);
        blackjack.newRound();
        assertNotEquals("", blackjack.getDealersHand());
    }

    @Test
    void getDealerValue() {
        Blackjack blackjack = new Blackjack(1);
        blackjack.newRound();
        assertNotEquals(0, blackjack.getDealerValue());
    }

    @Test
    void getPlayerValue() {
        Blackjack blackjack = new Blackjack(1);
        blackjack.newRound();
        assertNotEquals(0, blackjack.getPlayerValue());
    }

    @Test
    void getPlayersHand() {
        Blackjack blackjack = new Blackjack(1);
        assertThrows(NullPointerException.class, blackjack::getPlayersHand);
        blackjack.newRound();
        assertNotEquals("", blackjack.getPlayersHand());
    }

    @Test
    void hit() {
        Blackjack blackjack = new Blackjack(1);
        blackjack.hit();
        blackjack.newRound();
        int t = blackjack.getPlayersHand().length();
        blackjack.hit();
        int tt = blackjack.getPlayersHand().length();
        assertNotEquals(t, tt);
    }

    @Test
    void newRound() {
        Blackjack blackjack = new Blackjack(1);
        blackjack.newRound();
        assertEquals(1, blackjack.round);
    }

    @Test
    void pass() {
        Blackjack blackjack = new Blackjack(1);
        assertEquals(Blackjack.Result.UNDEF, blackjack.pass());
        for (int i = 0; i < 16; i++) {
            blackjack.newRound();
            blackjack.pass();
        }
    }
}