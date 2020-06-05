import java.util.Scanner;

public class GameRunner {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLACK = "\u001B[30m";




    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Deck theDeck = new Deck(1, true);

        System.out.println("What is your name?");
        String playerName = sc.nextLine();
        Player me = new Player(playerName);
        Player dealer = new Player("Dealer");
        Player meSplit = new Player(me.getName()+ "'s Split");

        boolean playGame = false;
        System.out.println(ANSI_YELLOW + "Do you want to play a game of BlackJack? (y/n)" + ANSI_RESET);
        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            playGame = true;
        }

        while ( playGame && me.getInitalAmount() >= 5 ) {
            me.askPlayersBet();

            me.addCard(theDeck.dealNextCard());
            dealer.addCard(theDeck.dealNextCard());
            me.addCard(theDeck.dealNextCard());
            dealer.addCard(theDeck.dealNextCard());

            int playersFirstCard = me.printHand(0);
            int playersSecondCard = me.printHand(1);

            System.out.println(ANSI_YELLOW + "Cards are dealt!\n" + ANSI_RESET);
            me.printHand(true);
            System.out.println("");
            dealer.printHand(false);
            System.out.println("\nYour card sum is: " + me.getHandSum());
            System.out.println("\n");

            boolean meDone = false;
            boolean dealerDone = false;
            boolean roundSplit = false;
            String ans;

            if (playersFirstCard == playersSecondCard) {

                System.out.println(ANSI_CYAN + "Would you like to split your cards? (y/n)" + ANSI_RESET);
                String wantToSplit = sc.next();

                if(wantToSplit.equalsIgnoreCase("y")) {
                    roundSplit = true;
    
                    // Draw another card for the second pair.
                    meSplit.addCard(me.printHandCard(1));
                    meSplit.addCard(theDeck.dealNextCard());

                    // Change the value of the second card to another 
                    // card being drawn for the first pair.
                    me.setHandCard(1, theDeck.dealNextCard());
                }

            }

            while (!meDone || !dealerDone || roundSplit) {
                if (!meDone) {
                    System.out.println(ANSI_CYAN + "Hit or Stay? (Enter H or S)" + ANSI_RESET);
                    ans = sc.next();
                    System.out.println();

                    if (ans.compareToIgnoreCase("H") == 0) {
                        meDone = !me.addCard(theDeck.dealNextCard());
                        me.printHand(true);
                        System.out.println("");
                    } else {
                        meDone = true;
                    }
                }
                if (roundSplit) {
                    System.out.println(ANSI_CYAN + "Second Hand: Hit or Stay? (Enter H or S)" + ANSI_RESET);
                    ans = sc.next();
                    System.out.println();

                    if (ans.compareToIgnoreCase("H") == 0) {
                        roundSplit = meSplit.addCard(theDeck.dealNextCard());
                        meSplit.printHand(true);
                        System.out.println("");
                    } else {
                        roundSplit = false;
                    }
                }
 
                if (!dealerDone) {
                    if (dealer.getHandSum() < 17) {
                        System.out.println(ANSI_CYAN + "The Dealer hits\n" + ANSI_RESET);
                        dealerDone = !dealer.addCard(theDeck.dealNextCard());
                        dealer.printHand(false);
                    } else {
                        System.out.println(ANSI_CYAN + "The Dealer Stays\n"+ ANSI_RESET);
                        dealerDone = true;
                    }
                }
                System.out.println("\nYour card sum is: " + me.getHandSum());
                System.out.println("");
            }

            int mySum = me.getHandSum();
            int mySplitSum = meSplit.getHandSum();
            int dealerSum = dealer.getHandSum();

            if (roundSplit) {
                me.printHand(true);
                System.out.println("");
                meSplit.printHand(true);
                System.out.println("");
                dealer.printHand(true);
                System.out.println("");
    
                System.out.println("\nYour final sum for hand 1 is: " + me.getHandSum());
                System.out.println("\nYour final sum for hand 2 is: " + meSplit.getHandSum());
                System.out.println("\nDealer's final sum is: " + dealer.getHandSum());
                System.out.println("");

            } else {
                me.printHand(true);
                System.out.println("");
                dealer.printHand(true);
                System.out.println("");

                System.out.println("\nYour final sum is: " + me.getHandSum());
                System.out.println("\nDealer's final sum is: " + dealer.getHandSum());
                System.out.println("");
            }

            double betAmount = me.getPlayersBet();

            if (roundSplit) {

                if (mySum > dealerSum && mySum <= 21 || dealerSum > 21) {
                    System.out.println(ANSI_GREEN + "Your First Hand Wins!");
                    me.playerWins(betAmount);
                    System.out.println("You won $" + betAmount + ANSI_RESET);
                    System.out.println("Your current pot is $" + me.getInitalAmount());
                } else {
                    System.out.println(ANSI_RED + "Dealer wins!");
                    System.out.println("You lost $" + betAmount + ANSI_RESET);
                    System.out.println("Your current pot is $" + me.getInitalAmount());
                }

                if (mySplitSum > dealerSum && mySplitSum <= 21 || dealerSum > 21) {
                    System.out.println(ANSI_GREEN + "Your Second Hand Win!");
                    me.playerWins(betAmount);
                    System.out.println("You won $" + betAmount + ANSI_RESET);
                    System.out.println("Your current pot is $" + me.getInitalAmount());
                } else {
                    System.out.println(ANSI_RED + "Dealer wins!");
                    System.out.println("You lost $" + betAmount + ANSI_RESET);
                    System.out.println("Your current pot is $" + me.getInitalAmount());
                }

            } else if (mySum > dealerSum && mySum <= 21 || dealerSum > 21) {
                System.out.println(ANSI_GREEN + "You Win!");
                me.playerWins(betAmount);
                System.out.println("You won $" + betAmount + ANSI_RESET);
                System.out.println("Your current pot is $" + me.getInitalAmount());
            } else {
                System.out.println(ANSI_RED + "Dealer wins!");
                System.out.println("You lost $" + betAmount + ANSI_RESET);
                System.out.println("Your current pot is $" + me.getInitalAmount());
            }

            me.emptyHand();
            meSplit.emptyHand();
            dealer.emptyHand();

            if (me.getInitalAmount() < 5){
                System.out.println(ANSI_RED + "\nLooks like your luck just ran out!\n" + ANSI_RESET);
                break;
            }

            System.out.println(ANSI_CYAN + "\nDo you want to play again? (y/n)" + ANSI_RESET);
            String askAgain = sc.next();

            if (askAgain.equalsIgnoreCase("y")) {
                System.out.println("--------------");
                System.out.println(":Next Round:");
            } else {
                playGame = false;
            }

        }
        System.out.println(ANSI_YELLOW + "Thank you for coming!" + ANSI_RESET);
    }
}