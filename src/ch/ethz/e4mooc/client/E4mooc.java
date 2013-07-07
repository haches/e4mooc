package ch.ethz.e4mooc.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class E4mooc implements EntryPoint {

	/** Create a remote service proxy to talk to the server-side execution service. */
	public static final ExecutionServiceAsync execService = GWT.create(ExecutionService.class);
	/** Create a remote service proxy to talk to the server-side project service. */
	public static final ProjectServiceAsync projectService = GWT.create(ProjectService.class);
	
	/** The client state */
	public static ClientState cState;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		
		EventBus eventBus = new SimpleEventBus();
		
		// initialize the client State
		cState = new ClientState(eventBus);

		AppController controller = new AppController(eventBus);
		controller.go();
	}
}
