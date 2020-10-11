package org.unlishema.questSystem;

/**
 * The Quest Type is designed for use with a party system you develop. It is
 * currently just an addon for you to be able to reference this info atm;
 * however, you could re-purpose it if needed
 * 
 * @author Unlishema
 *
 */
public enum QuestType {
	/**
	 * Single Player Quest no Partys Allowed
	 */
	SOLO,
	/**
	 * Party Quest so 1-2, 2-4, or even 1-(Your Defined Party Limit) its your choice
	 */
	PARTY,
	/**
	 * Raid Quest or Raid Boss???? so 1-4, 3-6, or (Your Defined Raid Min
	 * Limit)-(Your Defined Raid Max Limit) its your choice
	 */
	RAID;
}
