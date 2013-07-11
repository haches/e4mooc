/**
 * 
 */
package ch.ethz.e4mooc.client.events;

import ch.ethz.e4mooc.client.events.ToolModelAvailableEvent.ToolModelAvailableEventHandler;
import ch.ethz.e4mooc.shared.ToolModel;

import com.google.web.bindery.event.shared.Event;

/**
 * This event is fired by the ClientState
 * once it has a ToolModel available.
 * During the 
 * 
 * @author hce
 *
 */
public class ToolModelAvailableEvent extends Event<ToolModelAvailableEventHandler> {

	public interface ToolModelAvailableEventHandler {
		
		/** the action that will be performed one a toolModel is available */
		void toolModelAvailableAction(ToolModelAvailableEvent event);
	}
	
	ToolModel tm;
	
	public ToolModelAvailableEvent(ToolModel tm) {
		this.tm = tm;
	}
	
	/** @return the ToolModel that is now available */
	public ToolModel getToolModel() {
		return tm;
	}
	
	public static final Type<ToolModelAvailableEventHandler> TYPE = new Type<ToolModelAvailableEventHandler>();
	
	@Override
	public Type<ToolModelAvailableEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ToolModelAvailableEventHandler handler) {
		handler.toolModelAvailableAction(this);
	}

}
