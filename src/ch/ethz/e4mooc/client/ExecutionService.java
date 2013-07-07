package ch.ethz.e4mooc.client;

import java.util.Map;

import ch.ethz.e4mooc.shared.CompilationResultDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Client stub for the service that will execute command-line programs.
 * @author hce
 *
 */

@RemoteServiceRelativePath("exec")
public interface ExecutionService extends RemoteService {
	
	/**
	 * Compiles a project on the server. Returns an output which is
	 * either the compiler message or a error message if something went wrong.
	 * @param projectName the name of the project to compile
	 * @param map with file names and file contents (keys are file names, values are file content)
	 * @param timeStampOfLastCompilation the time stamp of the last compilation (if one has been done before)
	 * @return an object containing of the compilation
	 */
	public CompilationResultDTO compile(String projectName, Map<String, String> inputFiles, String timeStampOfLastCompilation);
	
	/**
	 * Executes the project with the given project name.
	 * This method must only be called if the project was compiled before.
	 * Otherwise, it will an error message string.
	 * @param projectName the project name
	 * @param timeStamp the time stamp when the project was last compiled on the server
	 * @return the output of the program or a default error message if it was not possible to run the program
	 */
	public String execute(String projectName, String timeStamp);
	
}
