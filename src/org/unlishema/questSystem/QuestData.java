package org.unlishema.questSystem;

/**
 * This is a class that is used for a few different things but the most
 * important to you is loading and saving your players quest data to your
 * database. You can request all the data but only the Quest System can import
 * data to prevent misuse of the system.
 * 
 * @author Unlishema
 *
 */
public class QuestData {
	protected String currentStep = "Setup";
	protected boolean triggered = false;

	public final int id;

	/**
	 * This Constructor should only be used by the Quest System when pre-loading the
	 * QuestData for each player
	 * 
	 * @param id The ID of the quest
	 */
	protected QuestData(final int id) {
		this.id = id;
	}

	/**
	 * You Should ONLY be using this constructor for loading in your saved data into
	 * the quest system.
	 * 
	 * @param id        The ID of the quest you are loading
	 * @param step      The current Step the quest is on
	 * @param triggered If the Quest was Started already or not
	 */
	public QuestData(final int id, final String step, final boolean triggered) {
		this.id = id;
		this.currentStep = step;
		this.triggered = triggered;
	}

	/**
	 * Get the Name of the Current Step we are on
	 * 
	 * @return The name of the current step as a String
	 */
	public String getCurrentStep() {
		return this.currentStep;
	}

	/**
	 * Check and see if the quest has been completed
	 * 
	 * @return True if Quest is completed, otherwise false
	 */
	public boolean isCompleted() {
		return this.currentStep.toLowerCase().equals("Finished");
	}

	/**
	 * Check and see if the Quest has been Started by the Quest System
	 * 
	 * @return True if the Quest is Started, otherwise false
	 */
	public boolean isTriggered() {
		return this.triggered;
	}

	/**
	 * Manually trigger the quest to start it
	 */
	public void trigger() {
		this.triggered = true;
	}

	/**
	 * Perform the Next Step in all the different Quest Data we are holding
	 * 
	 * @param qs     The Quest System
	 * @param player The Player that is running the Next Step
	 */
	protected void nextStep(final QuestSystem qs, final QuestPlayer player) {
		final Quest quest = qs.getQuest(id);
		if (quest != null) {
			if (quest.getTriggerType() == QuestTrigger.RULE && !this.triggered) {
				final QuestRule triggerRule = quest.getTriggerRule();
				if ((triggerRule != null && !triggerRule.notFlag && triggerRule.check(this, player))
						|| (triggerRule != null && triggerRule.notFlag && !triggerRule.check(this, player))) {
					this.triggered = true;
					this.currentStep = "Begin";
				}
			} else if (this.triggered) {
				final QuestStep step = quest.getStep(this.currentStep);
				if (step != null && !step.name.equals("Finished")) {
					final String nextStep = step.checkRules(this, player);
					if (nextStep != null) {
						final QuestStep stepSetup = quest.getStep(nextStep);
						if (stepSetup != null) {
							this.currentStep = nextStep;
							stepSetup.executeActions(this, player);
						}
					}
				}
			}
		}
	}
}
