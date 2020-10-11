package org.unlishema.questSystem;

/**
 * A Quest Action is just a Wrapper to keep the method and arguments together
 * until we are ready to execute them, then we can just use this classes execute
 * function to pass everything on.
 * 
 * @author Unlishema
 *
 */
public class QuestAction {
	protected final QuestActionMethod method;
	protected final Object[] args;

	/**
	 * A Quest Action is just a Wrapper to keep the method and arguments together
	 * until we are ready to execute them, then we can just use this classes execute
	 * function to pass everything on.
	 * 
	 * @param method The Method that will be used for this Action
	 * @param args   The Arguments to pass to the method
	 */
	protected QuestAction(final QuestActionMethod method, final Object... args) {
		this.method = method;
		this.args = args;
	}

	/**
	 * Execute the method with all the arguments in this method and with the quests
	 * programmed arguments
	 * 
	 * @param questData The Quest Data that will be executing this code
	 * @param player    The Player that is executing the code
	 */
	protected void execute(final QuestData questData, final QuestPlayer player) {
		this.method.execute(questData, player, this.args);
	}
}
