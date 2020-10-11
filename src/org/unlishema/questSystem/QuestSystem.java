package org.unlishema.questSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h2>Quest System</h2>
 * 
 * JAVADOC QuestSystem
 * 
 * FIXME Adjust README
 * 
 * @author Unlishema
 *
 */
public class QuestSystem {
	/**
	 * Quest Actions is used during the Loading of Quest Only
	 * 
	 * @author Unlishema
	 *
	 */
	private enum QuestActions {
		INFO, INFOE, ACTION, RULE, RULEH;
	}

	private final List<QuestController> questControllers = Collections
			.synchronizedList(new ArrayList<QuestController>());
	private final List<QuestActionMethod> actionMethods = Collections
			.synchronizedList(new ArrayList<QuestActionMethod>());
	private final List<QuestRuleMethod> ruleMethods = Collections.synchronizedList(new ArrayList<QuestRuleMethod>());
	private final List<Quest> quests = Collections.synchronizedList(new ArrayList<Quest>());
	private final Thread thread = new Thread() {

		@Override
		public void run() {
			while (running) {
				if (paused)
					continue;
				for (final QuestController controller : questControllers)
					controller.runSteps();
			}
		}
	};

	private boolean running = false, paused = false;
	private String dir = null;

	/**
	 * Default and ONLY Constructor of the Quest System
	 */
	public QuestSystem() {
		try {
			this.registerActionMethod(new QuestActionMethod("Finished", 0) {

				@Override
				public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
					qd.currentStep = "Finished";
					qd.triggered = false;
				}
			});
			this.registerActionMethod(new QuestActionMethod("Reset", 0) {

				@Override
				public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
					final Quest quest = getQuest(qd.id);
					qd.triggered = quest.getTriggerType() == QuestTrigger.AUTO;
					qd.currentStep = quest.getTriggerType() == QuestTrigger.AUTO ? "Begin" : "setup";
				}
			});
			this.registerRuleMethod(new QuestRuleMethod("Completed", 1) {

				@Override
				public boolean check(final QuestData qd, final QuestPlayer player, final Object... args) {
					final int questID = Integer.parseInt((String) args[0]);
					if (questID != qd.id) {
						final QuestData questData = getQuestDataForPlayer(player, questID);
						System.out.println(questData.currentStep + " the Quest");
						return questData.currentStep.toLowerCase().equals("finished");
					}
					return false;
				}
			});
			this.running = true;
			this.thread.start();
		} catch (final QuestSystemError e) {
			e.printStackTrace();
		}
	}

	/**
	 * De-register the Action Method from the Quest System. I don't see much use for
	 * this right now but I will add it just in case.
	 * 
	 * @param method The Action Method to register to the Quest System
	 */
	public void deregisterActionMethod(final QuestActionMethod method) {
		if (this.hasActionMethod(method))
			this.actionMethods.remove(method);
	}

	/**
	 * De-register the Rule Method from the Quest System. I don't see much use for
	 * this right now but I will add it just in case.
	 * 
	 * @param method The Rule Method to register to the Quest System
	 */
	public void deregisterRuleMethod(final QuestRuleMethod method) {
		if (this.hasRuleMethod(method))
			this.ruleMethods.remove(method);
	}

	/**
	 * Remove the Player from the Quest System. If a Player is not register then
	 * quest info cannot be requested or updated for that player.
	 * 
	 * @param player The Player you want to add to the update system
	 */
	public void deregisterPlayer(final QuestPlayer player) {
		if (this.hasRegisteredPlayer(player)) {
			final QuestController controller = this.getController(player);
			if (controller != null) {
				this.questControllers.remove(controller);
				for (final QuestData qd : controller.getQuestDataList())
					player.saveQuestData(qd);
			}
		}
	}

	/**
	 * Get the All the active Quest Data for a Specific Player
	 * 
	 * @param player Player that you want All the Quest Data for
	 * @return An ArryaList of QuestData for the Player, otherwise null
	 */
	public ArrayList<QuestData> getActiveQuestDataListForPlayer(final QuestPlayer player) {
		for (final QuestController controller : this.questControllers)
			if (controller.getPlayer().getPlayerID() == player.getPlayerID())
				return controller.getActiveQuestDataList();
		return null;
	}

	/**
	 * Get the All the Finished Quest Data for a Specific Player
	 * 
	 * @param player Player that you want All the Quest Data for
	 * @return An ArryaList of QuestData for the Player, otherwise null
	 */
	public ArrayList<QuestData> getFinishedQuestDataListForPlayer(final QuestPlayer player) {
		for (final QuestController controller : this.questControllers)
			if (controller.getPlayer().getPlayerID() == player.getPlayerID())
				return controller.getFinishedQuestDataList();
		return null;
	}

	/**
	 * Get the All the Inactive Quest Data for a Specific Player
	 * 
	 * @param player Player that you want All the Quest Data for
	 * @return An ArryaList of QuestData for the Player, otherwise null
	 */
	public ArrayList<QuestData> getInactiveQuestDataListForPlayer(final QuestPlayer player) {
		for (final QuestController controller : this.questControllers)
			if (controller.getPlayer().getPlayerID() == player.getPlayerID())
				return controller.getInactiveQuestDataList();
		return null;
	}

	/**
	 * Get a single quest's data for a Specific Player
	 * 
	 * @param player Player that you want the Quest Data for
	 * @param id     ID of the Quest you want the Data for
	 * @return The QuestData for the Player, otherwise null
	 */
	public QuestData getQuestDataForPlayer(final QuestPlayer player, final int id) {
		for (final QuestController controller : this.questControllers)
			if (controller.getPlayer().getPlayerID() == player.getPlayerID())
				return controller.getQuestData(id);
		return null;
	}

	/**
	 * Get the All the Quest Data for a Specific Player
	 * 
	 * @param player Player that you want All the Quest Data for
	 * @return An ArryaList of QuestData for the Player, otherwise null
	 */
	public List<QuestData> getQuestDataListForPlayer(final QuestPlayer player) {
		for (final QuestController controller : this.questControllers)
			if (controller.getPlayer().getPlayerID() == player.getPlayerID())
				return controller.getQuestDataList();
		return null;
	}

	/**
	 * Get a quest based on its id
	 * 
	 * @param id The ID of the quest you want
	 * @return The Quest you requested or null if not found
	 */
	public Quest getQuest(final int id) {
		for (final Quest quest : this.getQuests())
			if (quest.id == id)
				return quest;
		return null;
	}

	/**
	 * Get the List of Quest that are Loaded into the System
	 * 
	 * @return A List of quests that was Loaded
	 */
	public List<Quest> getQuests() {
		return this.quests;
	}

	/**
	 * Load all the quests into the system after you have setup the Quest System by
	 * registering all action and rule methods.
	 * 
	 * FIXME Adjust the loading function so it is more legible and simpler
	 * 
	 * @param dir The directory that holds all the quest files
	 * @throws QuestSystemError Will throw an error if something happens while
	 *                          loading.
	 */
	public void loadQuests(final String dir) throws QuestSystemError {
		if (this.dir == null && dir == null)
			throw new QuestSystemError(
					"You must provide the directory to load the quests from once then you can just reload them!");
		if (this.quests.size() > 0 && this.dir != null && dir != null && this.dir.equals(dir))
			throw new QuestSystemError(
					"You already loaded the quests once. Please use reload to load the quests again!");
		if (this.quests.size() > 0)
			this.quests.clear();
		if (dir != null && this.questControllers.size() > 0)
			this.questControllers.clear();
		// Get Quest Files
		final File dirFile = new File(dir != null ? (this.dir = dir) : this.dir);
		for (final File questFile : dirFile.listFiles()) {
			// Extract Quest ID
			final int id = Integer.parseInt(questFile.getName().substring(0, questFile.getName().indexOf(".")));

			// Create the Quest so we can add data as needed
			final Quest quest = new Quest(id);

			// Get the Quest Data as a String
			final String questData = this.getQuestData(questFile);

			// Split the Steps of the Quest up
			final ArrayList<String> stepsSplit = this.splitString(questData, '}');
			for (String stepString : stepsSplit) {
				// Extract the name of the Step
				final String stepName = stepString.toLowerCase().startsWith("setup{") ? "Setup"
						: stepString.substring(4, stepString.indexOf('{'));

				// Create the Step of the Quest
				final QuestStep questStep = new QuestStep(stepName);

				// Extract the Data of the Step
				stepString = stepString.substring(stepString.indexOf('{') + 1, stepString.length() - 1);

				// Split the Actions of the Step up
				final ArrayList<String> actionsSplit = this.splitString(stepString, ';');
				for (String actionItem : actionsSplit) {
					// Extract the type of the action
					final String actionType = actionItem.substring(0, actionItem.indexOf(":"));

					// Extract the Data of the action
					actionItem = actionItem.substring(actionItem.indexOf(":") + 1, actionItem.length() - 1);

					// Do something with action based on Type
					if (stepName.toLowerCase().equals("setup")) {
						if (actionType.toLowerCase().equals("name")) { // Extract Quest Name
							quest.setName(actionItem.substring(1, actionItem.length() - 1));
						} else if (actionType.toLowerCase().equals("version")) { // Extract Quest Version
							final String[] versionSplit = actionItem.split("\\.");
							if (versionSplit.length >= 2) {
								final int major = Integer.parseInt(versionSplit[0]);
								final int minor = Integer.parseInt(versionSplit[1]);
								quest.setVersion(major, minor);
							} else {
								throw new QuestSystemError("Version can only have major.minor version in \""
										+ questFile.getName() + "\"!");
							}
						} else if (actionType.toLowerCase().equals("type")) { // Extract Quest Type
							try {
								quest.setType(QuestType.valueOf(actionItem.toUpperCase()));
							} catch (final IllegalArgumentException e) {
								throw new QuestSystemError("Type quest type you specified in \"" + questFile.getName()
										+ "\" does not exist!");
							}
						} else if (actionType.toLowerCase().equals("trigger")) { // Extract Quest Type
							try {
								quest.setTrigger(QuestTrigger.valueOf(actionItem.toUpperCase()));
							} catch (final IllegalArgumentException e) {
								throw new QuestSystemError("Type quest trigger you specified in \""
										+ questFile.getName() + "\" does not exist!");
							}
						} else if (actionType.toLowerCase().equals("rule")) { // Extract Quest Trigger Rule
							try {
								final String ruleName = actionItem.substring(0, actionItem.indexOf('('));
								if (this.hasRuleMethod(ruleName.startsWith("!") ? ruleName.substring(1) : ruleName)) {
									// Check if rule is supposed to be NOT
									boolean notFlag = ruleName.startsWith("!");

									// Get the Method we need to check
									final QuestRuleMethod method = this
											.getRuleMethod(ruleName.startsWith("!") ? ruleName.substring(1) : ruleName);

									// Get the Arguments to pass to method
									final ArrayList<String> args = new ArrayList<String>();
									final String argList = actionItem.substring(actionItem.indexOf('(') + 1,
											actionItem.indexOf(')'));

									// Clean Arguments if needed
									for (final String arg : argList.split(",")) {
										if (arg.startsWith("\""))
											args.add(this.stripArgument(arg));
										else
											args.add(arg.endsWith(",") ? arg.substring(0, arg.length() - 1) : arg);
									}

									// Add Rule to the Quest Step
									quest.setTriggerRule(new QuestRule(method, notFlag, "Begin", args.toArray()));
								} else {
									throw new QuestSystemError("Rule Method \"" + ruleName
											+ "\" was never registered for \"" + questFile.getName() + "\"!");
								}
							} catch (final IllegalArgumentException e) {
								throw new QuestSystemError("Type quest trigger rule you specified in \""
										+ questFile.getName() + "\" does not exist!");
							}
						}
						// NOTICE Add Anything Else needed to Setup
					} else {
						try {
							switch (QuestActions.valueOf(actionType.toUpperCase())) {
								case INFO: // Info
									questStep.setInfo(actionItem.substring(1, actionItem.length() - 1));
									break;
								case INFOE: // Info Extended
									questStep.setInfoExtended(actionItem.substring(1, actionItem.length() - 1));
									break;
								case ACTION: // Action
									final String actionName = actionItem.substring(0, actionItem.indexOf('('));
									if (this.hasActionMethod(actionName)) {
										// Get the Method we need to execute
										final QuestActionMethod method = this.getActionMethod(actionName);

										// Get the Arguments to pass to method
										final ArrayList<String> args = new ArrayList<String>();
										actionItem = actionItem.substring(actionItem.indexOf('(') + 1,
												actionItem.indexOf(')'));

										// Clean Arguments if needed
										for (final String arg : this.splitString(actionItem, ',')) {
											if (arg.startsWith("\""))
												args.add(this.stripArgument(arg));
											else
												args.add(arg.endsWith(",") ? arg.substring(0, arg.length() - 1) : arg);
										}

										// Add Action to the Quest Step
										questStep.addAction(method, args.toArray());
									} else {
										throw new QuestSystemError("Action Method \"" + actionName
												+ "\" was never registered for \"" + questFile.getName() + "\"!");
									}
									break;
								case RULE: // Rule
									final String ruleName = actionItem.substring(0, actionItem.indexOf('('));
									if (this.hasRuleMethod(
											ruleName.startsWith("!") ? ruleName.substring(1) : ruleName)) {
										// Check if rule is supposed to be NOT
										boolean notFlag = ruleName.startsWith("!");

										// Get the Method we need to check
										final QuestRuleMethod method = this.getRuleMethod(
												ruleName.startsWith("!") ? ruleName.substring(1) : ruleName);

										// Get the Arguments to pass to method
										final ArrayList<String> args = new ArrayList<String>();
										final String argList = actionItem.substring(actionItem.indexOf('(') + 1,
												actionItem.indexOf(')'));

										// Clean Arguments if needed
										for (final String arg : argList.split(",")) {
											if (arg.startsWith("\""))
												args.add(this.stripArgument(arg));
											else
												args.add(arg.endsWith(",") ? arg.substring(0, arg.length() - 1) : arg);
										}

										// Get the Step to GOTO
										actionItem = actionItem.substring(actionItem.indexOf(')') + 1);
										if (!actionItem.startsWith("goto"))
											throw new QuestSystemError("Rule Method \"" + ruleName
													+ "\" did not declare which Step to switch to in \""
													+ questFile.getName() + "\"!");
										final String nextStep = actionItem.substring(4);

										// Add Rule to the Quest Step
										questStep.addRule(method, notFlag, nextStep, args.toArray());
									} else {
										throw new QuestSystemError("Rule Method \"" + ruleName
												+ "\" was never registered for \"" + questFile.getName() + "\"!");
									}
									break;
								case RULEH: // Rule Hidden
									/*
									 * TODO Extract hidden rule of step
									 * A Hidden Rule is a Rule that normally wont be shown to the player to make
									 * things more challenging. Normally a Rule would be something you let a player
									 * see as it is what you must do to complete the quest; however, a hidden rule
									 * would be more of a secret in a quest to keep it from the player.
									 */
									break;
								default:
									break;
							}
						} catch (final IllegalArgumentException e) {
							throw new QuestSystemError("The Action of \"" + actionType + "\" does not exist in \""
									+ questFile.getName() + "\"!");
						}
					}
				}
				if (!quest.hasStep(questStep)) {
					if (!questStep.name.equals("setup"))
						quest.addStep(questStep);
				} else {
					throw new QuestSystemError("Duplicate quest step found in \"" + questFile.getName() + "\"!");
				}
			}
			this.quests.add(quest);
			// System.out.println(quest.id + ": " + quest.getType());
		}
	}

	/**
	 * Register a Action Method to the quest system to be used during the loading of
	 * quests
	 * 
	 * @param method The Action Method to register to the Quest System
	 * @throws QuestSystemError Will throw an error if something happens while
	 *                          loading.
	 */
	public void registerActionMethod(final QuestActionMethod method) throws QuestSystemError {
		if (!this.hasActionMethod(method))
			this.actionMethods.add(method);
		else
			throw new QuestSystemError("Cannot add Duplicate Action Methods!");
	}

	/**
	 * Register a Rule Method to the quest system to be used during the loading of
	 * quests
	 * 
	 * @param method The Rule Method to register to the Quest System
	 * @throws QuestSystemError Will throw an error if something happens while
	 *                          loading.
	 */
	public void registerRuleMethod(final QuestRuleMethod method) throws QuestSystemError {
		if (!this.hasRuleMethod(method))
			this.ruleMethods.add(method);
		else
			throw new QuestSystemError("Cannot add Duplicate Rule Methods!");
	}

	/**
	 * Register a Player with the Quest Update System so it can be updated and
	 * tracked
	 * 
	 * @param player The Player you want to register with the system
	 */
	public void registerPlayer(final QuestPlayer player) {
		if (!this.hasRegisteredPlayer(player)) {
			final QuestController controller = new QuestController(this, player);
			this.questControllers.add(controller);
			for (final QuestData data : controller.getQuestDataList()) {
				final QuestData qd = player.loadQuestData(data.id);
				if (qd != null)
					controller.updateQuestData(qd);
			}
		}
	}

	/**
	 * Clear out the current quests in the system and load in new ones
	 * 
	 * @throws QuestSystemError Will throw an error if something happens while
	 *                          loading.
	 */
	public void reloadQuests() throws QuestSystemError {
		if (this.dir == null)
			throw new QuestSystemError("You must have loaded the quest at least once to use this method.");
		this.paused = true;
		this.quests.clear();
		this.loadQuests(null);

		for (final QuestController controller : this.questControllers) {
			controller.reloadQuestData();
			for (final QuestData data : controller.getQuestDataList()) {
				final QuestData qd = controller.getPlayer().loadQuestData(data.id);
				if (qd != null)
					controller.updateQuestData(qd);
			}
		}
		this.paused = false;
	}

	/**
	 * Stop the Update System Thread, this should be ran once when the Quest System
	 * is being stopped before closing app
	 */
	public void exit() {
		this.running = false;
	}

	/**
	 * Get the Action Method from the List with the name in question
	 * 
	 * @param name The name of method you want to search for
	 * @return The Action Method you request or null if not found
	 */
	private QuestActionMethod getActionMethod(final String name) {
		for (final QuestActionMethod method : this.actionMethods)
			if (method.name.equals(name))
				return method;
		return null;
	}

	/**
	 * Get the Quest Controller for a specific player
	 * 
	 * @param player The Player in which you want the controller for
	 * @return The Controller if found, otherwise null
	 */
	private QuestController getController(final QuestPlayer player) {
		for (final QuestController controller : this.questControllers)
			if (controller.getPlayer().getPlayerID() == player.getPlayerID())
				return controller;
		return null;
	}

	/**
	 * Get the Rule Method from the List with the name in question
	 * 
	 * @param name The name of method you want to search for
	 * @return The Rule Method you request or null if not found
	 */
	private QuestRuleMethod getRuleMethod(final String name) {
		for (final QuestRuleMethod method : this.ruleMethods)
			if (method.name.equals(name))
				return method;
		return null;
	}

	/**
	 * Extract the Quest Data from the obstructed file. Also run Validation to make
	 * sure the file is correctly created for most part.
	 * 
	 * @param questFile File to extract data from
	 * @return A string that contains the quest data
	 * @throws QuestSystemError If there is any errors extracting the data
	 */
	private String getQuestData(final File questFile) throws QuestSystemError {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(questFile));
			final StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = br.readLine()) != null) {
				// Strip out Whitespace and Comments
				line = this.stripCommentsAndTrim(line);

				// Validate the line
				if (line.endsWith("{") && !(line.contains("Setup") || line.contains("Step")))
					throw new QuestSystemError(
							"You must define weather it is the Steup or a Step in \"" + questFile.getName() + "\"!");
				else if (line.endsWith(";") && !line.contains(":"))
					throw new QuestSystemError("You forgot to declare a colon(:) in \"" + questFile.getName() + "\"!");

				// Add Line to the String Builder if it checks out in Validate
				sb.append(line);
			}
			br.close();
			return sb.toString();
		} catch (final NumberFormatException e) {
			throw new QuestSystemError("Could not convert \"" + questFile.getName() + "\" to an ID!");
		} catch (final FileNotFoundException e) {
			throw new QuestSystemError("Could not locate \"" + questFile.getAbsolutePath() + "\"!");
		} catch (final IOException e) {
			throw new QuestSystemError("Issue reading data from \"" + questFile.getAbsolutePath() + "\"!");
		} catch (final Exception e) {
			throw new QuestSystemError(
					"Something went horribly wrong reading \"" + questFile.getAbsolutePath() + "\"!");
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (final IOException e) {
					throw new QuestSystemError("Issue reading data from \"" + questFile.getAbsolutePath() + "\"!");
				}
		}
	}

	/**
	 * Check to see if the Action Method already exists in the Quest System
	 * 
	 * @param name The name of the method you want to lookup
	 * @return true if found, otherwise false
	 */
	private boolean hasActionMethod(final String name) {
		for (final QuestActionMethod method : this.actionMethods)
			if (method.name.equals(name))
				return true;
		return false;
	}

	/**
	 * Check to see if the Action Method already exists in the Quest System
	 * 
	 * @param method The method to check for. (Notice: It just uses the name to
	 *               search)
	 * @return true if found, otherwise false
	 */
	private boolean hasActionMethod(final QuestActionMethod method) {
		return this.hasActionMethod(method.name);
	}

	/**
	 * Check to see if the Rule Method already exists in the Quest System
	 * 
	 * @param method The method to check for. (Notice: It just uses the name to
	 *               search)
	 * @return true if found, otherwise false
	 */
	private boolean hasRuleMethod(final QuestRuleMethod method) {
		return this.hasRuleMethod(method.name);
	}

	/**
	 * Check to see if the Rule Method already exists in the Quest System
	 * 
	 * @param name The name of the method you want to lookup
	 * @return true if found, otherwise false
	 */
	private boolean hasRuleMethod(final String name) {
		for (final QuestRuleMethod method : this.ruleMethods)
			if (method.name.equals(name))
				return true;
		return false;
	}

	/**
	 * Check if a Player is registered in the quest update system
	 * 
	 * @param player The Player you want to check the system for
	 * @return True if player is registered, otherwise false
	 */
	private boolean hasRegisteredPlayer(final QuestPlayer player) {
		for (final QuestController controller : this.questControllers)
			if (controller.getPlayer().getPlayerID() == player.getPlayerID())
				return true;
		return false;
	}

	/**
	 * Split a String by a delimiter without splitting inside of quotes or splitting
	 * an escaped char.
	 * 
	 * @param line      String to Split up
	 * @param delimiter The delimiter you want to split by
	 * @return An ArrayList<String> that contains the split up data
	 */
	private ArrayList<String> splitString(final String line, final char delimiter) {
		final ArrayList<String> split = new ArrayList<String>();
		final StringBuilder sb = new StringBuilder();
		boolean escapeChar = false, insideQuotes = false;
		for (final char c : line.toCharArray()) {
			sb.append(c);
			if (c == '\\' || escapeChar) {
				escapeChar = !escapeChar;
			} else if (c == '\"' || c == '\'') {
				insideQuotes = !insideQuotes;
			} else if (!insideQuotes && c == delimiter) {
				if (!sb.toString().isEmpty())
					split.add(sb.toString());
				sb.setLength(0);
			}
		}
		if (!sb.toString().isEmpty())
			split.add(sb.toString());
		return split;
	}

	/**
	 * Remove the quotes and escape characters from the argument as we shouldn't
	 * need them anymore
	 * 
	 * @param line The Argument you want to strip as a String
	 * @return A String containing the newly Stripped Argument
	 */
	private String stripArgument(final String line) {
		final StringBuilder sb = new StringBuilder();
		boolean escapeChar = false;
		for (final char c : line.toCharArray()) {
			if (c == '\\' || escapeChar)
				escapeChar = !escapeChar;
			if (!escapeChar && c != '\"' && c != '\'')
				sb.append(c);
		}
		final String toReturn = sb.toString();
		return toReturn.endsWith(",") ? toReturn.substring(0, toReturn.length() - 1) : toReturn;
	}

	/**
	 * Strip out any comments and extra whitespace as we don't need them
	 * 
	 * @param line The String you are wanting to strip
	 * @return Returns a new String with everything Stripped out
	 */
	private String stripCommentsAndTrim(final String line) {
		final StringBuilder sb = new StringBuilder();
		boolean startStripping = false, insideQuotes = false, escapeChar = false;
		for (final char c : line.toCharArray()) {
			if (!startStripping && (c == '\\' || escapeChar))
				escapeChar = !escapeChar;
			else if (!startStripping && (c == '\"' || c == '\''))
				insideQuotes = !insideQuotes;
			else if (!startStripping && !insideQuotes && c == '/')
				startStripping = true;
			if (!startStripping && (insideQuotes || (c != '\t' && c != ' '))) {
				sb.append(c);
			}
		}
		return sb.toString().trim();
	}

	/**
	 * Prints the Library Information to the Console for Easy Info Checking
	 */
	public void printLibraryInfo() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
	}

	/**
	 * Get the Current Version of the Library in its "Fancy" form
	 * 
	 * @return String
	 */
	public static String version() {
		return "##library.prettyVersion##";
	}

	/**
	 * Get the Current Version of the Library in its "Raw" form
	 * 
	 * @return int
	 */
	public static int versionRaw() {
		return Integer.parseInt("##library.version##");
	}
}
