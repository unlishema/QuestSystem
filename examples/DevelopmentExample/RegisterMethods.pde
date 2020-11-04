public void registerMethods() throws QuestSystemError {
  // Actions
  qs.registerActionMethod(new QuestActionMethod("AddNpcText", 2) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Add NPC Text");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });
  qs.registerActionMethod(new QuestActionMethod("MarkLocation", 2) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Mark Location on Map");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });
  qs.registerActionMethod(new QuestActionMethod("RemovePartyItems", 2) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Remove Items from Party");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });
  qs.registerActionMethod(new QuestActionMethod("PlaySound", 1) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Play a Sound on game");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });
  qs.registerActionMethod(new QuestActionMethod("GivePartyEXP", 1) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Give Party Exp");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });
  qs.registerActionMethod(new QuestActionMethod("GivePlayerEXP", 1) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Give Player Exp");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });
  qs.registerActionMethod(new QuestActionMethod("GivePartyItem", 2) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Give Party Item(s)");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });
  qs.registerActionMethod(new QuestActionMethod("GivePlayerItem", 2) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Give Player Item");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });
  qs.registerActionMethod(new QuestActionMethod("Print", 1) {

    @Override
    public void execute(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Print");
      for (final Object arg : args)
        System.out.println(arg);
    }
  });

  // Rules
  qs.registerRuleMethod(new QuestRuleMethod("TalkToNpc", 1) {
    
    private int index = 0;

    @Override
    public boolean check(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Check if Talking to NPC");
      for (final Object arg : args)
        System.out.println(arg);
      return index++ >= 3;
    }
  });
  qs.registerRuleMethod(new QuestRuleMethod("WalkToLocation", 2) {

    @Override
    public boolean check(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Check if near Location");
      for (final Object arg : args)
        System.out.println(arg);
      return false;
    }
  });
  qs.registerRuleMethod(new QuestRuleMethod("CollectPartyItems", 2) {
    
    private int index = 0;

    @Override
    public boolean check(final QuestData data, final QuestPlayer player, final Object... args) {
      System.out.println("Check if Party has collected items");
      for (final Object arg : args)
        System.out.println(arg);
      return index++ >= 2;
    }
  });
}
