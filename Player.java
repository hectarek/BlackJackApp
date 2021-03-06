import java.util.Scanner;


//implementation of blackjack player
public class Player {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    // the player's name
    private String name;

    // The player's Inital betting amount
    private double playersAmount = 200.00;

    // The player's amount they bet
    private double playersBet;

    // the cards in the players hand
    private Card[] hand = new Card[10];// we assume that you will never get more than 10 cards

    // the number of cards in the player's current hand
    private int numCards;

    // Player constructor
    // takes param of aName, the name of the player
    public Player(String aName) {
        this.name = aName;

        // set a player's hand to empty
        this.emptyHand();// any cards from the previous game, we want to zero out
    }

    // Reset the player's hand to have no cards
    public void emptyHand() {
        for (int c = 0; c < 10; c++) {
            this.hand[c] = null;
        }
        this.numCards = 0; // number of cards is now set to zero
    }

    public String getName(){
        return this.name;
    }

    // Get players bet amount
    public double getPlayersAmount() {
        return this.playersAmount;
    }

    public double askPlayersBet() {
        System.out.println("Your current amount is " + ANSI_YELLOW + this.playersAmount + ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a bet amount in increments of 5. (Ex. 5, 10, 15, etc.)");
        double amount;
        do {
            amount = sc.nextDouble();
            if (amount <= this.playersAmount) {
                if (amount % 5 == 0 && amount >= 5) {
                    this.playersAmount -= amount;
                    System.out.println("Your bet amount is " + ANSI_YELLOW + amount + ANSI_RESET);
                    break;                
                } else {
                    System.out.println("Error, please enter an increment of 5");
                }
            } else {
                System.out.println("Sorry, you do not have enough funds, try a smaller amount.");
            }
        } while (1 != 0);

        this.playersBet = amount;

        return amount;
    }

    public double getPlayersBet() {
        return this.playersBet;
    }

    public void doublePlayersBet() {
        this.playersAmount -= this.playersBet;
        this.playersBet = this.playersBet *2;
    }

    // Add a card to the player's hand.
    // takes the param of aCard, the card to add to the hand
    // then returns whether the sum of the new hand is below or equal to 21
    public boolean addCard(Card aCard) {
        // print error if we already have max number of cards
        if (this.numCards == 10) {
            System.err.printf("%s's hand already has 10 cards; " + "cannot add another\n", this.name);
            System.exit(1);
        }
        // add a new card in next slot and increment number of cards counter
        this.hand[this.numCards] = aCard;
        this.numCards++;

        return (this.getHandSum() <= 21);// check if current hand is <=21 and return that value
    }

    // Get sume of the cards in the player's hand
    // return the sum
    public int getHandSum() {
        int handSum = 0;
        int cardNum;
        int numAces = 0;
        // calc each card's contribution to the hand sum
        for (int c = 0; c < this.numCards; c++) {
            // get the number of cards
            cardNum = this.hand[c].getMyNumber();

            if (cardNum == 1) {// 1=Ace
                numAces++;
                handSum += 11;
            } else if (cardNum > 10) {// face card
                handSum += 10;
            } else {
                handSum += cardNum;
            }
        }
        // If we have aces and our sum is > 21, set some/all of them to value 1 insteady
        while (handSum > 21 && numAces > 0) {// if hand sum is greater than 21 and number of aces is greater than 0,
                                             // we will subtract 10 from handsum or decrement the Aces
            handSum -= 10;
            numAces--;
        }
        return handSum;
    }

    // print out cards in player's hand
    // take param of showFirstCard which says if we want to print the card of the
    // first hand; useful when we may not want to see the cards of the dealer
    public void printHand(boolean showFirstCard) {
        System.out.println(ANSI_YELLOW + this.name + "'s" + ANSI_RESET + " cards: \n");
        for (int c = 0; c < this.numCards; c++) {
            if (c == 0 && !showFirstCard) {// if we are on the first card and don't want to print it
                System.out.println( ANSI_YELLOW + " [hidden]" + ANSI_RESET);
            } else {
                System.out.printf(" %s\n", this.hand[c].toString());
            }
        }
    }

    public int printHand(int card) {
        return this.hand[card].getMyNumber();
    }

    public Card printHandCard(int card) {
        return this.hand[card];
    }

    public void setHandCard(int card, Card cardToChange) {
        this.hand[card] = cardToChange;
    }

    public void playerWins(double bet) {
        this.playersAmount += bet;
    }
}