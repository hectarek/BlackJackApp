import java.util.Scanner;

public class GameRunner {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Deck theDeck = new Deck(1, true);

        System.out.println("What is your name?");
        String playerName = sc.nextLine();
        Player me = new Player(playerName);
        Player dealer = new Player("Dealer");

        boolean playGame = false;
        System.out.println("Do you want to play a game of BlackJack? (y/n)");
        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            playGame = true;
        }

        while ( playGame && me.getInitalAmount() >= 5) {
            me.askPlayersBet();

            me.addCard(theDeck.dealNextCard());
            dealer.addCard(theDeck.dealNextCard());
            me.addCard(theDeck.dealNextCard());
            dealer.addCard(theDeck.dealNextCard());

            System.out.println("Cards are dealt!\n");
            me.printHand(true);
            System.out.println("");
            dealer.printHand(false);
            System.out.println("\nYour card sum is: " + me.getHandSum());
            System.out.println("\n");

            boolean meDone = false;
            boolean dealerDone = false;
            String ans;

            while (!meDone || !dealerDone) {
                if (!meDone) {
                    System.out.println("Hit or Stay? (Enter H or S)");
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
                if (!dealerDone) {
                    if (dealer.getHandSum() < 17) {
                        System.out.println("The Dealer hits\n");
                        dealerDone = !dealer.addCard(theDeck.dealNextCard());
                        dealer.printHand(false);
                    } else {
                        System.out.println("The Dealer Stays\n");
                        dealerDone = true;
                    }
                }
                System.out.println("\nYour card sum is: " + me.getHandSum());
                System.out.println("");
            }

            me.printHand(true);
            System.out.println("");
            dealer.printHand(true);
            System.out.println("");

            int mySum = me.getHandSum();
            int dealerSum = dealer.getHandSum();
            System.out.println("\nYour final sum is: " + me.getHandSum());
            System.out.println("\nDealer's final sum is: " + dealer.getHandSum());
            System.out.println("");

            double betAmount = me.getPlayersBet();

            if (mySum > dealerSum && mySum <= 21 || dealerSum > 21) {
                System.out.println("You Win!");
                me.playerWins(betAmount);
                System.out.println("You won $" + betAmount);
                System.out.println("Your current pot is $" + me.getInitalAmount());
            } else {
                System.out.println("Dealer wins!");
                System.out.println("You lost $" + betAmount);
                System.out.println("Your current pot is $" + me.getInitalAmount());
            }

            me.emptyHand();
            dealer.emptyHand();

            if (me.getInitalAmount() < 5){
                System.out.println("\nLooks like your luck just ran out!\n");
                break;
            }

            System.out.println("\nDo you want to play again? (y/n)");
            String askAgain = sc.next();

            if (askAgain.equalsIgnoreCase("y")) {
                System.out.println("--------------");
                System.out.println(":Next Round:");
            } else {
                playGame = false;
            }

        }
        System.out.println("Thank you for coming!");
    }
}