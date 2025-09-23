package pro.sys;

import java.util.ArrayList;

/**
 * This is deck of PockerCards class.
 */
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
