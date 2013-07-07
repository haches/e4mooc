/**
 * 
 */
package ch.ethz.e4mooc.server.util;

/**
 * Provides access to E4Mooc's environment variables that are
 * used by various server classes.
 * 
 * @author hce
 *
 */
public enum ServerProperties {

	E4MOOC_TMP_FOLDER("E4MOOC_TMP"),
	E4MOOC_PROJECTS_FOLDER("E4MOOC");
	
	private final String envVar;
	
	/** Private constructor */
	private ServerProperties(String envVarName) {
		envVar = System.getenv(envVarName);
	}
	
	@Override
	public String toString() {
		return envVar;
	}
}
