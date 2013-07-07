/**
 * 
 */
package ch.ethz.e4mooc.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import ch.ethz.e4mooc.client.events.TabSelectedEvent.TabSelectedEventHandler;


/**
 * @author hce
 *
 */
public class TabSelectedEvent extends Event<TabSelectedEventHandler> {

	private int tabIndex;
	
	public interface TabSelectedEventHandler extends EventHandler {
		void tabSelectedClick(TabSelectedEvent event);
	}
	
	public TabSelectedEvent(int tabIndex) {
		this.tabIndex = tabIndex;
	}	
		
	public static final Type<TabSelectedEventHandler> TYPE = new Type<TabSelectedEventHandler>();
	
	public int getTabIndex() {
		return tabIndex;
	}
	
	@Override
	public Type<TabSelectedEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(TabSelectedEventHandler handler) {
		handler.tabSelectedClick(this);
	}
}
