import java.util.ArrayList;

public class PokerDeck extends Deck<PokerCard> {

    PokerDeck() {
        cards = new ArrayList<>(PokerCard.Face.values().length * PokerCard.Suit.values().length);
        for (PokerCard.Suit suit : PokerCard.Suit.values()) {
            for (PokerCard.Face face : PokerCard.Face.values()) {
                cards.add(new PokerCard(face, suit));
            }
        }
    }
}
