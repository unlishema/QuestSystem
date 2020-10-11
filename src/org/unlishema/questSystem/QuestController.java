package org.unlishema.questSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Quest Controller is a Class you will never access from outside of the
 * Quest System. It is only used to make the internal structure of the Quest
 * System simpler and easier to understand.
 * 
 * @author Unlishema
 *
 */
public class QuestController {

	private final List<QuestData> questDataList = Collections.synchronizedList(new ArrayList<QuestData>());
	private final QuestSystem qs;
	private final QuestPlayer player;

	/**
	 * The Quest Controller is used to hold all the Data for a Specific Player. It
	 * is created when you register a Player with the Quest System so the Quest
	 * System can have all quests tracked for all players
	 * 
	 * @param qs     The Quest System itself because we need to access it methods
	 * @param player The Player to link this controller to
	 */
	protected QuestController(final QuestSystem qs, final QuestPlayer player) {
		this.qs = qs;
		this.player = player;
		this.reloadQuestData();
	}

	/**
	 * Get all the Active Quest Data for this
	 * 
	 * @return The Quest Data that is Active as an ArrayList
	 */
	protected ArrayList<QuestData> getActiveQuestDataList() {
		final ArrayList<QuestData> active = new ArrayList<QuestData>();
		for (final QuestData qd : this.getQuestDataList())
			if (qd.isTriggered())
				active.add(qd);
		return active;
	}

	/**
	 * Get all the Finished Quest Data for this
	 * 
	 * @return The Quest Data that is Finished as an ArrayList
	 */
	protected ArrayList<QuestData> getFinishedQuestDataList() {
		final ArrayList<QuestData> active = new ArrayList<QuestData>();
		for (final QuestData qd : this.getQuestDataList())
			if (qd.isCompleted())
				active.add(qd);
		return active;
	}

	/**
	 * Get all the Inactive Quest Data for this
	 * 
	 * @return The Quest Data that is Inactive as an ArrayList
	 */
	protected ArrayList<QuestData> getInactiveQuestDataList() {
		final ArrayList<QuestData> active = new ArrayList<QuestData>();
		for (final QuestData qd : this.getQuestDataList())
			if (!qd.isTriggered() && !qd.isCompleted())
				active.add(qd);
		return active;
	}

	/**
	 * Get a Specific Quest's Data for this controller
	 * 
	 * @param id The ID of the Quest you want
	 * @return The QuestData of the Quest or null if not found
	 */
	protected QuestData getQuestData(final int id) {
		for (final QuestData qd : this.getQuestDataList())
			if (qd.id == id)
				return qd;
		return null;
	}

	/**
	 * Get all the Quest Data for this
	 * 
	 * @return The Quest Data as an ArrayList
	 */
	protected List<QuestData> getQuestDataList() {
		return this.questDataList;
	}

	/**
	 * Update the QuestData we have stored with the Quest Data that has been given
	 * 
	 * @param questData
	 */
	protected void updateQuestData(final QuestData questData) {
		for (final QuestData data : this.questDataList) {
			if (data.id == questData.id) {
				data.triggered = questData.triggered;
				data.currentStep = questData.currentStep;
			}
		}
	}

	/**
	 * Reload the Quest Data back into the controller when we reload the quests
	 */
	protected void reloadQuestData() {
		if (this.questDataList.size() > 0)
			this.questDataList.clear();
		for (final Quest quest : this.qs.getQuests()) {
			final QuestData qd = new QuestData(quest.id);
			if (quest.getTriggerType() == QuestTrigger.AUTO) {
				qd.triggered = true;
				qd.currentStep = "Begin";
			}
			this.questDataList.add(qd);
		}
	}

	/**
	 * Get the Player registered to the controller
	 * 
	 * @return
	 */
	protected QuestPlayer getPlayer() {
		return this.player;
	}

	/**
	 * Run the Steps for the player registered to the controller
	 */
	protected void runSteps() {
		for (final QuestData data : this.questDataList)
			data.nextStep(this.qs, this.player);
	}
}
