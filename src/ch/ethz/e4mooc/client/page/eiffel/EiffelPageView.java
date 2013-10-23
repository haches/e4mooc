/**
 * 
 */
package ch.ethz.e4mooc.client.page.eiffel;


import ch.ethz.e4mooc.client.IPresenter;
import ch.ethz.e4mooc.client.IView;
import ch.ethz.e4mooc.client.widgets.cmdline.CmdLineView;
import ch.ethz.e4mooc.client.widgets.editor.EditorView;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * The interface for a SimplePage view.
 * 
 * @author hce
 *
 */
public interface EiffelPageView extends IView<EiffelPageView.Presenter>, IsWidget {
	
	/**
	 * The interface of the presenter of the SimplePage.
	 * 
	 * @author hce
	 *
	 */
	public interface Presenter extends IPresenter {
		
	}
	
	/**
	 * Returns the view of the editor widget.
	 * @return the view of the editor widget
	 */
	EditorView getEditorView();
			
	/**
	 * Returns the view of the command line widget.
	 * @return the CmdLineView
	 */
	CmdLineView getCmdLineView();
	
	/**
	 * Sets the text of the output text area.
	 * If the text is HTML, it will be rendered as HTML in the output area.
	 * @param text the text to display
	 */
	void setOutputText(String text);
	
	/**
	 * Clears the output area and sets its the default text.
	 */
	void setDefaultOutputText();
	
	
	/**
	 * Sets the height of the output box.
	 * @param height height value in pixels
	 */
	void setOutputBoxHeight(int height);
	
	
	/**
	 * Sets the background color of the Eiffel project page.
	 * @param color a hex-formated string
	 */
	void setBackgroundColor(String color);
}
