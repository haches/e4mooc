/**
 * 
 */
package ch.ethz.e4mooc.client;

import java.util.LinkedList;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.ethz.e4mooc.shared.ProjectModelDTO;

/**
 * @author hce
 *
 */
public interface ProjectServiceAsync {

	/** @see ch.ethz.e4mooc.client.ProjectService#getAllProjectNames() */
	public void getAllProjectNames(AsyncCallback<LinkedList<String>> callback);
	
	/** @see ch.ethz.e4mooc.client.ProjectService#hasProject(String) */
	public void hasProject(String projectName, AsyncCallback<Boolean> callback);
	
	/** @see ch.ethz.e4mooc.client.ProjectService#getProject(String) */
	public void getProject(String projectName, AsyncCallback<ProjectModelDTO> callback);
	
}
