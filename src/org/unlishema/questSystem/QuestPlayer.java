package org.unlishema.questSystem;

/**
 * The Quest Player is a interface you can implement into your player class and
 * use your data to setup the quest system properly.
 * 
 * @author Unlishema
 *
 */
public interface QuestPlayer {

	/**
	 * Get the ID of the player that implements this QuestPlayer
	 * 
	 * @return The ID if the player
	 */
	public abstract int getPlayerID();

	/**
	 * In this function you will be given one quest's data at a time to save into
	 * your database, the id of each quest will be used for loading it back in
	 * 
	 * @param questData The QuestData that you need to save to your database
	 */
	public abstract void saveQuestData(final QuestData questData);

	/**
	 * This function is made to load your quest data into the system, it loads 1
	 * quest at a time, giving you the id for reference of which quest it wants.<br>
	 * <br>
	 * You are creating a temporary package to hold the data until the Quest System
	 * uses it to updates its internal data
	 * 
	 * @param questID The ID of the Quest the Quest System wants you to load
	 * @return QuestData A Small Package that keeps track of your personal quest
	 *         data
	 */
	public abstract QuestData loadQuestData(final int questID);
}
