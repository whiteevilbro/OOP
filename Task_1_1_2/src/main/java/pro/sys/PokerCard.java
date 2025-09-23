package pro.sys;

import java.util.Map;

/**
 * This is Pocker Card class
 */
public class PokerCard extends Card {

    private final Map<Face, String> facesLocale;
    private final Map<Suit, String> suitLocale;

    private final Suit suit;
    private final Face face;

    PokerCard(Face face, Suit suit, Map<Face, String> facesLocale, Map<Suit, String> suitLocale) {
        this.face = face;
        this.suit = suit;
        this.facesLocale = facesLocale;
        this.suitLocale = suitLocale;
    }

    PokerCard(Face face, Suit suit) {
        this(face, suit,
            Map.ofEntries(
                Map.entry(Face.ACE, "туз"), Map.entry(Face.TWO, "двойка"),
                Map.entry(Face.THREE, "тройка"), Map.entry(Face.FOUR, "четвёрка"),
                Map.entry(Face.FIVE, "пятерка"), Map.entry(Face.SIX, "шестерка"),
                Map.entry(Face.SEVEN, "семёрка"), Map.entry(Face.EIGHT, "восьмерка"),
                Map.entry(Face.NINE, "девятка"), Map.entry(Face.TEN, "десятка"),
                Map.entry(Face.JACK, "валет"), Map.entry(Face.QUEEN, "дама"),
                Map.entry(Face.KING, "король")),
            Map.ofEntries(
                Map.entry(Suit.HEARTS, "черв"), Map.entry(Suit.SPADES, "пик"),
                Map.entry(Suit.DIAMONDS, "бубен"), Map.entry(Suit.CLUBS, "треф"))
        );
    }

    /**
     * @return Face of the card.
     */
    public Face getFace() {
        return face;
    }

    /**
     * @return Suit of the card
     */
    public Suit getSuit() {
        return suit;
    }

    public String toString() {
        return String.format("%s %s", facesLocale.get(getFace()), suitLocale.get(getSuit()));
    }

    /**
     * PokerCard Face enumeration.
     */
    public enum Face {
        /**
         * Ace face.
         */
        ACE,
        /**
         * Two face.
         */
        TWO,
        /**
         * Three face.
         */
        THREE,
        /**
         * Four face.
         */
        FOUR,
        /**
         * Five face.
         */
        FIVE,
        /**
         * Six face.
         */
        SIX,
        /**
         * Seven face.
         */
        SEVEN,
        /**
         * Eight face.
         */
        EIGHT,
        /**
         * Nine face.
         */
        NINE,
        /**
         * Ten face.
         */
        TEN,
        /**
         * Jack face.
         */
        JACK,
        /**
         * Queen face.
         */
        QUEEN,
        /**
         * King face.
         */
        KING
    }

    /**
     * PokerCard Suit enumeration.
     */
    public enum Suit {
        /**
         * Hearts suit.
         */
        HEARTS,
        /**
         * Spades suit.
         */
        SPADES,
        /**
         * Diamonds suit.
         */
        DIAMONDS,
        /**
         * Clubs suit.
         */
        CLUBS
    }

}
