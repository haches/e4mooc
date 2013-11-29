/**
 * 
 */
package ch.ethz.e4mooc.client.widgets.editor;

import java.util.List;

import ch.ethz.e4mooc.client.E4mooc;
import ch.ethz.e4mooc.client.events.CompilationStartedEvent;
import ch.ethz.e4mooc.client.events.TabSelectedEvent;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author hce
 *
 */
public class EditorPresenter implements EditorView.EditorPresenter {

	private EditorView view;
	private EventBus eventBus;
	
	public EditorPresenter(EditorView view, EventBus eventBus) {
		this.view = view;
		this.eventBus = eventBus;
		
		bind();
	}
	
	/**
	 * Setups all the bindings needed.
	 */
	private void bind() {
		view.setPresenter(this);
		
		// when requested to store, we store the editor's current text into the client state
//		reg = RequestStoringEvent.register(eventBus, new RequestStoringEventHandler() {
//			
//			@Override
//			public void requestStoringAction(RequestStoringEvent event) {
//				E4mooc.cState.storeTextFromUser(event.getTabIndex(), view.getEditorText());
//			}
//		});
		
		//  if a tab was clicked, we have to load the corresponding code
		eventBus.addHandler(TabSelectedEvent.TYPE, new TabSelectedEvent.TabSelectedEventHandler() {
			
			@Override
			public void tabSelectedClick(TabSelectedEvent event) {
				
				// get the example for the selected tab and load it into the browser
				String fileContent = E4mooc.cState.getContentOfFile(event.getTabIndex());
				view.setEditorText(fileContent);
				view.updateSelectedTab(event.getTabIndex());
				view.setCurrentCursorPosition(E4mooc.cState.getCursorPositionForFile(event.getTabIndex()));
				view.updateSelectedTab(event.getTabIndex());
				
			}
		});
		
		
		// if a new compilation was started, we store the current content of the editor
		// to the client state; thus, the latest changes of the user will be taken
		// into account during the compilation
		eventBus.addHandler(CompilationStartedEvent.TYPE, new CompilationStartedEvent.CompilationStartedEventHandler() {
			
			@Override
			public void toolExecutionStartedAction() {
				E4mooc.cState.storeTextFromUser(view.getCurrentlySelectedTabIndex(), view.getEditorText());	
			}
		});
	}
	
	
	HandlerRegistration reg;
	
	private void unbind() {
		reg.removeHandler();
	}
	
	@Override
	public void addFiles(List<String> fileNames) {
		
		// add all the tabs
		view.addTabs(fileNames);
		
		if(fileNames.size() > 0) {
			view.setEditorText(E4mooc.cState.getContentOfFile(0));
			view.updateSelectedTab(0);
		}
	}
	
	
	@Override
	public void setEditorMode(String editorMode) {
		view.setEditorMode(editorMode);
	}
	
	
	@Override
	public void onTabLinkClick(int tabIndex) {
		
		// first we store the current user changes
		E4mooc.cState.storeTextFromUser(view.getCurrentlySelectedTabIndex(), view.getEditorText());
		E4mooc.cState.storeCursorPositionFromUser(view.getCurrentlySelectedTabIndex(), view.getCurrentCursorPosition());
		
		eventBus.fireEvent(new TabSelectedEvent(tabIndex));
		
		// get the example for the selected tab and load it into the browser
//		String fileContent = E4mooc.cState.getContentOfFile(tabIndex);
//		view.setEditorText(fileContent);
//		view.setCurrentCursorPosition(E4mooc.cState.getCursorPositionForFile(tabIndex));
//		view.updateSelectedTab(tabIndex);
	}
	
	
	public void go() {
		view.addAceEditor("Loading project...");
	}
	

	@Override
	public void onReloadBtnClick(int tabIndex) {
		// to reload the original version of the example of the current tab
		// we a) inform the client state to delete the user's version
		E4mooc.cState.deleteFromStorage(tabIndex);
		
		// and b) send an event that represents a tab-click for the current tab
		eventBus.fireEvent(new TabSelectedEvent(tabIndex));
	}

}
