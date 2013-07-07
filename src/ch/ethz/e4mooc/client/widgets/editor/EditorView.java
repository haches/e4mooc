/**
 * 
 */
package ch.ethz.e4mooc.client.widgets.editor;

import java.util.List;

import ch.ethz.e4mooc.client.IPresenter;
import ch.ethz.e4mooc.client.IView;
import ch.ethz.e4mooc.shared.ExampleModel;


/**
 * The editor widget comprises
 * the ACE editor and a Tab bar, taken
 * from the the bootstrap library
 * 
 * @author hce
 *
 */
public interface EditorView extends IView<EditorPresenter> {

	public interface EditorPresenter extends IPresenter {
		
		/**
		 * This method should be called if a tab is clicked.
		 * It will put a TabSelectedEvent on the presenter's event bus.
		 * @param tabIndex the tabIndex associated of the tab was clicked
		 */
		public void onTabLinkClick(int tabIndex);
		
		/**
		 * This method should be called if the reload button is clicked.
		 * @param tabIndex the index of the tab that is currently selected
		 */
		public void onReloadBtnClick(int tabIndex);
		
		/**
		 * Adds tabs to the editor where each tab represents an file using it's file name
		 * @param fileNames list of fileNames
		 */
		public void addFiles (List<String> fileNames);
		
		/**
		 * Sets the mode of the editor, i.e. what kind language is used
		 * for syntax highlighting.
		 * @param editorMode a string that defines the editor mode
		 */
		public void setEditorMode(String editorMode);
		
	}
	
	/**
	 * Adds the ace editor. The editor needs to be injected through is call as it is
	 * not automatically added to the DOM.
	 * @param startFile the text to display when the ace editor is loaded for the first time
	 */
	public void addAceEditor(String startFile);
	
	/**
	 * Returns the text currently displayed in the editor.
	 * @return the editor text
	 */
	public String getEditorText();
	
	/**
	 * Sets the text currently displayed in the editor.
	 * @param text the text to set to the editor
	 */
	public void setEditorText(String text);
	
	/**
	 * Sets the mode of the editor. The mode determines
	 * syntax highlighting.
	 * @param mode name of the mode.
	 */
	public void setEditorMode(String mode);
	
	/**
	 * Adds a tab for every element provided in the list.
	 * @param tabNames a list of strings where each String represents a tab name
	 */
	public void addTabs(List<String> tabNames);
	
	/**
	 * The tab with tab index 'tabIndex' will be selected as active, all other tabs
	 * will be set to not active (it's only a visual effect, behavior not affected at all).
	 * @param tabIndex the index of the tab that should be set active
	 */
	public void updateSelectedTab(int tabIndex);
	
	/**
	 * Returns the name of the currently active file
	 * @return index of the currently selected tab
	 */
	public int getCurrentlySelectedTabIndex();
		
}
