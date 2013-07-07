/**
 * 
 */
package ch.ethz.e4mooc.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A model for an example that would come with a tool.
 * An example consists of a name, the code to display in editor,
 * the corresponding command-line argument, and a short description
 * of what that command-line argument does.
 * 
 * @author hce
 *
 */
public class ExampleModel implements Serializable {

	/** Generated id */
	private static final long serialVersionUID = 1991231802926528868L;
	
	/** Map to store the example properties */
	private Map<ToolModelProperties.Example, String> exampleMap;
	
	/** Empty constructor is needed for serialization. Don't use otherwise. */
	public ExampleModel() {}
	
	/**
	 * Constructor for a new ExampleModel.
	 *  
	 * @param name the name to be displayed for this example
	 * @param filenName the acutal file name used on the server
	 * @param fileContent the content of the example (i.e. the code)
	 * @param argument the default argument for running this example (excluding the filename)
	 * @param argDescription a short description of what the arguments mean
	 * @param enableArInput is the user allowed to input arguments to tool
	 */
	public ExampleModel(String name, String fileName, String fileContent, String argument, String argDescription, boolean enableArgInput) {
		exampleMap = new HashMap<ToolModelProperties.Example, String>();
		
		exampleMap.put(ToolModelProperties.Example.NAME, name);
		exampleMap.put(ToolModelProperties.Example.FILE, fileName);
		exampleMap.put(ToolModelProperties.Example.FILECONTENT, fileContent);
		exampleMap.put(ToolModelProperties.Example.ARG, argument);
		exampleMap.put(ToolModelProperties.Example.ARGDESCRIPTION, argDescription);
		exampleMap.put(ToolModelProperties.Example.ENABLEARGINPUT, String.valueOf(enableArgInput));
	}
	
	/** @return the name of the example */
	public String getName() {
		return exampleMap.get(ToolModelProperties.Example.NAME);
	}
	
	/** @return the name of the file used on the server */
	public String getFileName() {
		return exampleMap.get(ToolModelProperties.Example.FILE);
	}
	
	public String getFileContent() {
		return exampleMap.get(ToolModelProperties.Example.FILECONTENT);
	}
		
	/** @return the default argument for the example */
	public String getArgument() {
		return exampleMap.get(ToolModelProperties.Example.ARG);
	}
	
	/** @return the description for the default argument */
	public String getArgDescription() {
		return exampleMap.get(ToolModelProperties.Example.ARGDESCRIPTION);
	}
	
	/** @return is the user allowed to input arguments to tool */
	public boolean isEnableArgInput() {
		return Boolean.parseBoolean(exampleMap.get(ToolModelProperties.Example.ENABLEARGINPUT));
	}
	
}
