import org.unlishema.questSystem.*;

QuestSystem qs = new QuestSystem();;

void setup() {
  size(500, 500);
	try {
		registerMethods();
		qs.loadQuests(sketchPath() + "/quests");
	
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
	
		for (final QuestData qd : qs.getPlayerQuestData(player))
			System.out.println("Player: " + player.getPlayerID() + " Q:" + qd.id + " [" + qd.isTriggered() + "] "
					+ qd.getCurrentStep());
	
		qs.startUpdateSystem();
		
		Thread.sleep(100);
	
		for (final QuestData qd : qs.getPlayerQuestData(player))
			System.out.println("Player: " + player.getPlayerID() + " Q:" + qd.id + " [" + qd.isTriggered() + "] "
					+ qd.getCurrentStep());
		
		qs.stopUpdateSystem();
	} catch (final QuestSystemError e) {
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

void draw() {
  background(0);
}
