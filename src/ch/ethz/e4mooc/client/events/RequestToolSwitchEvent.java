/**
 * 
 */
package ch.ethz.e4mooc.client.events;

import ch.ethz.e4mooc.client.events.RequestToolSwitchEvent.RequestToolSwitchEventHandler;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;

/**
 * This event is send to inform listeners
 * that a switch to a different tool should be
 * performed. This event is usually triggered
 * after a ToolSelectedEvent which, in contrast,
 * only signals that the user has selected a
 * different tool but does not trigger the 
 * switching to that tool yet.
 * 
 * @author hce
 *
 */
public class RequestToolSwitchEvent extends Event<RequestToolSwitchEventHandler> {

	public interface RequestToolSwitchEventHandler extends EventHandler {
		/**
		 * Action that should be performed upon a tool switch was instructed.
		 * @param event the tool switch event
		 */
		void toolSwitchRequestAction(RequestToolSwitchEvent event);
	}
	
	/** the name of the tool that was selected */
	private final String toolName;
	
	
	public RequestToolSwitchEvent(String toolName) {
		this.toolName = toolName;
	}
	
	public static final Type<RequestToolSwitchEventHandler> TYPE = new Type<RequestToolSwitchEventHandler>();

	@Override
	public Type<RequestToolSwitchEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestToolSwitchEventHandler handler) {
		handler.toolSwitchRequestAction(this);
	}
	
	/**
	 * Returns the name of the tool which to which should be switched.
	 * @return a tool name
	 */
	public String getToolName( ) {
		return toolName;
	}
}
