/**
 * 
 */
package ch.ethz.e4mooc.client.events;

import ch.ethz.e4mooc.client.events.ResultEvent.ResultEventHandler;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author hce
 *
 */
public class ResultEvent extends Event<ResultEventHandler> {

	public interface ResultEventHandler extends EventHandler {
		/**
		 * Processing of an ResultEvent.
		 * @param successful is true is the execution of the command-line program was successful
		 * @param resultString the string that was returned by the command-line program
		 */
		void processResult(boolean successful, String resultString);
	}

	private boolean successful;
	private String resultString;
	
	public ResultEvent(boolean successful, String resultString) {
		this.successful = successful;
		this.resultString = resultString;
	}
	
	public static final Type<ResultEventHandler> TYPE = new Type<ResultEventHandler>(); 
	
    /**
     * Register a handler for MessageReceivedEvent events on the eventbus.
     * 
     * @param eventBus the {@link EventBus}
     * @param handler an {@link ResultEvent.ResultEventHandler} instance
     * @return an {@link HandlerRegistration} instance
     */
    public static HandlerRegistration register(EventBus eventBus, ResultEvent.ResultEventHandler handler) {
      return eventBus.addHandler(TYPE, handler);
    } 
	
	@Override
	public Type<ResultEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ResultEventHandler handler) {
		handler.processResult(successful, resultString);
	}
}
