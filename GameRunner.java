import java.util.Scanner;

public class GameRunner {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String YELLOW_BOLD = "\033[1;33m";




    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Deck theDeck = new Deck(1, true);

        System.out.print(ANSI_YELLOW + "Welcome " + ANSI_RESET);
        System.out.print(ANSI_RED + "to " + ANSI_RESET);
        System.out.print(ANSI_PURPLE + "George's " + ANSI_RESET);
        System.out.print(ANSI_GREEN + "Casino!\n " + ANSI_RESET);

        System.out.println(RED_BOLD_BRIGHT + "What is your name?" + ANSI_RESET);
        String playerName = sc.nextLine();
        Player me = new Player(ANSI_GREEN_BACKGROUND + playerName + ANSI_RESET);
        Player dealer = new Player("Dealer");

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
            String ans;

            while (!meDone || !dealerDone) {
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
                System.out.println(ANSI_GREEN + "You Win!");
                me.playerWins(betAmount);
                System.out.println("You won $" + betAmount + ANSI_RESET);
                System.out.println(ANSI_GREEN + ANSI_WHITE_BACKGROUND + "[̲̅$̲̅(̲̅ ͡° ͜ʖ ͡°̲̅)̲̅$̲̅]" + ANSI_RESET);
                System.out.println(ANSI_BLUE_BACKGROUND + YELLOW_BOLD +  "Your current pot is $"  + me.getInitalAmount()+ ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Dealer wins!");
                System.out.println("You lost $" + betAmount + ANSI_RESET);
                System.out.println(ANSI_BLUE_BACKGROUND + YELLOW_BOLD +  "Your current pot is $"  + me.getInitalAmount() + ANSI_RESET);
            }

            me.emptyHand();
            dealer.emptyHand();

            if (me.getInitalAmount() < 5){
                System.out.println(ANSI_RED + "\nLooks like your luck just ran out!\n"+ANSI_WHITE_BACKGROUND +"(ノಠ益ಠ)ノ彡┻━┻" + ANSI_RESET);
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

