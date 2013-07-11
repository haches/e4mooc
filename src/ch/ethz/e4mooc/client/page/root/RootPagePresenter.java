/**
 * 
 */
package ch.ethz.e4mooc.client.page.root;


import java.util.LinkedList;

import ch.ethz.e4mooc.client.E4mooc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The presenter for the SimplePage widget.
 * 
 * @author hce
 *
 */
public class RootPagePresenter implements RootPageView.Presenter {

	private EventBus eventBus;
	private RootPageView view;
	
	
	public RootPagePresenter(EventBus eventBus, RootPageView view) {
		this.eventBus = eventBus;
		this.view = view;
		
		bind();
		
		addProjectNames();
	}
	
	/**
	 * Binds all the handlers.
	 */
	private void bind() {
		// assign this presenter to the view
		view.setPresenter(this);
	}
	
	private void addProjectNames() {
		E4mooc.projectService.getAllProjectNames(new AsyncCallback<LinkedList<String>>() {
			
			@Override
			public void onSuccess(LinkedList<String> result) {
				view.addProjectNames(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
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
	}
}
