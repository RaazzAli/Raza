package PJ3;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *      http://www.freeslots.com/poker.htm
 *
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each playerHand. 
 * The player is dealt one five-card poker playerHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. Jacks or Better: a pair pays out only if the cards in the pair are Jacks, 
 * 	Queens, Kings, or Aces. Lower pairs do not pay out. 
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the video poker game class.
 * It uses OneDeck and Card objects to implement video poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */



public class VideoPoker {

    // default constant values
	private Scanner scanner;
    private static final int defaultBalance=100;
    private static final int numberCards=5;

    // default constant payout value and playerHand types
    private static final int[]    winningMultipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] winningHands={ 
	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // use one deck of cards
    private final OneDeck thisDeck;

    // holding current player 5-card hand, balance, bet    
    private List<Card> playerHand;
    private int playerBalance;
    private int playerBet;

    /** default constructor, set balance = defaultBalance */
    public VideoPoker()
    {
	this(defaultBalance);
    }

    /** constructor, set given balance */
    public VideoPoker(int balance)
    {
	this.playerBalance= balance;
        thisDeck = new OneDeck();
    }

    /** This display the payout table based on winningMultipliers and 
      * winningHands arrays */
    private void showPayoutTable()
    { 
	System.out.println("\n\n");
	System.out.println("Payout Table   	      Multiplier   ");
	System.out.println("=======================================");
	int size = winningMultipliers.length;
	for (int i=size-1; i >= 0; i--) {
		System.out.println(winningHands[i]+"\t|\t"+winningMultipliers[i]);
	}
	System.out.println("\n\n");
    }
    
    boolean showPayoutTable = true; //Shows the payout table

    /** Check current playerHand using winningMultipliers and winningHands arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {
	// implement this method
    	if(RoyalFlush()) {
    		System.out.println("Royal Flush!");
    		playerBalance += winningMultipliers[8] * playerBet;
    	}
    	else if (StraightFlush()) {
    		System.out.println("Straight Flush!");
    		playerBalance += winningMultipliers[7] * playerBet;
    	}
    	else if (FourOfAKind()) {
    		System.out.println("Four of a Kind!");
    		playerBalance += winningMultipliers[6] * playerBet;
    	}
    	else if (FullHouse()) {
    		System.out.println("Full House!");
    		playerBalance += winningMultipliers[5] * playerBet;
    	}
    	else if (Flush()) {
    		System.out.println("Flush!");
    		playerBalance += winningMultipliers[4] * playerBet;
    	}
    	else if (Straight()) {
    		System.out.println("Straight!");
    		playerBalance += winningMultipliers[3] * playerBet;
    	}
    	else if (ThreeOfAKind()) {
    		System.out.println("Three of a kind!");
    		playerBalance += winningMultipliers[2] * playerBet;
    	}
    	else if (TwoPair()) {
    		System.out.println("Two Pair!");
    		playerBalance += winningMultipliers[1] * playerBet;
    	}
    	else if (RoyalPair()) {
    		System.out.println("Royal Pair!");
    		playerBalance += winningMultipliers[0] * playerBet;
    	}
    	else
    		System.out.println("You lost :(");
    }
    

    /*************************************************
     *   add additional private methods here ....
     *
     *************************************************/
  
    private boolean RoyalFlush() {
    	boolean royalflush = true;
    	int firstCardSuit = playerHand.get(0).getSuit();
    	List<Integer> royalFlushRankList = Arrays.asList(1,10,11,12,13);
    	
    	for(Card thiscard : playerHand) {
    		if((thiscard.getSuit()!= firstCardSuit || !royalFlushRankList.contains(thiscard.getRank())))
    			royalflush = false;
    		}
    	return royalflush;    	
    }
    
    
    private boolean StraightFlush() {
    	boolean straightflush = true;
    	int firstCardSuit = playerHand.get(0).getSuit();
    	List<Integer> CardRankings = new ArrayList<>();
    	
    	for (Card thiscard : playerHand) {
    		CardRankings.add(thiscard.getRank());
    	}
    	
    	Collections.sort(CardRankings);
    	
    	for(Card thiscard : playerHand) {
    		if(thiscard.getSuit()!= firstCardSuit)
    			straightflush = false;
    	}
    	for(int i=0; i<4; i++) {
    		if(!(CardRankings.get(i)== (CardRankings.get(i+1)-1)))
    			straightflush = false;
    	}
    	return straightflush;
    }
    
    private boolean FourOfAKind() {
    	boolean fourofakind = true;
    	List<Integer> CardRankings = new ArrayList<>();
    	for (Card thiscard : playerHand) {
    		CardRankings.add(thiscard.getRank());
    	}
    	Collections.sort(CardRankings);
    	
    	int commonHand = CardRankings.get(2);
    	int counter = 0;
    	for(int i =0; i<4; i++) {
    		if(CardRankings.get(i)== commonHand) {
    			counter++;
    		}
    	}
    	if(counter !=4)
    		fourofakind = false;
    	return fourofakind;  
    }
    
    private boolean FullHouse() {
    	boolean fullhouse = false;
    	List<Integer> CardRankings = new ArrayList<>();
    	for(Card thiscard : playerHand) {
    		CardRankings.add(thiscard.getRank());
    	}
    	Collections.sort(CardRankings);
    	
    	int Value1 = CardRankings.get(1);
        int Value2 = CardRankings.get(3);
        //Split a person's hand to find out whether or not it's a full house
        
        int counter = 0;
        int counter2 = 0;
        for(int i=0; i<5; i++) {
        	if(CardRankings.get(i)== Value1) {
        		counter ++;
        	}
        	else if (CardRankings.get(i)== Value2) {
        		counter2 ++;
        	}
        }
        if((counter == 3 && counter2 == 2)|| (counter ==2 &&counter2 ==3)) {
        	return true;
        }
        return fullhouse;     
    }
    
    private boolean Flush() {
    	int FirstCard = playerHand.get(0).getSuit();
    	for(Card thiscard : playerHand) {
    		if(thiscard.getSuit()!=FirstCard)
    			return false;
    	}
    	return true;
    }
    
    private boolean Straight() {
    	boolean straight = false;
    	int FirstCard = playerHand.get(0).getSuit();
    	List<Integer> CardRankings = new ArrayList<>();
    	for(Card thiscard : playerHand) {
    		CardRankings.add(thiscard.getRank());
    	}
    	Collections.sort(CardRankings);
    	for(Card thiscard : playerHand) {
    		if(thiscard.getSuit()!= FirstCard) {
    			straight = true;
    			break;
    		}
    	}
    	for (int i=0; i<4; i++) {
    		if(!(CardRankings.get(i)==(CardRankings.get(i+1)-1)))
    			return false;
    	}
    	return straight;
    	//tests to see whether or not the cards match, and if they have a different suit
    }
    
    private boolean ThreeOfAKind() {
    	boolean threeofakind = true;
    	List<Integer> CardRankings = new ArrayList<>();
    	for (Card thiscard : playerHand) {
    		CardRankings.add(thiscard.getRank());
    	}
    	Collections.sort(CardRankings);
   //If there's three of a kind the rank has to be in the middle
    	int Value = CardRankings.get(2);
    	int counter = 0;
    	for (int i=0; i<4; i++) {
    		if(CardRankings.get(i)== Value) {
    			counter ++;
    		}
    	}
    	if(!(counter==3))
    		return false;
    	return true;
    }
    
    private boolean TwoPair() {
    	boolean twopair = false;
    	List <Integer> CardRankings = new ArrayList<>();
    	for (Card thiscard : playerHand) {
    		CardRankings.add(thiscard.getRank());
    	}
    	Collections.sort(CardRankings);
    	int counter = 0;
    	for(int i=0; i<4; i++) {
    		if(CardRankings.get(i)==CardRankings.get(i+1)) {
    			counter ++;
    		}
    	}
    	if(counter ==2)
    		twopair = true;
    	return twopair;
    }
    
    private boolean RoyalPair() {
    	boolean royalpair = false;
    	List<Integer> CardRankings = new ArrayList<>();
    	List<Integer> RoyalRankings = Arrays.asList(1,11,12,13);
    	for (Card thiscard : playerHand) {
    		CardRankings.add(thiscard.getRank());
    	}
    	Collections.sort(CardRankings);
    	int counter = 0;
    	for(int i=0; i<4; i++) {
    		if(CardRankings.get(i)==CardRankings.get(i+1)&& RoyalRankings.contains(CardRankings.get(i))) {
    			counter++;
    		}
    	}
    	if (counter != 0 && counter != 2) {
    		royalpair = true;
    	}
    	return royalpair;
    }
    
    private void giveBalance() {
    	System.out.println("Balance: $"+ playerBalance);
    }
    
    private void getBet() {
    	System.out.print("Enter bet:");
    	try {
    		Scanner in = new Scanner(System.in);
    		playerBet = scanner.nextInt();
    		if(playerBet>playerBalance) {
    			System.out.println("Bet is larger than balance, please try again");
    			getBet();
    		}
    	}
    	catch(InputMismatchException E) {
    		System.out.println("Invalid Entry, please try again");
    		getBet();
    	}
    }
    
    private void updateBalance() {
    	playerBalance -= playerBet;
    }
    
    private void giveCards() {
    	try {
    		playerHand = thisDeck.deal(numberCards);
    		System.out.println(playerHand.toString());
    	}
    	catch(PlayingCardException E) {
    		System.out.println("PlayingCardException:" + E.getMessage());
    	}
    }
    
    private void changeCards() {
    	System.out.println("Enter positions of cards to keep (For ex. 1,2,3):");
    	scanner = new Scanner (System.in);
    	String PositionInput = scanner.nextLine();
    	
    	//Put positions input into an array and split
    	String[] Positions = PositionInput.split(" ");
    	if(PositionInput.isEmpty()) {
    		return;
    	}
    	try {
    		for(int i=0; i<Positions.length; i++) {
    			int position = Integer.parseInt(Positions[i]) - 1;
    			playerHand.set(position, thisDeck.deal(1).get(0));
    		}
    		System.out.println(playerHand.toString());
    	}
    	catch(Exception E) {
    		System.out.println("Please input integers from 1-5 only! Try again");
    		changeCards();
    	}
    }
    
    private void exitGame() {
    	System.out.println("Thanks for playing!");
    	System.exit(0);
    }
    
    private void PlayoutTable() {
    	System.out.println("\nWant to see payout table (y or n)");
    	String input = scanner.nextLine();
    	if(input.equals("n")) {
    		showPayoutTable = false;
    		System.out.println("------------------------");
    	}
    	else if(input.equals("y"))
    		showPayoutTable = true;
    	else if (input.isEmpty()) {
    		PlayoutTable();
    	}
    	else {
    		System.out.println("Invalid entry. Please try again");
    		PlayoutTable();
    	}
    }
    
    private void newGame() {
    	System.out.println("Play again? (y or n)");
    	scanner = new Scanner (System.in);
    	String input = scanner.nextLine();
    	if(input.equals("y")){
    		PlayoutTable();
    		play();
    	}
    	else if (input.equals("n")) {
    		exitGame();
    	}
    	else if(input.isEmpty()) {
    		newGame();
    	}
    	else {
    		System.out.println("Invalid entry. Please try again");
    		PlayoutTable();
    	}
    }
    	
    

    public void play() 
    {
    /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for positions of cards to keep  
     *          get positions in one input line
     *		update cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */

	// implement this method
    	if(showPayoutTable) {
    		showPayoutTable();
    	}
    	giveBalance();
    	getBet();
    	updateBalance();
    	thisDeck.reset();
    	thisDeck.shuffle();
    	changeCards();
    	changeCards();
    	checkHands();
    	giveBalance();
    	if(playerBalance == 0) {
    		exitGame();
    	}
    	else {
    		newGame();
    		play();
    	}
    }


    /*************************************************
     *   do not modify methods below
     *   methods are used for testing your program.
     *
     *************************************************/

    /** testCheckHands is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */ 
    public void testCheckHands()
    {
      	try {
    		playerHand = new ArrayList<Card>();

		// set Royal Flush
		playerHand.add(new Card(1,4));
		playerHand.add(new Card(10,4));
		playerHand.add(new Card(12,4));
		playerHand.add(new Card(11,4));
		playerHand.add(new Card(13,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight Flush
		playerHand.set(0,new Card(9,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight
		playerHand.set(4, new Card(8,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Flush 
		playerHand.set(4, new Card(5,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

		// set Four of a Kind
		playerHand.clear();
		playerHand.add(new Card(8,4));
		playerHand.add(new Card(8,1));
		playerHand.add(new Card(12,4));
		playerHand.add(new Card(8,2));
		playerHand.add(new Card(8,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Three of a Kind
		playerHand.set(4, new Card(11,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Full House
		playerHand.set(2, new Card(11,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Two Pairs
		playerHand.set(1, new Card(9,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Royal Pair
		playerHand.set(0, new Card(3,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// non Royal Pair
		playerHand.set(2, new Card(3,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		System.out.println(e.getMessage());
      	}
    }

    /** testOneDeck() is used to test OneDeck class  
     *  testOneDeck() should execute OneDeck's main()
     */ 
    public void testOneDeck()
    {
    	OneDeck tmp = new OneDeck();
        tmp.main(null);
    }

    /* Quick testCheckHands() */
    public static void main(String args[]) 
    {
	VideoPoker pokergame = new VideoPoker();
	pokergame.testCheckHands();
    }
}
