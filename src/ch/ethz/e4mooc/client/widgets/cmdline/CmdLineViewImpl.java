/**
 * 
 */
package ch.ethz.e4mooc.client.widgets.cmdline;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author hce
 *
 */
public class CmdLineViewImpl extends Composite implements CmdLineView {

	private static CmdLineViewImplUiBinder uiBinder = GWT
			.create(CmdLineViewImplUiBinder.class);

	interface CmdLineViewImplUiBinder extends UiBinder<Widget, CmdLineViewImpl> {
	}

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public CmdLineViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button compileButton;
	@UiField
	Button runButton;
	//@UiField
	//TextBox argumentBox;
	//@UiField
	//SpanElement argDescription;
	
	/** Presenter for this view */
	CmdLinePresenter presenter;

	@UiHandler("compileButton")
	void onCompileBtnClick(ClickEvent e) {
		presenter.onCompileBtnClick();
	}
	
	@UiHandler("runButton")
	void onRunBtnClick(ClickEvent e) {
		presenter.onRunBtnClick();
	}
	
//	/**
//	 * Pressing enter on the argument-box will run the tool.
//	 * @param e
//	 */
//	@UiHandler("argumentBox")
//	void onKeyPress(KeyPressEvent e) {
//		if(e.getCharCode() == KeyCodes.KEY_ENTER)
//			presenter.executeRun();
//	}
	
	public void setText(String text) {
		
	}

	/**
	 * Gets invoked when the default constructor is called
	 * and a string is provided in the ui.xml file.
	 */
	public String getText() {
		return "";
	}

	@Override
	public void setRunButtonEnabled(boolean enabled, String disabledText) {
		runButton.setEnabled(enabled);
		if(enabled)
			runButton.setText("Run...");
		else
			runButton.setText(disabledText);
	}
	
	@Override
	public void setCompileBtnEnabled(boolean enabled, String disabledText) {
		compileButton.setEnabled(enabled);
		if(enabled)
			compileButton.setText("Compile");
		else
			compileButton.setText(disabledText);
	}

//	@Override
//	public String getArgument() {
//		return argumentBox.getText();
//	}

//	@Override
//	public void setArgument(String arg) {
//		argumentBox.setText(arg);
//	}

//	@Override
//	public void setHint(String hint) {
//		argDescription.setInnerText(hint);
//	}

//	@Override
//	public void setArgInputBoxVisible(boolean visible) {
//		argumentBox.setVisible(visible);
//	}

	@Override
	public void setPresenter(ch.ethz.e4mooc.client.widgets.cmdline.CmdLinePresenter presenter) {
		this.presenter = presenter;
	}

}
