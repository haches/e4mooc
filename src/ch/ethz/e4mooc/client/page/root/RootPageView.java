/**
 * 
 */
package ch.ethz.e4mooc.client.page.root;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.ethz.e4mooc.client.IPresenter;
import ch.ethz.e4mooc.client.IView;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * The interface for a SimplePage view.
 * 
 * @author hce
 *
 */
public interface RootPageView extends IView<RootPageView.Presenter>, IsWidget {
	
	/**
	 * The interface of the presenter of the SimplePage.
	 * 
	 * @author hce
	 *
	 */
	public interface Presenter extends IPresenter {
		
	}
	
	/**
	 * Adds a list of given projects names as links to the page.
	 * @param projectNames the list of project names
	 */
	void addProjectNames(LinkedList<String> projectNames);
	
	/**
	 * The view has a container where the main content (welcome page, tool page)
	 * should be added. This method returns that container.
	 * @return the container were the content widgets should be added
	 */
	HasWidgets getContentContainer();
}
