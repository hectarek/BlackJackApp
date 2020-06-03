import java.util.Scanner;

public class GameRunner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Deck theDeck = new Deck(1, true);

        System.out.println("What is your name?");
        String playerName = sc.nextLine();
        Player me = new Player(playerName);
        
        Player dealer = new Player("Dealer");
        
        me.askPlayersBet();

        me.addCard(theDeck.dealNextCard());
        dealer.addCard(theDeck.dealNextCard());
        me.addCard(theDeck.dealNextCard());
        dealer.addCard(theDeck.dealNextCard());

        System.out.println("Cards are dealt!\n");
        me.printHand(true);
        dealer.printHand(false);
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
            System.out.println();
        }
        sc.close();

        me.printHand(true);
        dealer.printHand(true);

        int mySum = me.getHandSum();
        int dealerSum = dealer.getHandSum();

        double betAmount = me.getPlayersBet();

        if (mySum > dealerSum && mySum <= 21 || dealerSum > 21) {
            System.out.println("You Win!");
           
            me.playerWins(betAmount);
            System.out.println(betAmount);
        } else {
            System.out.println("Dealer wins!");
            me.playerLoses(betAmount);
            System.out.println(betAmount);
        }
    }
}