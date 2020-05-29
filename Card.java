public class Card {
    public enum Value {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
    

        public static Value getValues(int index) {
            Value[] values = Value.values();
            return values[index];
        }

        public static int getValueLength() {
            return Value.values().length;
        }
    }

    public enum Suit {
        HEART, DIAMOND, CLUB, SPADE;

        public static Suit getSuitLength(int index) {
            Suit[] suits = Suit.values();
            return suits[index];
        }

        public static int getSuitLength() {
            return Suit.values().length;
        }
    }


    private Suit suit;
    private Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    

}