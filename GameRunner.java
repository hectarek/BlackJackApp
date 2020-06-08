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

        while ( playGame && me.getPlayersAmount() >= 5 ) {
            
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
            boolean meSplitDone = false;
            boolean dealerDone = false;
            boolean roundSplit = false;
            String ans;
                
            // added check to not allow play to split if player bet was more than twice what they have in hanc
            if (playersFirstCard == playersSecondCard && me.getPlayersBet()*2 < me.getPlayersAmount()) {

                System.out.println(ANSI_CYAN + "Would you like to split your cards? (y/n)" + ANSI_RESET);
                String wantToSplit = sc.next();

                if(wantToSplit.equalsIgnoreCase("y")) {
                    roundSplit = true;
                    System.out.println("\nCards have been split!\n");
    
                    // Draw another card for the second pair.
                    meSplit.addCard(me.printHandCard(1));
                    meSplit.addCard(theDeck.dealNextCard());

                    // Change the value of the second card to another 
                    // card being drawn for the first pair.
                    me.setHandCard(1, theDeck.dealNextCard());

                    // Double the bet so that it reflect spliting and doubling bet amount.
                    me.doublePlayersBet();
                    System.out.println("Bet has been doubled!");
                    System.out.println("Amount wagered: " + me.getPlayersBet());
                    System.out.println("Current amount: " + me.getPlayersAmount());
                }

            }

            while (!meDone && !dealerDone && !meSplitDone) {
                if (!meDone) {
                    if (roundSplit) {
                        System.out.println(ANSI_CYAN + "First Hand: Hit or Stay? (Enter H or S)" + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_CYAN + "Hit or Stay? (Enter H or S)" + ANSI_RESET);
                    }
                    ans = sc.next();
                    System.out.println();

                    if (ans.compareToIgnoreCase("H") == 0) {
                        meDone = !me.addCard(theDeck.dealNextCard());
                        me.printHand(true);
                        System.out.println("");
                        System.out.println("\nYour hand sum is: " + me.getHandSum());
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
                        meSplitDone = !meSplit.addCard(theDeck.dealNextCard());
                        meSplit.printHand(true);
                        System.out.println("");
                        System.out.println("\nYour second hand sum is: " + me.getHandSum());
                        System.out.println("");
                    } else {
                        meSplitDone = true;
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

                // Halfing the bet amount to equal one of the two hands (was doubled above)
                double bets = betAmount/2;

                if (mySum > dealerSum && mySum <= 21 || dealerSum > 21) {
                    System.out.println(ANSI_GREEN + "Your First Hand Wins!");
                    me.playerWins(bets);
                    System.out.println("You won $" + bets + ANSI_RESET);
                    System.out.println("Your current pot is $" + me.getPlayersAmount());
                } else {
                    System.out.println(ANSI_RED + "Your first hand lost to the Dealer!");
                    System.out.println("You lost $" + bets + ANSI_RESET);
                    System.out.println("Your current pot is $" + me.getPlayersAmount());
                }

                if (mySplitSum > dealerSum && mySplitSum <= 21 || dealerSum > 21) {
                    System.out.println(ANSI_GREEN + "Your Second Hand Win!");
                    me.playerWins(bets);
                    System.out.println("You won $" + bets + ANSI_RESET);
                    System.out.println("Your current pot is $" + me.getPlayersAmount());
                } else {
                    System.out.println(ANSI_RED + "Your second hand lost to the Dealer!");
                    System.out.println("You lost $" + bets + ANSI_RESET);
                    System.out.println("Your current pot is $" + me.getPlayersAmount());
                }

                roundSplit = false;

            } else if (mySum > dealerSum && mySum <= 21 || dealerSum > 21) {
                System.out.println(ANSI_GREEN + "You Win!");
                me.playerWins(betAmount);
                System.out.println("You won $" + betAmount + ANSI_RESET);
                System.out.println("Your current pot is $" + me.getPlayersAmount());
            } else {
                System.out.println(ANSI_RED + "Dealer wins!");
                System.out.println("You lost $" + betAmount + ANSI_RESET);
                System.out.println("Your current pot is $" + me.getPlayersAmount());
            }

            me.emptyHand();
            meSplit.emptyHand();
            dealer.emptyHand();

            if (me.getPlayersAmount() < 5){
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