package pro.sys;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import pro.sys.Blackjack.Result;

/**
 * Main class.
 */
public class Main {

    /**
     * Main method.
     *
     * @param args kvargs
     */
    public static void main(String[] args) {
        pseudointeractive(System.in, System.out, null);
    }

    /**
     * Interactive Blackjack.
     *
     * @param in InputStream from where commands are read from
     * @param out PrintStream to which responses are written
     * @param deck Deck to use. Pass null to generate random PokerDeck
     */
    public static void pseudointeractive(InputStream in, PrintStream out,
        Deck<? extends PokerCard> deck) {
        Blackjack blackjack;
        if (deck == null) {
            blackjack = new Blackjack(1);
        } else {
            blackjack = new Blackjack(deck);
        }
        Scanner sc = new Scanner(in);
        Result result;
        String ans;

        while (true) {
            result = blackjack.newRound();
            out.printf("Раунд %d\n", blackjack.round);
            out.println("Карты дилера:");
            out.printf("%s => (min %d)\n", blackjack.getDealersHand(),
                blackjack.getDealerValue());
            if (result == Result.UNDEF) {
                boolean answer;
                boolean overdraw = false;
                do {
                    out.println("Ваши карты:");
                    out.printf("%s => (%d)\n", blackjack.getPlayersHand(),
                        blackjack.getPlayerValue());

                    out.println("Ещё? (y/N)");
                    ans = sc.next("[yYNn]");
                    answer = ans.matches("[Yy]");
                    if (answer && blackjack.hit() != Result.UNDEF) {
                        overdraw = true;
                    }
                } while (answer && !overdraw);
                result = blackjack.pass();
                if (!overdraw) {
                    out.println("Дилер открывает карты:");
                    out.printf("%s => (%d)\n", blackjack.getDealersHand(),
                        blackjack.getDealerValue());
                }
                switch (result) {
                    case WIN -> out.println("Вы выиграли. Ещё? [y/N]");
                    case DRAW -> out.println("Ничья. Ещё? [y/N]");
                    case LOSE -> out.println("Вы проиграли. Ещё? [y/N]");
                    default -> out.println("Случилось непредвиденное. Ещё? [Y/n]");
                }
            } else {
                out.println("Ваши карты:");
                out.printf("%s => (%d)\n", blackjack.getPlayersHand(),
                    blackjack.getPlayerValue());
                switch (result) {
                    case WIN -> out.println("У вас блэкджек. Ещё? [y/N]");
                    case LOSE -> out.println("У диллера блекджек. Ещё? [y/N]");
                    case DRAW -> out.println("И у вас и у диллера блэкджек. Ещё? [y/N]");
                    default -> {
                    }
                }
            }
            ans = sc.next("[yYNn]");
            if (ans.matches("[nN]")) {
                break;
            }
            out.println();
        }
    }
}

