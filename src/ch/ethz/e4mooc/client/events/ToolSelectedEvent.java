/**
 * 
 */
package ch.ethz.e4mooc.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import ch.ethz.e4mooc.client.events.ToolSelectedEvent.ToolSelectedEventHandler;

/**
 * This event is send if a tool was selected
 * by the user, e.g. through the navigation bar
 * or by entering the ULR token for a specific
 * tool.
 * 
 * @author hce
 *
 */
public class ToolSelectedEvent extends Event<ToolSelectedEventHandler> {

	public interface ToolSelectedEventHandler extends EventHandler {
		void toolSelectedAction(ToolSelectedEvent event);
	}
	
	/** the name of the tool that was selected */
	private final String toolName;
	
	
	public ToolSelectedEvent(String toolName) {
		this.toolName = toolName;
	}
	
	public static final Type<ToolSelectedEventHandler> TYPE = new Type<ToolSelectedEventHandler>();

	@Override
	public Type<ToolSelectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ToolSelectedEventHandler handler) {
		handler.toolSelectedAction(this);
	}
	
	/**
	 * Returns the name of the tool which triggered this event.
	 * @return a tool name
	 */
	public String getToolName( ) {
		return toolName;
	}
}
