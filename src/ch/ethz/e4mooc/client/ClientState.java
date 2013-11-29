/**
 * 
 */
package ch.ethz.e4mooc.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.ethz.e4mooc.shared.ProjectModelDTO;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;

/**
 * This class holds client state.
 * 
 * @author hce
 *
 */
public class ClientState {

	/** the name of the current project */
	private String currentProjectName;
	
	/** the project model of the current project */
//	private ProjectModelDTO projectModel;
	/** a list of all the file names used by the project */
	private List<String> projectFileNames;
	/** the event bus used throughout the application */
	//private EventBus eventBus;
	
	/** a map that stores files at a specific tab index */
	private Map<Integer, FileContainer> userStorage;
	
	/**
	 * the following fields are about storing Uri parameters
	 */
	/** the id of the user */
	private String userId;
	/** the group-id of the user */
	private String groupId;
	/** the height of the output box that shows the result */
	private int outputBoxHeight;
	/** the color of the background */
	private String backgroundColor;
	
	
	Storage myStorage;
	
	
	/**
	 * 
	 * @param eventBus the eventBus used throughout the application
	 */
	public ClientState(EventBus eventBus) {
		//this.eventBus = eventBus;
		projectFileNames = new LinkedList<String>();
		userStorage = new HashMap<Integer, FileContainer>();
		
		// initialize default values for user properties
		userId = "";
		groupId = "";
		outputBoxHeight = 250;
		backgroundColor = "#FFFFFF"; // set it to white
		
		myStorage = Storage.getLocalStorageIfSupported();
		
		bind();
	}
	
	
	private void bind() {
		
		// on closing the browser window
		Window.addCloseHandler(new CloseHandler<Window>() {
			
			@Override
			public void onClose(CloseEvent<Window> event) {
				// before the browser window is closed, store the latest changes of the currently visible tab
				//storeYourCodeToLocalStorage();
			}
		});		
	}
	
	
	/**
	 * Stores a project model in the ClientState.
	 * @param pm the project model
	 */
	public void storeProjectModel(ProjectModelDTO pm) {
		
		this.currentProjectName = pm.getProjectName();
		
		// clear out the user-storage (in case the new PM has a file with the same name)
		userStorage.clear();
		
		// sort the files by filename so we display them in alphabetical order
		Collections.sort(pm.getFileNames());
		
		// map the files of the project to tab indexes
		
		for(int i = 0; i < pm.getFileNames().size(); i++) {
			String fileName = pm.getFileNames().get(i);
			FileContainer c = new FileContainer(i, fileName, pm.getFileContent(fileName));
			userStorage.put(i, c);
			
			projectFileNames.add(fileName);
		}
	}

	/**
	 * Returns the name of the project.
	 * @return the project name
	 */
	public String getProjectName() {
		return currentProjectName;
	}
	
	/**
	 * Returns a list of all the file names in the project.
	 * @return list of file names
	 */
	public List<String> getProjectFileNames() {
		return projectFileNames;
	}
	
	
	/**
	 * Returns the content of a file based on the tab index used by the editor.
	 * @param tabIndex the tab index
	 * @return the content of the file that belongs to the tab index
	 */
	public String getContentOfFile(int tabIndex) {
		String result = "";
				
		if(userStorage.containsKey(tabIndex)) {
			result = userStorage.get(tabIndex).getFileContent();
		}
		
		return result;
	}
	
	
	/**
	 * Returns a map with all the files. Files include the changes by users.
	 * @return map of all files
	 */
	public HashMap<String, String> getContentOfAllFiles() {
		HashMap<String, String> result = new HashMap<String, String>();
				
		for(int tabIndex: userStorage.keySet())
			result.put(userStorage.get(tabIndex).getFileName(), userStorage.get(tabIndex).getFileContent());

		return result;
	}

	
	/**
	 * Stores the given text into session storage, if that's supported by the browser.
	 * @param tabIndex the index of the tab that should be stored (is used as port of the KEY)
	 * @param text the text to store
	 */
	public void storeTextFromUser(int tabIndex, String text) {
		userStorage.get(tabIndex).setFileContent(text);
	}

	
	/**
	 * Removes the user version for the given tab index from the storage.
	 * @param tabIndex the tab index of the file for which to delete the entry
	 */
	public void deleteFromStorage(int tabIndex) {	
		if(userStorage.containsKey(tabIndex))
			userStorage.get(tabIndex).revertFileContentToOriginal();
	}
	
	
	/**
	 * Returns the id of the user who's using the current project.
	 * @return empty string for unknown users; otherwise the user id; 
	 */
	public String getUserId() {
		return userId;
	}
	
	
	/**
	 * Stores the given id in the client state.
	 * @param userId id of a user
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * Returns the group-id of the user who's using the current project.
	 * @return empty string for unknown users; otherwise the user id; 
	 */
	public String getUserGroupId() {
		return groupId;
	}
	
	
	/**
	 * Stores the given group-id in the client state.
	 * @param userId id of a user
	 */
	public void setUserGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	
	/**
	 * Returns the user's preferred height for the output box.
	 * @return value of preferred height or a default value
	 */
	public int getUserOutputBoxHeight() {
		return outputBoxHeight;
	}
	
	
	/**
	 * Set the user's preferred height for the output box.
	 * @param outputBoxHeight height value
	 */
	public void setUserOutputBoxHeight(int outputBoxHeight) {
		this.outputBoxHeight = outputBoxHeight;
	}
	
	/**
	 * Returns the user's preferred background color.
	 * @return hex-formated color; by default that's white (#FFFFFF)
	 */
	public String getUserBackgroundColor() {
		return this.backgroundColor;
	}
	
	/**
	 * Set the user's preferred background color
	 * @param backgroundColor a color formated in hex-format (e.g.#FFFFFF)
	 */
	public void setUserBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}


	public int[] getCursorPositionForFile(int tabIndex) {
		
		int [] result = new int[3];
		
		if (userStorage.containsKey(tabIndex))
			result = userStorage.get(tabIndex).getCursorPosition();
		
		return result;
	}


	public void storeCursorPositionFromUser(int tabIndex, int[] currentCursorPosition) {
		if (userStorage.containsKey(tabIndex))
			userStorage.get(tabIndex).setCursorPosition(currentCursorPosition);
	}
	
}


class FileContainer {
	
	private String fileName;
	/** the content of the file as returned by the server */
	private final String originalFileContent;
	/** the content of the file including changes made by the user */
	private String userFileContent;
	private int[] cursorPosition;
	private int tabIndex;
	/** must be set to true if this FileContainer holds content with user modifications */
	private boolean hasUserContent;
	
	public FileContainer(int tabIndex, String fileName, String fileContent) {
		this.tabIndex = tabIndex;
		this.fileName = fileName;
		this.originalFileContent = fileContent;
		this.hasUserContent = false;
		
		// Initialize the cursorPosition array with 0
		this.cursorPosition = new int[3];
	}
	
	
	/**
	 * Call this method to revert the file's content to the original
	 * version that has been provided by when the FileContainer object was created.
	 */
	public void revertFileContentToOriginal() {
		this.userFileContent = "";
		this.hasUserContent = false;
		this.cursorPosition = new int[3];
	}
	
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	/**
	 * Return the content that belongs to the file.
	 * The method returns the content of the file that includes the changes of a user if such changes exist;
	 * Otherwise the content is the one the one that was provided when the FileContainer was created.
	 * @return the content of the file represented by the FileContainer
	 */
	public String getFileContent() {
		if (hasUserContent)
			return userFileContent;
		else
			return originalFileContent;
	}
	
	
	public void setFileContent(String content) {
		this.userFileContent = content;
		this.hasUserContent = true;
	}


	/**
	 * @return the cursorPosition
	 */
	public int[] getCursorPosition() {
		return cursorPosition;
	}


	/**
	 * @param cursorPosition the cursorPosition to set
	 */
	public void setCursorPosition(int[] cursorPosition) {
		this.cursorPosition = cursorPosition;
	}


	/**
	 * @return the tabIndex
	 */
	public int getTabIndex() {
		return tabIndex;
	}


	/**
	 * @param tabIndex the tabIndex to set
	 */
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
}
