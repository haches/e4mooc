/**
 * 
 */
package ch.ethz.e4mooc.client.page.eiffel;

import ch.ethz.e4mooc.client.E4mooc;
import ch.ethz.e4mooc.client.events.CompilationStartedEvent;
import ch.ethz.e4mooc.client.events.ResultEvent;
import ch.ethz.e4mooc.client.events.ResultEvent.ResultEventHandler;
import ch.ethz.e4mooc.client.events.TabSelectedEvent;
import ch.ethz.e4mooc.client.widgets.cmdline.CmdLinePresenter;
import ch.ethz.e4mooc.client.widgets.editor.EditorPresenter;
import ch.ethz.e4mooc.shared.ProjectModelDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The presenter for the SimplePage widget.
 * 
 * @author hce
 *
 */
public class EiffelPagePresenter implements EiffelPageView.Presenter {

	private EventBus eventBus;
	private EiffelPageView view;

	private EditorPresenter editorPresenter;
	private CmdLinePresenter cmdLinePresenter;
	
	public EiffelPagePresenter(EventBus eventBus, EiffelPageView view, String projectName) {
		this.eventBus = eventBus;
		this.view = view;
		
		// Parent view creates child view
		// Parent presenter creates child presenter, giving its child view retrieved from the parent view
		this.editorPresenter = new EditorPresenter(this.view.getEditorView(), this.eventBus);
		this.cmdLinePresenter = new CmdLinePresenter(this.view.getCmdLineView(), this.eventBus);

		// request the project model from the server
		requestProjectModel(projectName);
		
		// bind all event listeners and the presenter
		bind();
	}
	
	
	private void requestProjectModel(String projectName) {
		E4mooc.projectService.getProject(projectName, new AsyncCallback<ProjectModelDTO>() {
			
			@Override
			public void onSuccess(ProjectModelDTO result) {
				// store the project model in the client state
				E4mooc.cState.storeProjectModel(result);
				// apply the project model
				applyProjectModel(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	/**
	 * Binds all the handlers.
	 */
	private void bind() {
		// assign this presenter to the view
		view.setPresenter(this);
		
		// set user specific background color and height of output box
		view.setOutputBoxHeight(E4mooc.cState.getUserOutputBoxHeight());
		view.setBackgroundColor(E4mooc.cState.getUserBackgroundColor());
		
//		// handler for events that indicate that a tool model is available
//		eventBus.addHandler(ToolModelAvailableEvent.TYPE, new ToolModelAvailableEvent.ToolModelAvailableEventHandler() {
//			
//			@Override
//			public void toolModelAvailableAction(ToolModelAvailableEvent event) {
//				// apply the tool model
//				applyToolModel(event.getToolModel());
//			}
//		});

		
		// handler for events that indicate a result has been returned by the server
		ResultEvent.register(eventBus, new ResultEventHandler() {
			
			@Override
			public void processResult(boolean successful, String resultString) {
				// we don't use the information about successful here because
				// if the the call to the server was not successful, the event's text
				// is already adapted.
				// the boolean successful might be interesting for other stuff in the future maybe 
				view.setOutputText(resultString);
			}
		});
		
		// handler for events which indicate that a new "Run..." was started by the user
		eventBus.addHandler(CompilationStartedEvent.TYPE, new CompilationStartedEvent.CompilationStartedEventHandler() {
			
			@Override
			public void toolExecutionStartedAction() {
				// the output area should be cleaned
				view.setOutputText("Waiting for results...");
			}
		});
		
		// if a different tab was selected, we want to clear the output-area from any previous results
		eventBus.addHandler(TabSelectedEvent.TYPE, new TabSelectedEvent.TabSelectedEventHandler() {
			
			@Override
			public void tabSelectedClick(TabSelectedEvent event) {
				view.setDefaultOutputText();
			}
		});
	}
	
	/**
	 * Apply the project model.
	 * @param pm the project model
	 */
	private void applyProjectModel(ProjectModelDTO pm) {
		// set the editor mode to eiffel
		editorPresenter.setEditorMode("eiffel");
	
		// add the files
		editorPresenter.addFiles(pm.getFileNames());
		
		// clear the output-text area and set the default text
		view.setDefaultOutputText();
	}
	
	/**
	 * Calling this method will add the view which belongs 
	 * to the presenter to the given container.
	 * @param container the container to which the view shall be added
	 */
	public void go(final HasWidgets container) {
		container.clear();
		// the view has a "asWidget()" method because the interface inherits "isWidget".
		// The ViewImpl implements the methods by inheriting from Composite which inherits from "Widget".
		container.add(view.asWidget());
		
		// instantiate the Ace editor; but only after the editor view has been initialized (it sets the ID for for the editor)
		editorPresenter.go();
		
		// cmdLinePresenter.go() // doesn't implement a go() method
	}
	
}
