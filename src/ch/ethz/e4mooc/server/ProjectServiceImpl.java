package ch.ethz.e4mooc.server;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import ch.ethz.e4mooc.client.ProjectService;
import ch.ethz.e4mooc.server.util.ProjectModel;
import ch.ethz.e4mooc.server.util.ServerProperties;
import ch.ethz.e4mooc.server.util.ServerState;
import ch.ethz.e4mooc.shared.ProjectModelDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * Implementation of the service that provides the data about
 * projects.
 * 
 * @author hce
 *
 */
public class ProjectServiceImpl extends RemoteServiceServlet implements ProjectService {

	/** Generated id */
	private static final long serialVersionUID = -4203949441332964358L;
	
	/** Logger for this class */
	private static final Logger LOGGER = Logger.getLogger(ch.ethz.e4mooc.server.ProjectServiceImpl.class.getName());
	
	/** The file path were all e4Mooc Eiffel projects are stored. */
	private String e4moocFilePath;
	/** file separator */
	private final String SEP = System.getProperty("file.separator");
	
	/**
	 * This method is called when the service is load by the web server.
	 * It reads all the Eiffel projects stored on the server.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		
		e4moocFilePath = ServerProperties.E4MOOC_PROJECTS_FOLDER.toString();
//		
//		// the folders within the root folder are the Eiffel projects; folder names equal project names
//		File rootFolder = new File(e4moocFilePath);		
//		File[] projectFolders = rootFolder.listFiles((FileFilter) FileFilterUtils.directoryFileFilter());
//		for(File f: projectFolders) {
//			
//			// generate a project model for the current project
//			ProjectModel pm = new ProjectModel(f.getName());
//			
//			// get all the Eiffel files (i.e. the files ending with ".e")
//			Collection<File> eiffelFiles = FileUtils.listFiles(f, new String[] {"e"}, true);
//			for(File ef: eiffelFiles) {
//				
//				String fileContent = "Server reports: there was a problem while reading the file content.";
//				
//				try {
//					fileContent = FileUtils.readFileToString(ef);
//				} catch (IOException e) {
//					e.printStackTrace();
//				} finally {
//					
//					// get the relative path of the Eiffel file. We need this path info when replacing the original
//					// Eiffel file with the one that was modified by the user
//					String relativeFilePath = getRelativePath(ef.getPath(), f.getPath(), SEP);
//
//					
//					// add the file and it's content the the project model
//					pm.addFileName(ef.getName(), relativeFilePath);
//					pm.addFile(ef.getName(), fileContent);
//				}
//			}
//			
//			// get the ecf file of this project and store it in the project model
//			// note: we should only have a single ecf file. If there are multiple, the project model will only keep the last one.
//			Collection<File> ecfFiles = FileUtils.listFiles(f, new String[] {"ecf"}, true);
//			for(File ecfFile: ecfFiles) {
//				pm.setEcfFile(getRelativePath(ecfFile.getPath(), f.getParent(), SEP));
//			}
//			
//			// store the project model in the server state
//			ServerState.getState().addProjectModel(pm);
//		}
	}
	
	
	@Override
	public LinkedList<String> getAllProjectNames() {
		LinkedList<String> result;
		result = new LinkedList<String>(ServerState.getState().getProjectNames());
		return result;
	}

	@Override
	public ProjectModelDTO getProject(String projectName) {		
		return ServerState.getState().getProjectModel(projectName).getProjectModelDTO();
	}

	@Override
	public boolean hasProject(String projectName) {
		if(getAllProjectNames().contains(projectName))
			return true;
		return false;
	}

	/**
     * Get the relative path from one file to another, specifying the directory separator. 
     * If one of the provided resources does not exist, it is assumed to be a file unless it ends with '/' or
     * '\'.
     * 
     * @param targetPath targetPath is calculated to this file
     * @param basePath basePath is calculated from this file
     * @param pathSeparator directory separator. The platform default is not assumed so that we can test Unix behavior when running on Windows (for example)
     * @throws Exception if path seperator is unknown or no common path element was found
     * @return the relative path
     */
//    private String getRelativePath(String targetPath, String basePath, String pathSeparator) {
//
//        // Normalize the paths
//        String normalizedTargetPath = FilenameUtils.normalizeNoEndSeparator(targetPath);
//        String normalizedBasePath = FilenameUtils.normalizeNoEndSeparator(basePath);
//
//        // Undo the changes to the separators made by normalization
//        if (pathSeparator.equals("/")) {
//            normalizedTargetPath = FilenameUtils.separatorsToUnix(normalizedTargetPath);
//            normalizedBasePath = FilenameUtils.separatorsToUnix(normalizedBasePath);
//
//        } else if (pathSeparator.equals("\\")) {
//            normalizedTargetPath = FilenameUtils.separatorsToWindows(normalizedTargetPath);
//            normalizedBasePath = FilenameUtils.separatorsToWindows(normalizedBasePath);
//
//        } else {
//           LOGGER.log(Level.SEVERE, "Unrecognised dir separator '" + pathSeparator + "'");
//        }
//
//        String[] base = normalizedBasePath.split(Pattern.quote(pathSeparator));
//        String[] target = normalizedTargetPath.split(Pattern.quote(pathSeparator));
//
//        // First get all the common elements. Store them as a string,
//        // and also count how many of them there are.
//        StringBuffer common = new StringBuffer();
//
//        int commonIndex = 0;
//        while (commonIndex < target.length && commonIndex < base.length
//                && target[commonIndex].equals(base[commonIndex])) {
//            common.append(target[commonIndex] + pathSeparator);
//            commonIndex++;
//        }
//
//        if (commonIndex == 0) {
//            // No single common path element. This most
//            // likely indicates differing drive letters, like C: and D:.
//            // These paths cannot be relativized.
//            LOGGER.log(Level.SEVERE, "No common path element found for '" + normalizedTargetPath + "' and '" + normalizedBasePath + "'");
//        }   
//
//        // The number of directories we have to backtrack depends on whether the base is a file or a dir
//        // For example, the relative path from
//        //
//        // /foo/bar/baz/gg/ff to /foo/bar/baz
//        // 
//        // ".." if ff is a file
//        // "../.." if ff is a directory
//        //
//        // The following is a heuristic to figure out if the base refers to a file or dir. It's not perfect, because
//        // the resource referred to by this path may not actually exist, but it's the best I can do
//        boolean baseIsFile = true;
//
//        File baseResource = new File(normalizedBasePath);
//
//        if (baseResource.exists()) {
//            baseIsFile = baseResource.isFile();
//
//        } else if (basePath.endsWith(pathSeparator)) {
//            baseIsFile = false;
//        }
//
//        StringBuffer relative = new StringBuffer();
//
//        if (base.length != commonIndex) {
//            int numDirsUp = baseIsFile ? base.length - commonIndex - 1 : base.length - commonIndex;
//
//            for (int i = 0; i < numDirsUp; i++) {
//                relative.append(".." + pathSeparator);
//            }
//        }
//        relative.append(normalizedTargetPath.substring(common.length()));
//        return relative.toString();
//    }
}
