package org.unlishema.questSystem;

/**
 * The Quest Trigger Defines how you want the quest to be started. Default is
 * MANUAL.
 * 
 * @author Unlishema
 *
 */
public enum QuestTrigger {
	/**
	 * The Quest Begins the moment you register the player
	 */
	AUTO,
	/**
	 * The Quest System will watch a Rule you define until it is true and then it
	 * will start the quest
	 */
	RULE,
	/**
	 * The Quest System will not do anything until you trigger the quest yourself
	 */
	MANUAL;
}
