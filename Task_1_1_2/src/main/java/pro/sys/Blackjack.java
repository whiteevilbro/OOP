package pro.sys;

import java.util.ArrayList;

/**
 * This is Blackjack game glass.
 */
public class Blackjack {

    private final Deck<? extends PokerCard> deck;
    private final BlackjackHand dealer;
    private final BlackjackHand player;
    private final Score score;
    /**
     * Current round number.
     */
    public int round;
    private GameState gameState;

    Blackjack(Deck<? extends PokerCard> deck) {
        score = new Score();
        score.dealer = 0;
        score.player = 0;
        gameState = GameState.OVER;
        player = new BlackjackHand();
        dealer = new BlackjackHand();
        this.deck = deck;
    }

    Blackjack(int n) {
        this(constructDeck(n));
    }

    private static Deck<PokerCard> constructDeck(int n) {
        ArrayList<PokerDeck> decks = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            decks.add(new PokerDeck());
        }
        return new CombinedDeck<>(decks);
    }

    private Result evalGame(Result state) {
        gameState = GameState.OVER;
        if (state == Result.UNDEF) {
            int playerScore = player.evaluate();
            int dealerScore = dealer.evaluate();

            if (playerScore > dealerScore) {
                state = Result.WIN;
            } else if (playerScore == dealerScore) {
                state = Result.DRAW;
            } else {
                state = Result.LOSE;
            }
        }

        if (state == Result.WIN) {
            score.player += 1;
        } else if (state == Result.LOSE) {
            score.dealer += 1;
        }
        return state;
    }

    private Result evalOverdraw() {
        if (player.evaluate() > 21) {
            gameState = GameState.OVER;
            return Result.LOSE;
        }
        if (dealer.evaluate() > 21) {
            gameState = GameState.OVER;
            return Result.WIN;
        }
        return Result.UNDEF;
    }

    /**
     * Get known Dealer's hand value.
     *
     * @return int, known dealer card value sum.
     */
    public int getDealerValue() {
        if (gameState == GameState.IN_PROGRESS) {
            return dealer.evaluate(1);
        }
        return dealer.evaluate();
    }

    /**
     * Get Dealers hand.
     *
     * @return formatted String containig Dealer's cards.
     */
    public String getDealersHand() {
        if (gameState == GameState.IN_PROGRESS) {
            return dealer.toString(1, "Закрыто");
        }
        return dealer.toString();
    }

    /**
     * Get Player hand value.
     *
     * @return int Player card value sum.
     */
    public int getPlayerValue() {
        return player.evaluate();
    }

    /**
     * Get Player hand.
     *
     * @return formatted String containig Player's cards.
     */
    public String getPlayersHand() {
        return player.toString();
    }

    /**
     * Get score of the game.
     *
     * @return Score current score in this game.
     */
    public Score getScore() {
        return score;
    }

    /**
     * Take one more card.
     *
     * @return Result WIN/DRAW/LOSE/UNDEF depending on state of the game.
     */
    public Result hit() {
        if (gameState == GameState.IN_PROGRESS) {
            player.addCard(deck.getNext());
            Result state = evalOverdraw();
            if (state != Result.UNDEF) {
                return evalGame(state);
            }
        }
        return Result.UNDEF;
    }

    /**
     * Check if game is in progress.
     *
     * @return boolean is game in progress
     */
    public boolean isInProgress() {
        return gameState == GameState.IN_PROGRESS;
    }

    /**
     * Start new round.
     *
     * @return Result WIN/DRAW/LOSE/UNDEF depending on state of the game.
     */
    public Result newRound() {
        round += 1;
        player.clear();
        dealer.clear();
        gameState = GameState.IN_PROGRESS;
        deck.reset();
        deck.shuffle();

        dealer.addCard(deck.getNext());
        dealer.addCard(deck.getNext());
        player.addCard(deck.getNext());
        player.addCard(deck.getNext());

        int playerScore = player.evaluate();
        int dealerScore = dealer.evaluate();
        if (playerScore == 21 || dealerScore == 21) {
            return evalGame(Result.UNDEF);
        }
        return Result.UNDEF;
    }

    /**
     * Pass turn to the dealer and end the round.
     *
     * @return Result WIN/DRAW/LOSE/UNDEF depending on state of the game.
     */
    public Result pass() {
        if (gameState == GameState.OVER) {
            return Result.UNDEF;
        }
        gameState = GameState.OVER;
        while (dealer.evaluate() < 17) {
            dealer.addCard(deck.getNext());
        }
        return evalGame(evalOverdraw());
    }

    private enum GameState {
        OVER, IN_PROGRESS
    }

    /**
     * Game Result enumeration.
     */
    public enum Result {
        /**
         * Player won.
         */
        WIN,
        /**
         * Player lost.
         */
        LOSE,
        /**
         * Game ended in a draw.
         */
        DRAW,
        /**
         * Game state is unknown.
         */
        UNDEF
    }

    /**
     * Game Score object.
     */
    public static class Score {

        /**
         * How many rounds did Dealer won.
         */
        public int dealer;
        /**
         * How many rounds did Player won.
         */
        public int player;
    }

}
