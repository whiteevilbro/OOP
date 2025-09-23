package pro.sys;

import java.util.ArrayList;
import java.util.Map;

/**
 * Blackjack specific hand class.
 */
public class BlackjackHand extends Hand<PokerCard> {

    static final Map<PokerCard.Face, Integer> faceToValue = Map.ofEntries(
        Map.entry(PokerCard.Face.ACE, 11),
        Map.entry(PokerCard.Face.TWO, 2), Map.entry(PokerCard.Face.THREE, 3),
        Map.entry(PokerCard.Face.FOUR, 4), Map.entry(PokerCard.Face.FIVE, 5),
        Map.entry(PokerCard.Face.SIX, 6), Map.entry(PokerCard.Face.SEVEN, 7),
        Map.entry(PokerCard.Face.EIGHT, 8), Map.entry(PokerCard.Face.NINE, 9),
        Map.entry(PokerCard.Face.TEN, 10), Map.entry(PokerCard.Face.JACK, 10),
        Map.entry(PokerCard.Face.QUEEN, 10), Map.entry(PokerCard.Face.KING, 10));

    /**
     * @return Integes sum of all cards' values.
     */
    public Integer evaluate() {
        return evaluate(0);
    }

    Integer evaluate(int skip) {
        Integer result = 0;
        int aceCount = 0;
        for (PokerCard card : cards.subList(skip, cards.size())) {
            PokerCard.Face cardFace = card.getFace();
            if (cardFace == PokerCard.Face.ACE) {
                aceCount++;
            }
            result += faceToValue.get(cardFace);
        }

        while (result > 21 && aceCount-- > 0) {
            result -= 10;
        }

        return result;
    }

    public String toString() {
        ArrayList<String> list = new ArrayList<>(cards.size());
        for (PokerCard card : cards) {
            list.add(card.toString());
        }
        return list.toString();
    }

    String toString(int n, String placeholder) {
        ArrayList<String> list = new ArrayList<>(cards.size());
        for (int i = 0; i < n; i++) {
            list.add(placeholder);
        }
        for (PokerCard card : cards.subList(n, cards.size())) {
            list.add(card.toString());
        }
        return list.toString();
    }
}
