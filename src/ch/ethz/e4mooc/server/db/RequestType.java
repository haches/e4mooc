/**
 * 
 */
package ch.ethz.e4mooc.server.db;

/**
 * The different types of request that can be made to the server
 * by an E4MOOC client.
 * 
 * The types listed here are synced with the E4Mooc database table
 * 'request_type'.
 * 
 * @author hce
 *
 */
public enum RequestType {
	
	COMPILE(1),
	RUN(2);
	
	private int idValue;
	
	RequestType(int idValue) {
		this.idValue = idValue;
	}
	
	/**
	 * Returns the ID that's used in the database for the request type.
	 * @return an id value
	 */
	int getIdValue() {
		return this.idValue;
	}
}
