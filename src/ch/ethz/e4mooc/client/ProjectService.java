/**
 * 
 */
package ch.ethz.e4mooc.client;

import java.util.LinkedList;
import java.util.Set;

import ch.ethz.e4mooc.shared.ProjectModelDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author hce
 *
 */

@RemoteServiceRelativePath("project")
public interface ProjectService extends RemoteService {

	/**
	 * Returns a list with the names of all Eiffel projects available on the server.
	 * @return a list of all project names.
	 */
	public LinkedList<String> getAllProjectNames();
	
	/**
	 * Returns true if the server has a project with the provided project name.
	 * @param projectName the name to check for
	 * @return true if a project of that name exists
	 */
	public boolean hasProject(String projectName);
	
	/**
	 * Returns a model that contains the data needed by the client to display an Eiffel project.
	 * @param projectName the name of the project
	 * @return the project model for the given project name
	 */
	public ProjectModelDTO getProject(String projectName);
	
}
