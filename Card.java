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
        return this.myNumber;
    }
    public void setMyNumber(int myNumber) {
        this.myNumber = myNumber;
    }

    @Override 
    public String toString(){
        String numStr = "";

        switch(this.myNumber) {
            case 1 :
                numStr="Ace";
                break;
            case 2 :
                numStr="Two";
                break;
            case 3 :
                numStr="Three";
                break;
            case 4 :
                numStr="Four";
                break;
            case 5 :
                numStr="Five";
                break;
            case 6 :
                numStr="Six";
                break;
            case 7 :
                numStr="Seven";
                break;
            case 8 :
                numStr="Eight";
                break;
            case 9 :
                numStr="Nine";
                break;
            case 10 :
                numStr="Ten";
                break;
            case 11 :
                numStr="Jack";
                break;
            case 12 :
                numStr="Queen";
                break;
            case 13 :
                numStr="King";
                break;
            default : 
                break;
        }

        return numStr + mySuit.toString();
    }

    


}