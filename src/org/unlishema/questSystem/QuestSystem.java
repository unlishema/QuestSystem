package org.unlishema.questSystem;

/**
 * <h2>Quest System</h2>
 * 
 * @author Unlishema
 *
 */
public class QuestSystem {
	/**
	 * Default and ONLY Constructor of the Quest System
	 * 
	 * @param parent The PApplet that this Library is being used on (Usually is
	 *               "this")
	 */
	public QuestSystem() {
		// TODO Work on Entire Quest System
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

	public static void main(final String[] args) {
		System.out.println("Quest Systems Runs");
	}
}
