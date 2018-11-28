package fr.univ_nantes.alma.engine;

import java.util.UUID;
import java.util.Vector;


/**
 * Manage all the actions that can happen in the game.
 *
 * @author Alexis Claveau, Martin Ars, Maud Van Dorssen, Alexis Loret
 * @version 0.0.1
 */
public class Game {
	private UUID idGame;
	private Player[] players;
	private int idCurrentPlayer;

	/**
	 * Initialize the attributes of this class.
	 * @param idGame the id of the game
	 * @param player1 the first player of the game
	 * @param player2 the second player of the game
	 */
	public Game(UUID idGame, Player player1, Player player2) {
		this.idGame = idGame;
		this.players = new Player[2];
		this.players[0] = player1;
		this.players[1] = player2;
		this.idCurrentPlayer = 0;
	}

	/**
	 * Get the id of the game
	 * @return the id of the game
	 */
	public UUID getIdGame() {
		return this.idGame;
	}
	
	/**
	 * Get an array containing the two players
	 * @return the players
	 */
	Player[] getPlayers() {
		return this.players;
	}
	
	/**
	 * Get the index of the current player.
	 * @return the index of the current player
	 */
	int getIdCurrentPlayer() {
		return this.idCurrentPlayer;
	}

	/**
	 * Draw a card from the player's deck and place it into his hand.
	 * @throws EngineException
	 */
	void drawCard() throws EngineException {
		int random = (int)(this.players[this.idCurrentPlayer].getDeck().length * Math.random());
		
		if(Rule.checkHandSize(this.players[this.idCurrentPlayer].getHand()))
			this.players[this.idCurrentPlayer].addCardToHand(this.players[this.idCurrentPlayer].getDeck()[random]);
		else 
			throw new EngineException("blabla");
	}
	
	/**
	 * Attack a target with a attacker given in parameter
	 * @param idAttack the id of the attacker
	 * @param idTarget the id of the target
	 * @throws EngineException 
	 */
	void attack(int idAttack, int idTarget) throws EngineException {
		Minion minion = this.players[this.idCurrentPlayer].getBoard().get(idAttack);
		
		//Check if the minion already attacked or not
		if(minion != null && !Rule.checkMinionAttacked(minion)) {
			int damage = minion.getDamage();
			
			if(idTarget == 0) { // If it's the hero
				Hero hero = this.players[this.idCurrentPlayer ^ 1].getHero();
				hero.receiveDamage(damage); //attack the hero
				minion.setAttacked(true);
				if(!Rule.checkAlive(hero.getHealthPoints())) {
					//end game
				}
			} else {
				Minion victim = this.players[this.idCurrentPlayer ^ 1].getBoard().get(idTarget);
				if(victim != null) {
					minion.receiveDamage(victim.getDamage()); // minion takes victim's damage
					victim.receiveDamage(damage); //attack the minion
					minion.setAttacked(true);
					if(!Rule.checkAlive(minion.getHealthPoints())) {
						this.players[this.idCurrentPlayer].getBoard().remove(idAttack);
					}
					if(!Rule.checkAlive(victim.getHealthPoints())) {
						this.players[this.idCurrentPlayer ^ 1].getBoard().remove(idTarget);
					}
				} else {
					throw new EngineException("blabla");
				}
			}
		} else {
			throw new EngineException("blabla");
		}
	}

	/**
	 * End the turn and switch the current player.
	 */
	void endTurn() {
		this.idCurrentPlayer ^= 1;
	}

	/**
     * Play the card with the specified id.
     * @param idCard the id of the card
     * @throws EngineException 
     */
	void playCard(int idCard) throws EngineException {
        Card card = this.players[this.idCurrentPlayer].getHand().get(idCard); // Create the card according to the id given on parameters
        
        if(Rule.checkManaPool(this.players[this.idCurrentPlayer].getManaPool(), card.getManaCost())) { // If the player have enought mana
        	if(card instanceof Minion) { // If it's a Minion
            	if(Rule.checkBoardSize(this.players[this.idCurrentPlayer].getBoard()))
            		this.players[this.idCurrentPlayer].addCardToBoard((Minion)this.players[this.idCurrentPlayer].getHand().get(idCard));
            	else
            		throw new EngineException("blabla");
            }
            else {
                // Cast spell
            }
            
            this.players[this.idCurrentPlayer].setManaPoolAfterPlay(card.getManaCost()); // Check manaCost
        } else {
        	throw new EngineException("blabla");
        }
        
    }
	
	void heroPower(Player player, int idTarget) throws EngineException {
		Hero hero = this.players[this.idCurrentPlayer].getHero();
		
		if (!hero.getHeroPowerUsed()) { // If the hero have use already use his power
			if (this.players[this.idCurrentPlayer].getManaPool() >= Rule.MANA_COST_HERO_POWER) {
				
				switch (hero.getType())
				{
				case "Warrior":
					hero.setArmorPoints(hero.getArmorPoints() + hero.getArmorBuff());
					hero.setHeroPowerUsed(true);
					this.players[this.idCurrentPlayer].setManaPoolAfterPlay(Rule.MANA_COST_HERO_POWER);
					break;
				case "Mage":
					if (idTarget == 0)
						player.getHero().receiveDamage(hero.getDamage());
					else
						player.getBoard().get(idTarget).receiveDamage(hero.getDamage());
					hero.setHeroPowerUsed(true);
					this.players[this.idCurrentPlayer].setManaPoolAfterPlay(Rule.MANA_COST_HERO_POWER);
					break;
				case "Paladin":
					if (Rule.checkBoardSize(this.players[this.idCurrentPlayer].getBoard())) {
						/*ArrayList<Minion> = Engine.retrieveMinion
						Minion minion = this.players[this.idCurrentPlayer].[9];
						this.players[this.idCurrentPlayer].addCardToBoard((minion) card);*/
						hero.setHeroPowerUsed(true);
						this.players[this.idCurrentPlayer].setManaPoolAfterPlay(Rule.MANA_COST_HERO_POWER);
					}
					break;
				}
			} else {
				throw new EngineException("Vous n'avez pas assez de mana!");
			}
		} else {
			throw new EngineException("Vous avez déjà utilisé votre pouvoir héroïque durant ce tour!");
		}
	}
}
