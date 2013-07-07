/**
 * 
 */
package ch.ethz.e4mooc.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import ch.ethz.e4mooc.client.events.CompilationStartedEvent.CompilationStartedEventHandler;

/**
 * This event is send out once a user clicks 
 * the "Run..." button on the page.
 * It's not needed for making the call to server but
 * rather to inform all other widgets that a new "Run"
 * was started and that they might want to update accordingly.
 * 
 * @author hce
 *
 */
public class CompilationStartedEvent extends Event<CompilationStartedEventHandler> {

	/** Interface for the handler of ToolExecutionStartedEvents. */
	public interface CompilationStartedEventHandler extends EventHandler {
		
		/**
		 * The action that is performed if the user click a "Run..." button.
		 */
		public void toolExecutionStartedAction();
	}
	
	public static Type<CompilationStartedEventHandler> TYPE = new Type<CompilationStartedEventHandler>();
	
	@Override
	public Type<CompilationStartedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CompilationStartedEventHandler handler) {
		handler.toolExecutionStartedAction();
	}

}