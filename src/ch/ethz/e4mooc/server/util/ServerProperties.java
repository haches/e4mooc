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
	
	/** which operating system runs on the server; does it have a sand-box setup */
	public final ServerSystem SERVER_SYSTEM = ServerSystem.OTHER;
	
	/** the path separtor used by the operating system */
	public final String SEP = System.getProperty("file.separator");
		
	/** the folder were the tmp folders of compiled project should be stored */
	public final String E4MOOC_TMP_FOLDER = System.getenv("S4PUBS_TMP");
	
	/** the folder were the Eiffel projects are stored */
	public final String E4MOOC_PROJECTS_FOLDER = System.getenv("S4PUBS");
	
	/** full qualified location of the pyton script file that's called to execute Synquid */
	public final String S4PUBS_EXEC_FILE = System.getenv("S4PUBS_EXEC_FILE");
	
	/** the path to the Sandboxie application (a simple sandbox for windows) */
	public final String SANDBOXIE_START = "C:\\Program Files\\Sandboxie\\Start.exe";
	
	/** the path to the drive used by the sandbox; when running programs, they store their output under this location */
	public final String SANDBOXIE_DRIVE = "C:\\Sandbox\\cloud\\DefaultBox\\drive";
}
