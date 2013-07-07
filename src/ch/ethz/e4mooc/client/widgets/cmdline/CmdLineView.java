/**
 * 
 */
package ch.ethz.e4mooc.client.widgets.cmdline;

import ch.ethz.e4mooc.client.IPresenter;
import ch.ethz.e4mooc.client.IView;

/**
 * @author hce
 *
 */
public interface CmdLineView extends IView<CmdLinePresenter> {

	public interface CmdLinePresenter extends IPresenter {
		/**
		 * This method is called when the compile button was clicked by the user.
		 */
		public void onCompileBtnClick();
		
		/**
		 * This method is called when the run button was clicked by the user.
		 */
		public void onRunBtnClick();
	}
	
	/**
	 * Enables or disables the "Compile" button, depending on the argument.
	 * If the button is disabled, it's text changes from "Compile" to "Working".
	 * @param enabled if true, "Run" button will be enabled
	 * @param btnText the text of the button
	 */	
	public void setCompileBtnEnabled(boolean enabled, String btnText);
	
	/**
	 * Enables or disables the "Run" button, depending on the argument.
	 * If the button is disabled, it's text changes from "Run..." to "Working".
	 * @param enabled if true, "Run" button will be enabled
	 * @param btnText the text of the button
	 */
	public void setRunButtonEnabled(boolean enabled, String btnText);
	
//	/** @return the arguments written in the text box */
//	public String getArgument();
//	
//	/** @param arg the argument that should be set to the text box */
//	public void setArgument(String arg);
	
//	/** @param hint the hint text*/
//	public void setHint(String hint);
	
//	/** @param visible if true, the arg input box will be visible */
//	public void setArgInputBoxVisible(boolean visible);
}
