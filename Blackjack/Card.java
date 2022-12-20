public class Card {
	private String face;	//The face value of the card
	private String suit;	//The suit of the card (doesn't impact the game, but assures the player that their all unique cards.
	private int primary;	//The value of the card if the score is 10 or less.
	private int secondary;	//The value of the card if the score is 11 or more.
	
	/**
	 * This is the primary constructor. It sets all the values in the Object class. 
	 * It is only directly called if the card is an ace.
	 */
	public Card(String face, String suit, int primary, int secondary) {
		this.face = face;
		this.suit = suit;
		this.primary = primary;
		this.secondary = secondary;
	}
	/**
	 * This is the secondary constructor, is only has 1 value for the card.
	 * It's called for any card that's not an ace. It calls the primary constructor with 
	 * the primary and secondary values being the same.
	 */
	public Card(String face, String suit, int primary) {
		this(face, suit, primary, primary);
	}
	//Get functions.
	public String getFace() {return face;}
	public String getSuit() {return suit;}
	public int primary() {return primary;}
	public int secondary() {return secondary;}
}
