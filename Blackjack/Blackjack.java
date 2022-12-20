import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/**
 * @author Dennis Shaykevich
 * 
 * This case, along with the associated Card.java, allows a user to play A simple version of Blackjack with the machine.
 *
 */
public class Blackjack {
	//This is the main method does most of the heavy lifting in terms of actually playing the game.
	public static void main(String args[]) throws InterruptedException{
		//This value stores the player's score. 
		int playerScore;
		//This value stores the dealer's (AKA, the opponent's) score.
	 	int dealerScore;
	 	//This double will store a randomly generated value for a later check.
	 	double random;
	 	//This double will store a threshold to compare the random value to.
	 	double test;
	 	//This value is used to store in the intensity of the game, which increases the sleep time when the dealer is playing.
	 	int tensity;
	 	//This boolean indicates if the game is still being played. It'll help with breaking while loops.
		boolean playing = true;
		//This simple i value will indicate the current card being played for the deck.
		int i;
		//This scanner will be used to read inputs
		Scanner scanner = new Scanner(System.in);
		//This string will be used to allow inputs
		String input = "";
		//This Card array will be the deck, it first uses generateDeck() to create the deck.
		Card[] deck = generateDeck();
		//This Card object will store the card currently being played.
		Card card;
		//This while loop will run through each round and will only close if the player wishes to stop playing.
		while(playing) {
			//At the beginning of each round, player and dealer score is set to 0.
			playerScore = 0;
			dealerScore = 0;
			//This string is used as pseudo-tertiary value storage, in this case whether the player wins, the dealer wins, or its a draw.
			String win = "";
			//Current card indicator set to 1 instead of typical 0 for easier math in the selection process.
			i = 1;
			//Tension is set to 0
			tensity = 0;
			//The deck is shuffled with the shuffleDeck() method.
			deck = shuffleDeck(deck);
			//Print out indicating the dealer's first card.
			System.out.println("Dealer's First Card:\n");
			//The zeroth card is pulled. All other card pulls use i and increment, which is why i was set to 1 earlier.
			card = deck[0];
			//Card info given.
			System.out.println(card.getFace() + " of " + card.getSuit());
			//Card value added to dealer score and displayed.
			dealerScore = card.primary();
			System.out.println("Dealer current score: " + dealerScore);
			//player's turn
			System.out.println("Player's Turn:" + "\n");
				//This boolean indicate turns
				boolean turn = true;
				//players turn continues until either player stands, player score is 21, or player score exceeds 21.
				while(turn) {
					//Pulls next card from top of deck and increments.
					card = deck[i];
					i++;
					//Card info given.
					System.out.println(card.getFace() + " of " + card.getSuit());
					//Checks current player score to give the correct card value to add to the score.
					//Only relevant if the card is an Ace but simpler to do it like this for all cards.
					if(playerScore < 11) {
						playerScore = playerScore + card.primary();
					} else {
						playerScore = playerScore + card.secondary();
					}
					//Prints player's current score along with dealer's to compare.
					System.out.print("Your current score: " + playerScore);
					if (playerScore != 21) {
						System.out.println("(vs dealer's " + dealerScore + ")");
					}
					//3 way check to see player's current score and perform the current action.
					//If the player exceeds a score of 21, the dealer is declared the winner and the turn automatically ends.
					if(playerScore > 21) {
						win = "dealer";
						turn = false;
						System.out.println();
					//If the player's score is less than 21, it gives the player the option to Hit or Stand.
					//If the player chooses to hit, the while loop loops again and another card is played.
					//If the player stands, their turn ends and it moves on to the dealer.
					} else if (playerScore < 21) {
						System.out.println();
						System.out.println("[H]it/[S]tand");
						input = scanner.nextLine();
						if (input.equals("S") || input.equals("s")) {
							turn = false;
						}
					//If the player's score is exactly 21, Blackjack is achieved and it automatically stands the player.
					} else {
						System.out.println(" Blackjack!");
						turn = false;
					}
				}
				//turn is set back to true for the dealer's turn
				turn = true;
				//card increment
				i++;
				//Checks to see if the dealer hasn't already won. In that case, indicate their turn.
				if (!win.equals("dealer")) {
					System.out.println("Dealer's turn:\n");
				}
				//Dealer's turn continues until either the dealer is already the winner, dealer exceeds 21, dealer beats player without going over 21, or dealer stands.
				while (turn && !win.equals("dealer")) {
					//This if else checks the dealer's and player's scores and sees what to do next.
					//If the dealer score exceeds the players without going over 21, the dealer wins and the turn ends.
					if (dealerScore > playerScore && dealerScore <= 21) {
						turn = false;
						win = "dealer";
						//One second delay for the player to process what happened.
						Thread.sleep(1000);
					//If the dealer's score exceeds 21, the player wins and turn ends.
					} else if(dealerScore > 21) {
						turn = false;
						win = "player";
						Thread.sleep(1000);
					//If both the player and the dealer have a score of 21, the game is a draw and the turn ends.
					} else if(dealerScore == 21 && playerScore == 21) {
						turn = false;
						win = "draw";
					//If not any of the above conditions are met, the round continues.
					} else {
						//This sequence checks if the player and dealer are tied with them above 11.
						//If they are, the gambling begins.
						if (dealerScore == playerScore && dealerScore > 11) {
							//This sets the random variable so that every point above 11 increases it by 0.1.
							//So, 12 gives it 0.1, 13 is 0.2, all the way to 20 with 0.9.
							//This is the odds the dealer wishes to draw the game.
							random = (1*(10-(21-dealerScore)))/10;
							//A random value between 0 and 1 is generated.
							test = Math.random();
							//If the test is less than the random value threshold, the dealer stands. Otherwise, they hit.
							if (random > test ) {
								System.out.println("Dealer Stands");
								turn = false;
								win = "draw";
								break;
							}
						}
						//If the score isn't tied or the dealer wishes to risk it, another card is played and i is incremented.
						card = deck[i];
						i++;
						//Card info given.
						System.out.println(card.getFace() + " of " + card.getSuit());
						//Checks current player score to give the correct card value to add to the score.
						if(dealerScore < 11) {
							dealerScore = dealerScore + card.primary();
						} else {
							dealerScore = dealerScore + card.secondary();
						}
						//Prints dealer's current score along with player's to compare.
						System.out.println("Dealer score: " + dealerScore + " (vs your " + playerScore + ")\n");
						//The sleep operation is called to make it seem like the dealer is thinking, as well as for the player to
						//comprehend the game. This sleep length increases as the score gets higher to add some suspense.
						//It's 2 seconds by default and increases an additional 0.5 seconds for every point over 11.
						if (dealerScore > 11) {
							tensity = (10 - (21 - dealerScore))* 500;
						}
						if (dealerScore < 21 && dealerScore < playerScore) {
							Thread.sleep(2000+tensity);
						}	
					}
				}
				//Once both turns have been played, as switch case is called to see who is the winner.
				switch(win) {
				//If the player wins, the player is congratulated.
				case "player":
					System.out.println("Player Wins!");
					break;
				//If the dealer wins, the dealer is congratulated.
				case "dealer":
					System.out.println("Dealer Wins!");
					break;
				//If it's a draw, a draw game is declared.
				case "draw":
					System.out.println("Draw Game!");
					break;
				//Default case in case issues arise.
				default:
					System.out.println("An error has occured within this game");
				}
		//A prompt asking the player if they want to play another round.
		//If yes, the while(playing) loop runs again, reseting all necessary values and re-shuffling the deck.
		//If no, the loop breaks, terminating the app.
		System.out.println("New Game? [Y]es/[N]o");
		input = scanner.nextLine();
		if (input.equals("N") || input.equals("n")){
			playing = false;
			scanner.close();
			} else {
				System.out.println("Reseting...\n\n\n\n\n");
			}
		}
		
		
		
	}
	/**
	 * This method generates a standard 52 card deck for the game to be played.
	 * 
	 * @return Card array with all 52 cards.
	 */
	public static Card[] generateDeck() {
		//Indicates the deck is begin generated.
		System.out.println("Generating Deck");
		//newDeck will be the Card array with the cards
		Card[] newDeck = new Card[52];
		//Initializing strings for face and suit
		String face = "";
		String suit = "";
		//2 for loops, the first for the faces and the second for the suits.
		for (int j = 0; j < 13; j++){
			for (int k = 0; k < 4; k++) {
				//Switch case checks the current k value and sets the suit appropriately.
				switch(k) {
				case 0:
					suit = "Hearts";
					break;
				case 1:
					suit = "Diamonds";
					break;
				case 2:
					suit = "Clubs";
					break;
				case 3:
					suit = "Spades";
					break;
				}
				//Switch case checks the current j value and sets the value appropriately.
				switch(j) {
				case 0:
					face = "King";
					break;
				case 1:
					face = "Ace";
					break;
				case 2:
					face = "Two";
					break;
				case 3:
					face = "Three";
					break;
				case 4:
					face = "Four";
					break;
				case 5:
					face = "Five";
					break;
				case 6:
					face = "Six";
					break;
				case 7:
					face = "Seven";
					break;
				case 8:
					face = "Eight";
					break;
				case 9:
					face = "Nine";
					break;
				case 10:
					face = "Ten";
					break;
				case 11:
					face = "Jack";
					break;
				case 12:
					face = "Queen";
					break;
				}
				//Checks the current value of the card.
				//This also sets each card in a different slot in the yet unshuffled deck.
				//If it's an ace, call the primary constructor with 11 as the primary value and 1 as the secondary value.
				if (j == 1) {
					newDeck[k*13+j] = new Card(face, suit, 11, 1);
				//If it's a face card, call the secondary constructor with 10 as the value.
				} else if (j == 0 || j > 10){
					newDeck[k*13+j] = new Card(face, suit, 10);
				//If it's a number card, call the secondary constructor with the number as the value.
				} else {
					newDeck[k*13+j] = new Card(face, suit, j);
				}
			}
		}
		//indicate the deck has finished generating.
		System.out.println("Finished Generating");
		//return the generated deck
		return newDeck;
	}
	/**
	 * This method shuffles the deck in play.
	 * 
	 * @param Card array deck that needs to be shuffled
	 * @return Card array deck that's shuffled.
	 */
	public static Card[] shuffleDeck(Card[] deck) {
		//Convert the array into a Card List
		List<Card> cardList = Arrays.asList(deck);
		//Use the shuffle feature on the List
		Collections.shuffle(cardList);
		//convert now shuffled list back into array.
		cardList.toArray(deck);
		//return the shuffled deck.
		return deck;
	}
}
