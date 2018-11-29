package fr.univ_nantes.alma.engine;

import java.util.UUID;
import java.util.Vector;

/**
 * The complete description of a player.
 *
 * @author Alexis Claveau, Martin Ars, Maud Van Dorssen, Alexis Loret
 * @version 0.0.1
 */
class Player {
	private UUID uuid;
	private String username;
	private Hero hero;
	private int manaPool;
	private int manaMaxTurn;
	private Card[] deck;
	private Vector<Card> hand;
	private Vector<Minion> board;

	/**
	 * Initialize the attributes of this class.
	 * @param uuid the id of the game
	 * @param username the username of the user
	 * @param hero the hero of the player
	 */
	
	Player(UUID uuid, String username, Hero hero, Card[] deck, Vector<Card> hand) {
		this.uuid = uuid;
		this.username = username;
		this.hero = hero;
		this.manaPool = 0;
		this.manaMaxTurn = 1;
		this.deck = deck; //TODO
		this.hand = hand;
		this.board = new Vector<Minion>();
	}

	/**
	 * Return the id of the game.
	 * @return the id of the game
	 */
	public UUID getUUID() {
		return this.uuid;
	}

	/**
	 * Return the username.
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Return the Hero the user has chosen.
	 * @return the hero
	 */
	public Hero getHero() {
		return this.hero;
	}

	/**
	 * Return the manaPool of the player.
	 * @return the manaPool of the player
	 */
	public int getManaPool() {
		return this.manaPool;
	}

	/**
	 * Return the ManaMaxTurn of the player.
	 * @return the ManaMaxTurn of the player
	 */
	public int getManaMaxTurn() {
		return this.manaMaxTurn;
	}

	/**
	 * Return the deck of the player.
	 * @return the deck of the player
	 */
	public Card[] getDeck() {
		return this.deck;
	}

	/**
	 * Return the hand of the player.
	 * @return he hand of the player
	 */
	public Vector<Card> getHand() {
		return this.hand;
	}

	/**
	 * Return the board of the player.
	 * @return the board of the player
	 */
	public Vector<Minion> getBoard() {
		return this.board;
	}

	/**
     * Set the mana pool at manaMaxTurn.
     */
    void setManaPoolForNewTurn() {
        this.manaPool = manaMaxTurn;
    }
    
    /**
     * Set the mana pool at manaMaxTurn.
     */
    void setManaPoolAfterPlay(int mana) {
    	this.manaPool -= mana;
    }

	/**
	 * Increase the mana pool each turn until it reaches 10
	 */
	void setManaMaxTurn() {
		this.manaMaxTurn++;
	}

	/**
	 * Add a card to the player's hand
	 * @param card the card to add
	 */
	void addCardToHand(Card card) {
		this.getHand().add(card);
	}

	/**
	 * Add a minion to the player's board
	 * @param minion the minion to add
	 */
	void addCardToBoard(Minion minion) {
		this.getBoard().add(minion);
	}

	/**
	 * Remove a card from the player's hand
	 * @param id the card to remove
	 */
	void removeCardFromHand(Card card) {
		this.getHand().remove(card);
	}

	/**
	 * Remove a minion from the player's board
	 * @param id the minion to remove
	 */
	void removeCardFromBoard(Minion minion) {
		this.getBoard().remove(minion);
	}
}
