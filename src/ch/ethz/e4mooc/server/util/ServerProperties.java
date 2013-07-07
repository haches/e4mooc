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
public interface ServerProperties {

	/** the path separtor used by the operating system */
	public final String SEP = System.getProperty("file.separator");
	
	/** is true if the current system is windows */
	public static final boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
	
	/** the folder were the tmp folders of compiled project should be stored */
	public final String E4MOOC_TMP_FOLDER = System.getenv("E4MOOC_TMP");
	
	/** the folder were the Eiffel projects are stored */
	public final String E4MOOC_PROJECTS_FOLDER = System.getenv("E4MOOC");
	
	/** the path to the Sandboxie application (a simple sandbox for windows) */
	public final String SANDBOXIE = "C:\\Program Files\\Sandboxie\\Start.exe";
	
}
