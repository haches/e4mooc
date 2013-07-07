/**
 * 
 */
package ch.ethz.e4mooc.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A ProjectModel represents an 
 * Eiffel projects and contains the files (and the
 * file names) that should be displayed on the web page.
 * 
 * @author hce
 *
 */
public class ProjectModelDTO implements Serializable {

	/** Generated serial id */
	private static final long serialVersionUID = 3341578986753893703L;
	
	/** Name of the project */
	private String projectName;
	/** List of all file names */
	private LinkedList<String> fileNames;
	/** Map which maps a file name to it's content */
	private HashMap<String, String> contentMap;
	
	/** Empty constructor for serialization; don't use otherwise. */
	public ProjectModelDTO() {
	}
	
	/**
	 * Constructor for a new project model.
	 * @param projectName the name of the project (we use the name of the project folder on the server)
	 */
	public ProjectModelDTO(String projectName) {
		this.projectName = projectName;
		this.fileNames = new LinkedList<String>();
		this.contentMap = new HashMap<String, String>();
	}
	
	/**
	 * Returns the name of this project, i.e. the name of the project folder on the server.
	 * @return the name of this project.
	 */
	public String getProjectName() {
		return projectName;
	}
	
	/**
	 * Adds a the name of a file to the project model's list of file names.
	 * @param fileName name of a file
	 */
	public void addFileName(String fileName) {
		fileNames.add(fileName);
	}
	
	/**
	 * Adds a file to the project model.
	 * @param fileName the file name
	 * @param fileContent the content of the file
	 */
	public void addFile(String fileName, String fileContent) {
		contentMap.put(fileName, fileContent);
	}
	
	/**
	 * Returns the list of all file names in the project represented by
	 * the current object.
	 * @return all the file names in the project
	 */
	public LinkedList<String> getFileNames() {
		return fileNames;
	}
	
	/**
	 * Returns the content of a file (as stored on the server) for a given file name.
	 * @param fileName the name of file for which the content should be returned
	 * @return the content of the file
	 */
	public String getFileContent(String fileName) {
		if(contentMap.containsKey(fileName))
			return contentMap.get(fileName);
		else
			return "It seems the content of this file is not available in the project model. That's odd.";
	}

	/**
	 * Returns true if the project model has a file with the given file name.
	 * @param fileName the file name to check for
	 * @return true if a file with that name exists, otherwise false
	 */
	public boolean hasFileName(String fileName) {
		return fileNames.contains(fileName);
	}
	
}
