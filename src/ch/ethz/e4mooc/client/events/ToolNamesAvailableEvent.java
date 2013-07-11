/**
 * 
 */
package ch.ethz.e4mooc.client.events;

import java.util.List;

import ch.ethz.e4mooc.client.events.ToolNamesAvailableEvent.ToolNamesAvailableEventHandler;

import com.google.web.bindery.event.shared.Event;

/**
 * This event is fired by the ClientState
 * once it has all the tool names available.
 * The event itself contains a list of 
 * of the tool names.
 * 
 * @author hce
 *
 */
public class ToolNamesAvailableEvent extends Event<ToolNamesAvailableEventHandler> {

	public interface ToolNamesAvailableEventHandler {
		
		/** the action that will be performed one a toolModel is available */
		void toolModelAvailableAction(ToolNamesAvailableEvent event);
	}
	
	List<String> toolNames;
	
	public ToolNamesAvailableEvent(List<String> toolNames) {
		this.toolNames = toolNames;
	}
	
	/** @return the list of tool names that is now available */
	public List<String> getToolNames() {
		return toolNames;
	}
	
	public static final Type<ToolNamesAvailableEventHandler> TYPE = new Type<ToolNamesAvailableEventHandler>();
	
	@Override
	public Type<ToolNamesAvailableEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ToolNamesAvailableEventHandler handler) {
		handler.toolModelAvailableAction(this);
	}
}
