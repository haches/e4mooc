/**
 * 
 */
package ch.ethz.e4mooc.shared;

import java.io.Serializable;

/**
 * A model that contains (only) the data to describe
 * a command line tool.
 * 
 * @author hce
 *
 */
public class ToolDescriptionModel implements Serializable {

	/** Generated id */
	private static final long serialVersionUID = 4280618167030335124L;
	
	private String toolName;
	private String toolDescription;
	private String toolLogo;
	
	/** Empty constructor is needed for serialization. Don't use otherwise. */
	public ToolDescriptionModel() {}

	/**
	 * Constructor
	 * @param toolName the name of the tool
	 * @param toolDescription the description of the tool (can be an HTML string)
	 * @param toolLogo the logo name of the tool
	 */
	public ToolDescriptionModel(String toolName, String toolDescription, String toolLogo) {
		this.toolName = toolName;
		this.toolDescription = toolDescription;
		this.toolLogo = toolLogo;
	}

	/**
	 * @return the toolName
	 */
	public String getToolName() {
		return toolName;
	}
	
	/**
	 * @return the description of the tool (can be an HTML string)
	 */
	public String getToolDescription() {
		return toolDescription;
	}
	
	/**
	 * @return the logo-name of the tool
	 */
	public String getToolLogo() {
		return toolLogo;
	}
}
