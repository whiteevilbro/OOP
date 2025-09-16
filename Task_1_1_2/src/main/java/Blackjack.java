import java.util.ArrayList;

public class Blackjack {

    private final int deckc;
    public int round;
    Score score;
    private Deck<PokerCard> deck;
    private GameState gameState;
    private BlackjackHand dealer;
    private BlackjackHand player;

    Blackjack(int n) {
        score = new Score();
        score.dealer = 0;
        score.player = 0;
        deckc = n;
        gameState = GameState.OVER;
    }

    public String getDealersHand() {
        if (gameState == GameState.IN_PROGRESS) {
            return dealer.toString(1, "Закрыто");
        }
        return dealer.toString();
    }

    public int getDealerValue() {
        if (gameState == GameState.IN_PROGRESS)
            return dealer.evaluate(1);
        return dealer.evaluate();
    }

    public int getPlayerValue() {
        return player.evaluate();
    }

    public String getPlayersHand() {
        return player.toString();
    }

    public void hit() {
        if (gameState == GameState.IN_PROGRESS) {
            player.addCard(deck.getNext());
            playerEval();
        }
    }

    public void newRound() {
        round += 1;
        gameState = GameState.IN_PROGRESS;
        player = new BlackjackHand();
        dealer = new BlackjackHand();
        ArrayList<PokerDeck> decks = new ArrayList<>(deckc);
        for (int i = 0; i < deckc; i++) {
            decks.add(new PokerDeck());
        }
        deck = new CombinedDeck<PokerCard>(decks);
        deck.shuffle();

        dealer.addCard(deck.getNext());
        dealer.addCard(deck.getNext());
        player.addCard(deck.getNext());
        player.addCard(deck.getNext());

    }

    public Result pass() {
        if (gameState == GameState.OVER) {
            return Result.UNDEF;
        }
        gameState = GameState.OVER;
        if (player.evaluate() > 21) {
            return Result.LOSE;
        }
        while (dealer.evaluate() < 17) {
            dealer.addCard(deck.getNext());
        }
        int t = dealer.evaluate();
        int tt = player.evaluate();
        if (t > 21 || tt > t) {
            score.player += 1;
            return Result.WIN;
        }
        if (t == tt) {
            return Result.DRAW;
        }
        score.dealer += 1;
        return Result.LOSE;
    }

    private void playerEval() {
        if (player.evaluate() > 21) {
            gameState = GameState.OVER;
        }
    }

    private enum GameState {
        OVER, IN_PROGRESS
    }

    public enum Result {
        WIN, LOSE, DRAW, UNDEF
    }

    public static class Score {

        public int dealer;
        public int player;
    }

}
