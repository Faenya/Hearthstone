package fr.univ_nantes.alma.engine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The complete description of a card wich can be either a Minion or a Spell. 
 *
 * @author Alexis Claveau, Martin Ars, Maud Van Dorssen, Alexis Loret
 * @version 0.0.1
 */
@Entity
abstract class Card {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    protected int id;
    protected String type;
    protected String name;
    protected int manaCost;
    protected int damage;
    protected String target;
    protected String description;

    /**
     * Initialize the attributes of this class. 
     * @param id the id of the card
     * @param type There are five types : common, mage, paladin, warrior and invocation 
     * @param name the name of the card
     * @param manaCost the amount of mana the card costs
     * @param damage the amount of damage that the card can inflict
     * @param target a simple text with the target of the card
     * @param description the description of the card
     */
    Card(int id, String type, String name, int manaCost, int damage, String target, String description ) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.manaCost = manaCost;
        this.damage = damage;
        this.target = target;
        this.description = description;
    }
    
    Card() {}

    /**
     * Get the id of the card. 
     * @return the id of the card
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the type of the card. 
     * @return the type of the card
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the name of the card. 
     * @return the name of the card
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the manaCost of the card. 
     * @return the manaCost of the card
     */
    public int getManaCost() {
        return this.manaCost;
    }

    /**
     * Get the damage of the card. 
     * @return the damage of the card
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Get the target of the card. 
     * @return the target of the card
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * Get the description of the card. 
     * @return the description of the card
     */
    public String getDescription() {
        return this.description;
    }
}