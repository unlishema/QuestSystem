package org.unlishema.questSystem;

/**
 * JAVADOC QuestValidator
 * 
 * @author Unlishema
 *
 */
public class QuestValidator {

	public static void registerMethods(final QuestSystem qs) throws QuestSystemError {
		// Actions
		qs.registerActionMethod(new QuestActionMethod("AddNpcText", 2) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Add NPC Text");
				for (final Object arg : args)
					System.out.println(arg);
			}
		});
		qs.registerActionMethod(new QuestActionMethod("MarkLocation", 2) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Mark Location on Map");
				for (final Object arg : args)
					System.out.println(arg);
			}
		});
		qs.registerActionMethod(new QuestActionMethod("RemovePartyItems", 2) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Remove Items from Party");
				for (final Object arg : args)
					System.out.println(arg);
			}
		});
		qs.registerActionMethod(new QuestActionMethod("PlaySound", 1) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Play a Sound on game");
				for (final Object arg : args)
					System.out.println(arg);
			}
		});
		qs.registerActionMethod(new QuestActionMethod("GivePartyEXP", 1) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Give Party Exp");
				for (final Object arg : args)
					System.out.println(arg);
			}
		});
		qs.registerActionMethod(new QuestActionMethod("GivePlayerEXP", 1) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Give Player Exp");
				for (final Object arg : args)
					System.out.println(arg);
			}
		});
		qs.registerActionMethod(new QuestActionMethod("GivePartyItem", 2) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Give Party Item(s)");
				for (final Object arg : args)
					System.out.println(arg);
			}
		});
		qs.registerActionMethod(new QuestActionMethod("GivePlayerItem", 2) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Give Player Item");
				for (final Object arg : args)
					System.out.println(arg);
			}
		});
		qs.registerActionMethod(new QuestActionMethod("Print", 1) {

			@Override
			public void execute(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println(args[0]);
			}
		});

		// Rules
		qs.registerRuleMethod(new QuestRuleMethod("TalkToNpc", 1) {
			
			private int index = 0;

			@Override
			public boolean check(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Check if Talking to NPC");
				for (final Object arg : args)
					System.out.println(arg);
				return index++ >= 3;
			}
		});
		qs.registerRuleMethod(new QuestRuleMethod("WalkToLocation", 2) {
			
			private int index = 0;

			@Override
			public boolean check(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Check if near Location");
				for (final Object arg : args)
					System.out.println(arg);
				return index++ >= 2;
			}
		});
		qs.registerRuleMethod(new QuestRuleMethod("CollectPartyItems", 2) {
			
			private int index = 0;

			@Override
			public boolean check(final QuestData qd, final QuestPlayer player, final Object... args) {
				System.out.println("Check if Party has collected items");
				for (final Object arg : args)
					System.out.println(arg);
				return index++ >= 2;
			}
		});
	}

	public static void main(final String[] args) {
		// TODO Create a Quest Writer/Validator that is a JFrame
		final QuestSystem qs = new QuestSystem();
		try {
			QuestValidator.registerMethods(qs);
			qs.loadQuests("examples/DevelopmentExample/quests");

			for (final Quest quest : qs.getQuests()) {
				System.out.println("Loaded Quest: [" + quest.id + "] " + quest.getName() + " " + quest.getVersion()
						+ " (" + quest.getType() + ") " + quest.getTriggerType());
				for (final QuestStep step : quest.getSteps()) {
					System.out.println("              [" + step.name + "] " + step.getInfo());
				}
			}

			final QuestPlayer player = new QuestPlayer() {

				@Override
				public int getPlayerID() {
					return 0;
				}

				@Override
				public void saveQuestData(final QuestData questData) {
				}

				@Override
				public QuestData loadQuestData(final int questID) {
					return null;
				}

			};
			qs.registerPlayer(player);

			for (final QuestData qd : qs.getQuestDataListForPlayer(player))
				System.out.println("Player: " + player.getPlayerID() + " Q:" + qd.id + " [" + qd.isTriggered() + "] "
						+ qd.getCurrentStep());
			
			Thread.sleep(75);

			for (final QuestData qd : qs.getQuestDataListForPlayer(player))
				System.out.println("Player: " + player.getPlayerID() + " Q:" + qd.id + " [" + qd.isTriggered() + "] "
						+ qd.getCurrentStep());
			
			Thread.sleep(75);

			for (final QuestData qd : qs.getQuestDataListForPlayer(player))
				System.out.println("Player: " + player.getPlayerID() + " Q:" + qd.id + " [" + qd.isTriggered() + "] "
						+ qd.getCurrentStep());
			
			qs.exit();
		} catch (final QuestSystemError | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
