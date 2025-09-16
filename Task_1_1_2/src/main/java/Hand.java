import java.util.ArrayList;

public abstract class Hand<T extends Card> {
    public ArrayList<T> cards;

    Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(T card) {
        cards.add(card);
    }

    public abstract Integer evaluate();
}