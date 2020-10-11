package org.unlishema.questSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Quest is used to hold the Data of the Quest itself and not the Data of each
 * Players Quest Information. For that you want QuestData.
 * 
 * @author Unlishema
 *
 */
public class Quest {
	public final int id;

	private final List<QuestStep> steps = Collections.synchronizedList(new ArrayList<QuestStep>());

	private String name = "You forgot name in setup";
	private QuestVersion version = new QuestVersion(1, 0);
	private QuestType type = QuestType.SOLO;
	private QuestTrigger trigger = QuestTrigger.MANUAL;
	private QuestRule triggerRule = null;

	/**
	 * Construct a new Quest with the ID you specify. Only the Quest System should
	 * be able to do this.
	 * 
	 * @param id The ID to use for the Quest
	 */
	protected Quest(final int id) {
		this.id = id;
	}

	/**
	 * Get the Name of the Quest
	 * 
	 * @return The name of the Quest as a String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get a specific Step of a Quest
	 * 
	 * @param name The name of the Step you want
	 * @return The Step of the quest as a QuestStep
	 */
	public QuestStep getStep(final String name) {
		for (final QuestStep step : this.getSteps())
			if (step.name.equals(name))
				return step;
		return null;
	}

	/**
	 * Get all the Steps of a Quest
	 * 
	 * @return The Steps of the Quest in an ArrayList
	 */
	public List<QuestStep> getSteps() {
		return this.steps;
	}

	/**
	 * Get the Trigger Type of the quest like AUTO, RULE, or MANUAL<br>
	 * <br>
	 * This will be used to control the starting of a quest like so:<br>
	 * &#183; AUTO - The Quest Begins the moment you register the player<br>
	 * &#183; RULE - The Quest System will watch a Rule you define until it is true
	 * and then it will start the quest<br>
	 * &#183; MANUAL - The Quest System will not do anything until you trigger the
	 * quest yourself<br>
	 * <br>
	 * You could extend its function and add something new also.
	 * 
	 * @return The Quest's Type as a QuestType
	 */
	public QuestTrigger getTriggerType() {
		return this.trigger;
	}

	/**
	 * Get the Type of the quest like SOLO, PARTY, and RAID<br>
	 * <br>
	 * You can use this to make your quest system more complex and dynamic
	 * 
	 * @return The Quest's Type as a QuestType
	 */
	public QuestType getType() {
		return this.type;
	}

	/**
	 * Get the Version of the Quest
	 * 
	 * @return The Version of quest as QuestVersion you can just use toString to get
	 *         a String Version
	 */
	public QuestVersion getVersion() {
		return this.version;
	}

	/**
	 * Add a Step into the Quest
	 * 
	 * @param step The Step to add into Quest
	 */
	protected void addStep(final QuestStep step) {
		if (!this.hasStep(step))
			this.steps.add(step);
	}

	/**
	 * Get the Trigger Rule for the Quest, only used if Trigger is set to RULE
	 * 
	 * @return The QuestRule that is set as the Trigger Rule
	 */
	protected QuestRule getTriggerRule() {
		return this.triggerRule;
	}

	/**
	 * Check to see if the quest has a specific step
	 * 
	 * @param name The Step name to look for
	 * @return True if found the Step, otherwise false
	 */
	protected boolean hasStep(final String name) {
		for (final QuestStep step : this.getSteps())
			if (step.name.equals(name))
				return true;
		return false;
	}

	/**
	 * Check to see if the quest has a specific step
	 * 
	 * @param step The Step to look for
	 * @return True if found the Step, otherwise false
	 */
	protected boolean hasStep(final QuestStep step) {
		return this.hasStep(step.name);
	}

	/**
	 * Remove the Step from the Quest.<br>
	 * <br>
	 * Do I even NEED this???
	 * 
	 * @param step
	 */
	protected void removeStep(final QuestStep step) {
		if (this.hasStep(step))
			this.steps.remove(step);
	}

	/**
	 * Set the Name of the Quest
	 * 
	 * @param name The name for the Quest as a String
	 */
	protected void setName(final String name) {
		this.name = name;
	}

	/**
	 * Set the Trigger Type for the Quest. AUTO, RULE, and MANUAL<br>
	 * <br>
	 * 
	 * @param trigger The Trigger Type from QuestTrigger
	 */
	protected void setTrigger(final QuestTrigger trigger) {
		this.trigger = trigger;
	}

	/**
	 * Set the Trigger Rule of the Quest if the Trigger is set to RULE
	 * 
	 * @param triggerRule The Rule to trigger quest from the quest file
	 */
	protected void setTriggerRule(final QuestRule triggerRule) {
		this.triggerRule = triggerRule;
	}

	/**
	 * Set the Type of the Quest which is basically how many people can participate
	 * 
	 * @param type The Type of the Quest from QuestType
	 */
	protected void setType(final QuestType type) {
		this.type = type;
	}

	/**
	 * Set the Version of the Quest
	 * 
	 * @param version The version of the Quest like QuestVersion(major, minor)
	 */
	protected void setVersion(final QuestVersion version) {
		this.version = version;
	}

	/**
	 * Set the Version of the Quest
	 * 
	 * @param major The major int of the version
	 * @param minor The minor int of the version
	 */
	protected void setVersion(final int major, final int minor) {
		this.setVersion(new QuestVersion(major, minor));
	}
}
