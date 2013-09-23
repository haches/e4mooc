/**
 * 
 */
package ch.ethz.e4mooc.server.util;

/**
 * Enum for the different types of operating system that might run
 * on the server.
 * 
 * Depending on the server's OS and wether the server supports a sandboxing
 * we execute a user's program differently. Use the interface ServerProperties
 * to set the server's operating system.
 * 
 * @author hce
 *
 */
public enum ServerSystem {
	WIN_SANDBOXED, LINUX_SANBOXED, OTHER;
}