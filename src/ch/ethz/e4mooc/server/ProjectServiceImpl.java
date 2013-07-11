package ch.ethz.e4mooc.server;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.ethz.e4mooc.client.ProjectService;
import ch.ethz.e4mooc.server.util.ServerState;
import ch.ethz.e4mooc.shared.ProjectModelDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * Implementation of the service that provides the data about
 * projects.
 * 
 * @author hce
 *
 */
public class ProjectServiceImpl extends RemoteServiceServlet implements ProjectService {

	/** Generated id */
	private static final long serialVersionUID = -4203949441332964358L;
	/** Logger for this class */
	private static final Logger LOGGER = Logger.getLogger(ch.ethz.e4mooc.server.ProjectServiceImpl.class.getName());
	
	
	@Override
	public LinkedList<String> getAllProjectNames() {
		
		LOGGER.log(Level.INFO, "Got request for all project names\n"
				+ "Session id: " + this.getThreadLocalRequest().getSession().getId() + "\n"
				+ "Time: " + new Date(System.currentTimeMillis()));
		
		LinkedList<String> result;
		result = new LinkedList<String>(ServerState.getState().getProjectNames());
		// the server state stores the project names as set (due to using a concurrent set)
		// the set might not be sorted so sort it first
		Collections.sort(result);
		return result;
	}

	
	@Override
	public ProjectModelDTO getProject(String projectName) {		
		LOGGER.log(Level.INFO, "Project " + projectName + " requested.\n"
					+ "Session id: " + this.getThreadLocalRequest().getSession().getId() + "\n"
					+ "Time: " + new Date(System.currentTimeMillis()));
		return ServerState.getState().getProjectModel(projectName).getProjectModelDTO();
	}

	
	@Override
	public boolean hasProject(String projectName) {
		if(getAllProjectNames().contains(projectName))
			return true;
		return false;
	}
	
}
