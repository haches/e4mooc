/**
 * 
 */
package ch.ethz.e4mooc.shared;

import java.io.Serializable;


public class ToolModelProperties implements Serializable{

	private static final long serialVersionUID = -1132918666207253494L;

	/**
	 * Enum for the keys used in the ComCom configuration file.
	 * 
	 * @author hce
	 *
	 */
	public enum Tool {
		
		TOOLNUMBER("toolNumber"),
		NAME("name"),
		HEADLINE("headline"),
		DESCRIPTION("descriptiontext"),
		DESCRIPTIONHTML("descriptionhtml"),
		IMAGE("image"),
		URL("url"),
		EMAIL("email"),
		EXECUTABLE("executable"),
		VERSION("version"),
		FILETYPE("filetype"),
		EDITORMODE("editormode"),
		LINECOMMENT("linecomment"),
		SUPPORTSHTMLOUTPUT("supportshtmloutput"),
		ARGHTMLOUTPUT("arghtmloutput"),
		ARGTEXTOUTPUT("argtextoutput"),
		ARGPREFIX("argprefix"),
		ARGPOSTFIX("argpostfix"),
		EXAMPLES("examples");

		private final String name;
		
		private Tool(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
	
	/**
	 * Editor Modes supported by ComCom.
	 * 
	 * @author hce
	 *
	 */
	public enum EditorMode {
		
		BOOGIE {
			@Override
			public String toString() {
				return "boogie";
			}
		},		
		JAVASCRIPT {
			@Override
			public String toString() {
				return "javascript";
			}
		},
		EIFFEL {
			@Override
			public String toString() {
				return "eiffel";
			}
		},
		QFIS {
			@Override
			public String toString() {
				return "qfis";
			}
		},
		TEXT {
			@Override
			public String toString() {
				return "text";
			}
		}
	}
	
	/**
	 * Keys used in the Comcom configuration file for examples.
	 * @author hce
	 *
	 */
	public enum Example {
		
		NAME("name"),
		FILE("file"),
		FILECONTENT("filecontent"),	
		ARG ("arg"),
		ARGDESCRIPTION("argdescription"),
		ENABLEARGINPUT("enablearginput");
		
		private final String name;
		
		private Example(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}		
	}
	
}
