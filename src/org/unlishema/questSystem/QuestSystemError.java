package org.unlishema.questSystem;

/**
 * The Quest System has a simple Error System right now but this will change in the future
 * 
 * FIXME Fix Error System in all of the QuestSystem
 * 
 * @author Unlishema
 *
 */
public class QuestSystemError extends Exception {
	private static final long serialVersionUID = 5658744028736184312L;

	/**
	 * A QuestSystem Error can occur anywhere inside the QuestSystem. A more complex
	 * error system will be added in the near future
	 * 
	 * @param e The String to display to the User of the Quest System when they get
	 *          an error
	 */
	public QuestSystemError(final String e) {
		super(e);
	}
}
