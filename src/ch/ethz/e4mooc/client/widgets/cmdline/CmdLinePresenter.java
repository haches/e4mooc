/**
 * 
 */
package ch.ethz.e4mooc.client.widgets.cmdline;

import java.util.HashMap;

import ch.ethz.e4mooc.client.E4mooc;
import ch.ethz.e4mooc.client.events.CompilationStartedEvent;
import ch.ethz.e4mooc.client.events.ResultEvent;
import ch.ethz.e4mooc.client.events.TabSelectedEvent;
import ch.ethz.e4mooc.shared.CompilationResultDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;


/**
 * @author hce
 *
 */
public class CmdLinePresenter implements CmdLineView.CmdLinePresenter {

	/** the event bus of the app */
	private EventBus eventBus;
	/** the view associated with this presenter */
	private CmdLineView view;
	
	/** the time stamp of the last successful compilation */
	private String timeStamp;
	
	public CmdLinePresenter(CmdLineView view, EventBus eventBus) {
		this.view = view;
		this.eventBus = eventBus;
		this.timeStamp = "";
		
		// we disable the "Run" button the first time around because the user needs to compile first
		this.view.setRunButtonEnabled(false, "Run...");
		
		bind();
	}
	
	
	private void bind() {
		// bind this presenter to its view
		view.setPresenter(this);
				
		// if a tab is selected, we need to update the argument and it's description
		eventBus.addHandler(TabSelectedEvent.TYPE, new TabSelectedEvent.TabSelectedEventHandler() {
			
			@Override
			public void tabSelectedClick(TabSelectedEvent event) {				
//				view.setArgument(E4mooc.cState.getExampleArgument(event.getTabIndex()));
//				view.setHint(E4mooc.cState.getExampleArgumentDescription(event.getTabIndex()));
//				view.setArgInputBoxVisible(E4mooc.cState.isExampleArgumentInputBoxVisible(event.getTabIndex()));
			}
		});
	}
	
	
	@Override
	public void onCompileBtnClick() {
		
		// disable the compile button
		//view.setCompileButtonEnabled(false);
		// disable the run button
		view.setCompileBtnEnabled(false, "Working");
		view.setRunButtonEnabled(false, "Run...");
		
		// send out an event to other widgets that a new compilation was started
		eventBus.fireEvent(new CompilationStartedEvent());
		
		String projectName = E4mooc.cState.getProjectName();
		HashMap<String, String> inputFiles = E4mooc.cState.getContentOfAllFiles();
		
		E4mooc.execService.compile(projectName, inputFiles, timeStamp, new AsyncCallback<CompilationResultDTO>() { 
			
			@Override
			public void onSuccess(CompilationResultDTO result) {
				// store the time stamp of the compilation
				timeStamp = result.getTimeStamp();
				
				if(result.wasCompilationSuccessful()) {
					// display result in output area
					eventBus.fireEvent(new ResultEvent(true, result.getCompilerOutput()));
					// enable both buttons
					view.setCompileBtnEnabled(true, "Compile");
					view.setRunButtonEnabled(true, "Run...");					
				} else {
					// display result in output area
					eventBus.fireEvent(new ResultEvent(false, result.getCompilerOutput()));
					// enable compile but not the run button
					view.setCompileBtnEnabled(true, "Compile");
					view.setRunButtonEnabled(false, "Run...");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// the output area should show some error message
				eventBus.fireEvent(new ResultEvent(false, caught.getMessage()));//"An unkown error occured while calling the command line tool. Please try again."));
				// enable the run button
				view.setCompileBtnEnabled(true, "Compile");
				view.setRunButtonEnabled(true, "Run...");
			}
		});
	}


	@Override
	public void onRunBtnClick() {
		
		// we disable the buttons while the programm is running
		view.setCompileBtnEnabled(false, "Compile");
		view.setRunButtonEnabled(false, "Running");
		
		// send out an event to other widgets that a new compilation was started (even though it's not really a compilation here)
		// the EiffelPresenter will the clear the output 
		eventBus.fireEvent(new CompilationStartedEvent());		
		

		String projectName = E4mooc.cState.getProjectName();
		E4mooc.execService.execute(projectName, timeStamp, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// we fire an event that a new result is available
				eventBus.fireEvent(new ResultEvent(true, result));
				
				view.setCompileBtnEnabled(true, "Compile");
				view.setRunButtonEnabled(true, "Run...");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// the output area should show some error message
				eventBus.fireEvent(new ResultEvent(false, caught.getMessage()));//"An unkown error occured while calling the command line tool. Please try again."));
				// enable the run button
				view.setCompileBtnEnabled(true, "Compile");
				view.setRunButtonEnabled(true, "Run...");
			}
		});
		
	}
}
