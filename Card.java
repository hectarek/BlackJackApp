import java.text.BreakIterator;

public class Card {

    private Suit mySuit;
    private int myNumber;

    public Card(Suit aSuit, int aNumber) {
        this.mySuit = aSuit;
        this.myNumber = aNumber;
    }

    public Suit getMySuit() {
        return mySuit;
    }

    public void setMySuit(Suit mySuit) {
        this.mySuit = mySuit;
    }

    public int getMyNumber() {
        return myNumber;
    }

    public void setMyNumber(int myNumber) {
        this.myNumber = myNumber;
    }

    @Override
    public String toString() {
        String numStr = "";
        String suitStr = "";

        switch (this.myNumber) {
            case 1:
                numStr = "Ace(A)";
                break;
            case 2:
                numStr = "Two(2)";
                break;
            case 3:
                numStr = "Three(3)";
                break;
            case 4:
                numStr = "Four(4)";
                break;
            case 5:
                numStr = "Five(5)";
                break;
            case 6:
                numStr = "Six(6)";
                break;
            case 7:
                numStr = "Seven(7)";
                break;
            case 8:
                numStr = "Eight(8)";
                break;
            case 9:
                numStr = "Nine(9)";
                break;
            case 10:
                numStr = "Ten(10)";
                break;
            case 11:
                numStr = "Jack(J)";
                break;
            case 12:
                numStr = "Queen(Q)";
                break;
            case 13:
                numStr = "King(K)";
                break;
            default:
                break;
        }

        switch (mySuit.toString()) {
            case "Hearts": 
                suitStr = "\u2764";
                break;
            case "Diamonds":
                suitStr = "\u2666";
                break;
            case "Clubs": 
                suitStr = "\u2663";
                break;
            case "Spades":
                suitStr = "\u2660";
                break;
            default:
                break;
        }

        return numStr + suitStr;
    }

}