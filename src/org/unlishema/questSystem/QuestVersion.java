package org.unlishema.questSystem;

/**
 * Will be useful for determining if you need to update a players local quest
 * version if you need a system like that otherwise you don't even have to use
 * it at all. May have other uses in the future.
 * 
 * @author Unlishema
 *
 */
public class QuestVersion {

	public final int major, minor;

	/**
	 * Construct a new Quest Version with a major and minor integer
	 * 
	 * @param major The Major version of the Quest
	 * @param minor The Minor version of the Quest
	 */
	public QuestVersion(final int major, final int minor) {
		this.major = major;
		this.minor = minor;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("V");
		sb.append(this.major);
		sb.append(".");
		sb.append(this.minor);
		return sb.toString();
	}
}
