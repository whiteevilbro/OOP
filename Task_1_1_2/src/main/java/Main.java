import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack(1);
        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            blackjack.newRound();
            System.out.printf("Раунд %d\n", blackjack.round);
            System.out.println("Карты дилера:");
            System.out.printf("%s => (min %d)\n", blackjack.getDealersHand(),
                blackjack.getDealerValue());

            while (true) {
                System.out.println("Ваши карты:");
                System.out.printf("%s => (%d)\n", blackjack.getPlayersHand(),
                    blackjack.getPlayerValue());

                System.out.println("Ещё? (y/N)");
                String ans = sc.next("[yYNn]");
                if (ans.matches("[Yy]")) {
                    blackjack.hit();
                } else {
                    Blackjack.Result result = blackjack.pass();
                    System.out.println("Дилер открывает карты:");
                    System.out.printf("%s => (%d)\n", blackjack.getDealersHand(),
                        blackjack.getDealerValue());
                    switch (result) {
                        case WIN -> {
                            System.out.println("Вы выиграли. Ещё? [y/N]");
                        }
                        case DRAW -> {
                            System.out.println("Ничья. Ещё? [y/N]");
                        }
                        case LOSE -> {
                            System.out.println("Вы проиграли. Ещё? [y/N]");
                        }
                        default -> {
                            System.out.println("Случилось непредвиденное. Ещё? [Y/n]");
                        }
                    }
                    ans = sc.next("[yYNn]");
                    if (ans.matches("[nN]")) {
                        flag = false;
                    }
                    break;
                }
            }
            System.out.println("\n\n");
        }
    }
}
