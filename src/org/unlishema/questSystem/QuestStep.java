package org.unlishema.questSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Quest Step holds all the Information for a Step in the Quest. Its also has
 * the ability to hold data specific to this step of the quest.
 * 
 * @author Unlishema
 *
 */
public class QuestStep {
	private final List<QuestAction> actions = Collections.synchronizedList(new ArrayList<QuestAction>());
	private final List<QuestRule> rules = Collections.synchronizedList(new ArrayList<QuestRule>());
	public final String name;

	private String info = "No Information Provided", infoE = "No Extended Information Provided";

	/**
	 * Construct a Quest Step to be used by the Quest System
	 * 
	 * @param name The name of the Step
	 */
	protected QuestStep(final String name) {
		this.name = name;
	}

	/**
	 * Get the Infomation for this Quest Step. This is usually a next step or short
	 * detail to show to the player.
	 * 
	 * @return The Info as a String
	 */
	public String getInfo() {
		return this.info;
	}

	/**
	 * Get the Extended Information for this Quest Step. This is usually a detailed
	 * section telling a lot more about the quest. You do NOT have to use this it is
	 * just extra ability for the System.
	 * 
	 * @return The Extended Info as a String
	 */
	public String getInfoExtended() {
		return this.infoE;
	}

	/**
	 * Add a Action into the Quest Step
	 * 
	 * @param method The method to execute when it comes time
	 * @param args   The Arguments to pass to the check
	 * @throws QuestSystemError
	 */
	protected void addAction(final QuestActionMethod method, final Object... args) throws QuestSystemError {
		if (method.argCount > args.length)
			throw new QuestSystemError(
					"The method \"" + method.name + "\" exspects a minimum of " + method.argCount + " args.");
		this.actions.add(new QuestAction(method, args));
	}

	/**
	 * Add A Rule into the Quest Step
	 * 
	 * @param method   The method to check when the time comes
	 * @param notFlag  If we are looking for the opposite of the output of not
	 * @param nextStep The next step to goto once check is successful
	 * @param args     The Arguments to pass to the check
	 * @throws QuestSystemError
	 */
	protected void addRule(final QuestRuleMethod method, final boolean notFlag, final String nextStep,
			final Object... args) throws QuestSystemError {
		if (method.argCount > args.length)
			throw new QuestSystemError(
					"The method \"" + method.name + "\" exspects a minimum of " + method.argCount + " args.");
		this.rules.add(new QuestRule(method, notFlag, nextStep, args));
	}

	/**
	 * Check all the Rules and determine if we need to change Steps based on them
	 * 
	 * @param questData The Quest Data that will be executing this code
	 * @param player    The Player that is executing the code
	 * @return The next Step to goto as a String, otherwise null
	 */
	protected String checkRules(final QuestData questData, final QuestPlayer player) {
		for (final QuestRule rule : this.rules)
			if (!rule.notFlag && rule.check(questData, player))
				return rule.nextStep;
			else if (rule.notFlag && !rule.check(questData, player))
				return rule.nextStep;
		return null;
	}

	/**
	 * Execute all the Actions of this step, this will be used once when we first
	 * get into the Step
	 * 
	 * @param questData The Quest Data that will be executing this code
	 * @param player    The Player that is executing the code
	 */
	protected void executeActions(final QuestData questData, final QuestPlayer player) {
		for (final QuestAction action : this.actions)
			action.execute(questData, player);
	}

	/**
	 * Set the Info of this Step in the quest<br>
	 * <br>
	 * Usually this would be the next step to display to the player
	 * 
	 * @param info The Information to set
	 */
	protected void setInfo(final String info) {
		this.info = info;
	}

	/**
	 * Set the Extended Info of this Step in the quest
	 * 
	 * @param infoE The Extended Information to set
	 */
	protected void setInfoExtended(final String infoE) {
		this.infoE = infoE;
	}
}
