package org.unlishema.questSystem;

/**
 * A Quest Rule Method is used for programming the Quest System to interact with
 * your game without any knowledge of how your game works. The Quest System
 * follows a simple rule and action state system or step in this case. A Rule is
 * used to check if we should goto the next step in the quest. An Action is Used
 * to execute code without a check when the Step is Stepped into.<br>
 * <br>
 * More information will be added in the near future
 * 
 * @author Unlishema
 *
 */
public abstract class QuestRuleMethod {
	public final String name;
	public final int argCount;

	/**
	 * A Quest Rule Method is used for programming the Quest System to interact with
	 * your game without any knowledge of how your game works. The Quest System
	 * follows a simple rule and action state system or step in this case. A Rule is
	 * used to check if we should goto the next step in the quest. An Action is Used
	 * to execute code without a check when the Step is Stepped into.
	 * 
	 * @param name     Name the Method to use it when writing your quest script
	 * @param argCount Supply a minimum number of arguments you want in the method
	 */
	public QuestRuleMethod(final String name, final int argCount) {
		this.name = name;
		this.argCount = argCount;
	}

	/**
	 * This is the method you will use to check your game's status against the Quest
	 * System. You can create any kind of check you want here it could even be 10k
	 * lines of code but remember the bigger the check is the longer it will take to
	 * process it. The quest system has to check every quest's current rules for
	 * every player that is registered to the Quest System.<br>
	 * <br>
	 * Example Method:<br>
	 * 
	 * <pre>
	 * questSystem.registerRuleMethod(new QuestRuleMethod("DoesPlayerHaveItem", 1) {
	 * 	&#64;Override
	 * 	public boolean check(final QuestData qd, final QuestPlayer player, final Object... args) {
	 * 		final int itemID = args[0];
	 * 		final int itemCount = args.length &gt; 1 ? args[1] : 1;
	 * 		final Player gamePlayer = (Player) player;
	 * 		return gamePlayer.inventory.hasItem(itemID, itemCount);
	 * 	}
	 * });
	 * </pre>
	 * 
	 * @param questData Direct access to the Quest's Data that executed the check
	 * @param player    The Player that is executing the command
	 * @param args      The Arguments you have added in the Quest's File
	 * @return True if you want the Quest System to goto the Next Step otherwise
	 *         False will stay on current step
	 */
	public abstract boolean check(final QuestData questData, final QuestPlayer player, final Object... args);
}
