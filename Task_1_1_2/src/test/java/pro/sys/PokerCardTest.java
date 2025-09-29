package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import pro.sys.PokerCard.Face;
import pro.sys.PokerCard.Suit;

class PokerCardTest {

    @Test
    void getFace() {
        PokerCard pokerCard = new PokerCard(Face.EIGHT, Suit.DIAMONDS);
        assertEquals(Face.EIGHT, pokerCard.getFace());
    }

    @Test
    void getSuit() {
        PokerCard pokerCard = new PokerCard(Face.EIGHT, Suit.DIAMONDS);
        assertEquals(Suit.DIAMONDS, pokerCard.getSuit());
    }

    @Test
    void testToString() {
        Face[] faces = {Face.ACE, Face.TWO, Face.THREE, Face.FOUR, Face.FIVE, Face.SIX, Face.SEVEN,
            Face.EIGHT, Face.NINE, Face.TEN, Face.JACK, Face.QUEEN, Face.KING};
        Suit[] suits = {Suit.HEARTS, Suit.CLUBS, Suit.DIAMONDS, Suit.SPADES};
        String[] names = {
            "туз черв",
            "двойка треф",
            "тройка бубен",
            "четверка пик",
            "пятерка черв",
            "шестерка треф",
            "семерка бубен",
            "восьмерка пик",
            "девятка черв",
            "десятка треф",
            "валет бубен",
            "дама пик",
            "король черв",
        };

        for (int i = 0; i < 13; i++) {
            assertEquals(names[i], (new PokerCard(faces[i], suits[i % 4])).toString());
        }
    }
}