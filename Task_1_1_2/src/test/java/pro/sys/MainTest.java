package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import pro.sys.BlackjackTest.PredeterminedPokerDeck;
import pro.sys.PokerCard.Face;

class MainTest {

    @Test
    void testInteractive() {
        String inputString =
            "y n" +
                " y " + "y n" +
                " y " +
                " y " +
                " y " + " n";
        String expectedString = """
            Раунд 1
            Карты дилера:
            [Закрыто, семёрка треф] => (min 7)
            Ваши карты:
            [десятка треф, пятерка треф] => (15)
            Ещё? (y/N)
            Ваши карты:
            [десятка треф, пятерка треф, пятерка треф] => (20)
            Ещё? (y/N)
            Дилер открывает карты:
            [десятка треф, семёрка треф] => (17)
            Вы выиграли. Ещё? [y/N]
            
            Раунд 2
            Карты дилера:
            [Закрыто, семёрка треф] => (min 7)
            Ваши карты:
            [десятка треф, четвёрка треф] => (14)
            Ещё? (y/N)
            Ваши карты:
            [десятка треф, четвёрка треф, двойка треф] => (16)
            Ещё? (y/N)
            Дилер открывает карты:
            [десятка треф, семёрка треф] => (17)
            Вы проиграли. Ещё? [y/N]
            
            Раунд 3
            Карты дилера:
            [десятка треф, туз треф] => (min 21)
            Ваши карты:
            [десятка треф, семёрка треф] => (17)
            У диллера блекджек. Ещё? [y/N]
            
            Раунд 4
            Карты дилера:
            [десятка треф, семёрка треф] => (min 17)
            Ваши карты:
            [десятка треф, туз треф] => (21)
            У вас блэкджек. Ещё? [y/N]
            
            Раунд 5
            Карты дилера:
            [десятка треф, туз треф] => (min 21)
            Ваши карты:
            [десятка треф, туз треф] => (21)
            И у вас и у диллера блэкджек. Ещё? [y/N]
            """.strip();
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        Deck<? extends PokerCard> deck = new PredeterminedPokerDeck(Arrays.asList(
            Face.TEN, Face.SEVEN, Face.TEN, Face.FIVE, Face.FIVE,
            Face.TEN, Face.SEVEN, Face.TEN, Face.FOUR, Face.TWO,
            Face.TEN, Face.ACE, Face.TEN, Face.SEVEN,
            Face.TEN, Face.SEVEN, Face.TEN, Face.ACE,
            Face.TEN, Face.ACE, Face.TEN, Face.ACE
        ));
        Main.pseudointeractive(inputStream, printStream, deck);
        String actualString = outputStream.toString().replace("\r\n", "\n").strip();
        assertEquals(expectedString, actualString);
    }
}