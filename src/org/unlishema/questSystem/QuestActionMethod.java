package org.unlishema.questSystem;

/**
 * A Quest Action Method is used for programming the Quest System to interact
 * with
 * your game without any knowledge of how your game works. The Quest System
 * follows a simple rule and action state system or step in this case. A Rule is
 * used to check if we should goto the next step in the quest. An Action is Used
 * to execute code without a check when the Step is Stepped into.<br>
 * <br>
 * More information will be added in the near future

 * @author Unlishema
 */
public abstract class QuestActionMethod {
	public final String name;
	public final int argCount;

	/**
	 * A Quest Action Method is used for programming the Quest System to interact
	 * with
	 * your game without any knowledge of how your game works. The Quest System
	 * follows a simple rule and action state system or step in this case. A Rule is
	 * used to check if we should goto the next step in the quest. An Action is Used
	 * to execute code without a check when the Step is Stepped into.
	 * 
	 * @param name     Name the Method to use it when writing your quest script
	 * @param argCount Supply a minimum number of arguments you want in the method
	 */
	public QuestActionMethod(final String name, final int argCount) {
		this.name = name;
		this.argCount = argCount;
	}

	/**
	 * This is the method you will use to execute an action in your game when the
	 * Quest System has moved to the next step. You can create any kind of execution
	 * here you want here it could even be 10k lines of code but remember the bigger
	 * the execution is the longer it will take to process it. The quest system has
	 * to check every quest's current rules for every player that is registered to
	 * the Quest System. Then when a Rule checks to be true the next Step is
	 * executed and if it takes too long to finish it could bog down the Quest
	 * System.<br>
	 * <br>
	 * Example Method:<br>
	 * 
	 * <pre>
	 * questSystem.registerActionMethod(new QuestActionMethod("GivePlayerItem", 2) {
	 * 	&#64;Override
	 * 	public boolean execute(final QuestData qd, final QuestPlayer player, final Object... args) {
	 * 		final int itemID = args[0];
	 * 		final int itemCount = args[1];
	 * 		((Player) player).inventory.giveItem(itemID, itemCount);
	 * 	}
	 * });
	 * </pre>
	 * 
	 * @param questData Direct access to the Quest's Data that executed the check
	 * @param player    The Player that is executing the command
	 * @param args      The Arguments you have added in the Quest's File
	 */
	public abstract void execute(final QuestData questData, final QuestPlayer player, final Object... args);
}
