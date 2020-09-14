package org.unlishema.questSystem;

import processing.core.PApplet;

/**
 * <h2>Quest System</h2>
 * 
 * @author Unlishema
 *
 */
public class QuestSystem {

	private final PApplet parent;
	/**
	 * Default and ONLY Constructor of the Quest System
	 * 
	 * @param parent The PApplet that this Library is being used on (Usually is
	 *               "this")
	 */
	public QuestSystem(final PApplet parent) {
		this.parent = parent;
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
