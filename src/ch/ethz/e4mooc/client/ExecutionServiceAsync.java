/**
 * 
 */
package ch.ethz.e4mooc.client;

import java.util.Map;

import ch.ethz.e4mooc.shared.CompilationResultDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async interface for the the execution service.
 * 
 * @author hce
 *
 */
public interface ExecutionServiceAsync {
	
	/**
	 * @see ch.ethz.se.client.ExecutionService#compile(String, String, String)
	 */	
	void compile(String projectName, Map<String, String> inputFiles, String timeStampOfLastCompilation, String userId, String groupId, AsyncCallback<CompilationResultDTO> callback);
	
	/**
	 * @see ch.ethz.se.client.ExecutionService#execute(String)
	 */	
	void execute(String projectName, String timeStamp, String userId, String groupId, AsyncCallback<String> callback);

}
