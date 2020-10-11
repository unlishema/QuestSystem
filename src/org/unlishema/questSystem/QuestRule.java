package org.unlishema.questSystem;

/**
 * A Quest Rule is just a Wrapper to keep the method and arguments together
 * until we are ready to check them, then we can just use this classes check
 * function to check and see if we should change the Step.
 * 
 * @author Unlishema
 *
 */
public class QuestRule {
	protected final QuestRuleMethod method;
	protected final boolean notFlag;
	protected final String nextStep;
	protected final Object[] args;

	/**
	 * A Quest Rule is just a Wrapper to keep the method and arguments together
	 * until we are ready to check them, then we can just use this classes check
	 * function to check and see if we should change the Step.
	 * 
	 * @param method   The Method that will be used for this Rule
	 * @param notFlag  The Flag that tells us we want to opposite result
	 * @param nextStep The next step to goto once check passes
	 * @param args     The Arguments to pass to the method
	 */
	protected QuestRule(final QuestRuleMethod method, final boolean notFlag, final String nextStep, final Object... args) {
		this.method = method;
		this.notFlag = notFlag;
		this.nextStep = nextStep;
		this.args = args;
	}

	/**
	 * Check and see if the Quest's current Step is ready to move to the Next Step
	 * 
	 * @param questData The Quest Data that will be checking this code
	 * @param player    The Player that is checking the code
	 * @return True if the check passed, otherwise false
	 */
	protected boolean check(final QuestData questData, final QuestPlayer player) {
		return this.method.check(questData, player, this.args);
	}
}