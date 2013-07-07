/**
 * 
 */
package ch.ethz.e4mooc.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A ToolModel represents a command-line tool and
 * all data that goes together with that tool to make
 * it usable through the ComCom web page.
 * 
 * @author hce
 *
 */
public class ToolModel implements Serializable, Comparable<ToolModel> {
	
	/** Generated id */
	private static final long serialVersionUID = 6517231031431018482L;

	/** Map that holds key/value pairs for all the properties of a tool */
	private Map<ToolModelProperties.Tool, String> toolMap;
	
	/** A list of example models for this tool */
	private List<ExampleModel> examples;
	
	/** Empty constructor for serialization, don't use otherwise. */
	public ToolModel() {	
	}
	
	/**
	 * Constructs a new ToolModel.
	 * @param toolNumber the number of the tool, defining the position of the tool in the webpages navigation bar
	 * @param name the name of the tool
	 * @param headline a headline for the description
	 * @param descriptionText a description of the tool; this can contain HTML
	 * @param image the logo image to be shown with this tool
	 * @param url the URL of the webpage belonging to the tool
	 * @param email the email address of the developer of the tool
	 * @param executable the name of the executable (e.g. "boogaloo.exe")
	 * @param version the version of the tool
	 * @param fileType the file ending used by the too (e.g. "bpl")
	 * @param editorMode the mode that should be used by the editor when running this tool
	 * @param lineComment the characters used to start a single-line comment 
	 * @param supportsHtmlOutput does the tool support the generation of HTML output
	 * @param argHtmlOutput the argument to make the tool provide the output in HTML format
	 * @param argTextOutput the argument to make th tool provide the output in TEXT format
	 * @param argPrefix the argument string to put before the user's argument
	 * @param argPostfix the argument string to put after the user's argument
	 */
	public ToolModel(String toolNumber, String name, String headline,
			String descriptionText, String descriptionHtml, 
			String image, String url, String email,
			String executable, String version, String fileType, ToolModelProperties.EditorMode editorMode, 
			String lineComment, String supportsHtmlOutput,
			String argHtmlOutput, String argTextOutput, String argPrefix, String argPostfix) {
		toolMap = new HashMap<ToolModelProperties.Tool, String>();
		examples = new LinkedList<ExampleModel>();
		
		toolMap.put(ToolModelProperties.Tool.TOOLNUMBER, toolNumber);
		toolMap.put(ToolModelProperties.Tool.NAME, name);
		toolMap.put(ToolModelProperties.Tool.HEADLINE, headline);
		toolMap.put(ToolModelProperties.Tool.DESCRIPTION, descriptionText);
		toolMap.put(ToolModelProperties.Tool.DESCRIPTIONHTML, descriptionHtml);
		toolMap.put(ToolModelProperties.Tool.IMAGE, image);
		toolMap.put(ToolModelProperties.Tool.URL, url);
		toolMap.put(ToolModelProperties.Tool.EMAIL, email);
		toolMap.put(ToolModelProperties.Tool.EXECUTABLE, executable);
		toolMap.put(ToolModelProperties.Tool.VERSION, version);
		toolMap.put(ToolModelProperties.Tool.FILETYPE, fileType);
		toolMap.put(ToolModelProperties.Tool.EDITORMODE, editorMode.toString());
		toolMap.put(ToolModelProperties.Tool.LINECOMMENT, lineComment);
		toolMap.put(ToolModelProperties.Tool.SUPPORTSHTMLOUTPUT, supportsHtmlOutput);
		toolMap.put(ToolModelProperties.Tool.ARGHTMLOUTPUT, argHtmlOutput);
		toolMap.put(ToolModelProperties.Tool.ARGTEXTOUTPUT, argTextOutput);
		toolMap.put(ToolModelProperties.Tool.ARGPREFIX, argPrefix);
		toolMap.put(ToolModelProperties.Tool.ARGPOSTFIX, argPostfix);		
	}
	
	public void addExampleModel(ExampleModel example) {
		examples.add(example);
	}
	
	/** @return the tool number of the tool */
	public String getToolNumber() {
		return toolMap.get(ToolModelProperties.Tool.TOOLNUMBER);
	}
	
	/** @return the name of a tool */
	public String getName() {
		return toolMap.get(ToolModelProperties.Tool.NAME);
	}
	
	/** @return the headline of a tool */
	public String getHeadline() {
		return toolMap.get(ToolModelProperties.Tool.HEADLINE);
	}
	
	/** @return the description of a tool */
	public String getDescription() {
		return toolMap.get(ToolModelProperties.Tool.DESCRIPTION);
	}
	
	/** @return the description of a tool in HTML format */
	public String getDescriptionHtml() {
		return toolMap.get(ToolModelProperties.Tool.DESCRIPTIONHTML);
	}
	
	/** @return the image of the tool */
	public String getImage() {
		return toolMap.get(ToolModelProperties.Tool.IMAGE);
	}
	
	/** @return the url of the webpage of a tool */
	public String getUrl() {
		return toolMap.get(ToolModelProperties.Tool.URL);
	}
	
	/** @return the Email address of a tool's contact person */
	public String getEmail() {
		return toolMap.get(ToolModelProperties.Tool.EMAIL);
	}
	
	/** @return the name of the executable of the tool */
	public String getExecutable() {
		return toolMap.get(ToolModelProperties.Tool.EXECUTABLE);
	}
	
	/** @return the version of the tool */
	public String getVersion() {
		return toolMap.get(ToolModelProperties.Tool.VERSION);
	}
	
	/** @return the file type used by the tool */
	public String getFileType() {
		return toolMap.get(ToolModelProperties.Tool.FILETYPE);
	}
	
	/** @return the mode the editor should use */
	public String getEditorMode() {
		return toolMap.get(ToolModelProperties.Tool.EDITORMODE);
	}
	
	/** @return the characters of how a single-line comments starts */
	public String getLineComment() {
		return toolMap.get(ToolModelProperties.Tool.LINECOMMENT);
	}
	
	/** @return true is the tool is able to generate HTML formatted output */
	public boolean supportsHtmlOutput() {
		String booleanString = toolMap.get(ToolModelProperties.Tool.SUPPORTSHTMLOUTPUT);
		if(booleanString.equals("true"))
			return true;
		
		return false;
	}
	
	/** @return the argument used such that the tool generates HTML output */
	public String getArgumentForHtmlOutput() {
		return toolMap.get(ToolModelProperties.Tool.ARGHTMLOUTPUT);
	}
	
	/** @return the argument used such that the tool generates plain TEXT output */
	public String getArgumentForTextOutput() {
		return toolMap.get(ToolModelProperties.Tool.ARGTEXTOUTPUT);
	}
	
	/** @return the string that should be before the argument provided by the user */
	public String getArgumentPrefix() {
		return toolMap.get(ToolModelProperties.Tool.ARGPREFIX);
	}
	
	/** @return the string that should be after the argument provided by the user */
	public String getArgumentPostFix() {
		return toolMap.get(ToolModelProperties.Tool.ARGPOSTFIX);
	}
	
	/** @return a list containing all example models for this tool */
	public List<ExampleModel> getExamples() {
		return examples;
	}

	@Override
	public int compareTo(ToolModel o) {
		return this.getToolNumber().compareTo(o.getToolNumber());
	}
}
