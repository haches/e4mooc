/**
 * 
 */
package ch.ethz.e4mooc.server.util;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

/**
 * This class implements a watcher on the E4Mooc
 * directory. It makes the projects of the E4Mooc project
 * available in the server state. Changes, adding or deleting
 * a project will (almost) immediately be reflected in 
 * the server state.
 * 
 * @author hce
 *
 */
public class DirectoryWatcher extends Thread {

    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private final boolean recursive;
    private boolean trace = false;
        
	/** Logger for this class */
	private static final Logger LOGGER = Logger.getLogger(ch.ethz.e4mooc.server.util.DirectoryWatcher.class.getName());
	/** The file path were all e4Mooc Eiffel projects are stored. */
	private String e4moocFilePath;
	/** file separator */
	private final String SEP = System.getProperty("file.separator");
    
    

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
 
    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                LOGGER.log(Level.INFO, "Register: ", dir);
            } else {
                if (!dir.equals(prev)) {
                	LOGGER.log(Level.INFO, "Update: " + prev + " -> " + dir);
                }
            }
        }
        keys.put(key, dir);
    }
 
    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
 
    /**
     * Creates a WatchService and registers the given directory
     */
    public DirectoryWatcher() throws IOException {
        
    	e4moocFilePath = ServerProperties.E4MOOC_PROJECTS_FOLDER.toString();
   	    	
    	this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        this.recursive = true;
        
    }
 
    /**
     * Process all events for keys queued to the watcher
     */
    void processEvents() {
        for (;;) {
 
            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
 
            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
 
            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
 
                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }
 
                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);
 
                // print out event
                LOGGER.log(Level.INFO, event.kind().name() + ":" + child);
                
                // now we determine the project folder in which the change (i.e. added, deleted, modified) occurred
                // the changed project folder is than re-read and stored/removed from the server state
                
                // get the length of the E4Mooc path
                int lengthOfParentPath = e4moocFilePath.length();
                // the full path where the change occurred
                File filePathWhereChangeOccured = child.toFile();
                // we remove the common E4Mooc path and get the relative path (relative to E4MOOC) where the change occurred
                String relativePathOfChange = filePathWhereChangeOccured.toString().substring(lengthOfParentPath); 
                // we separate the folder names; the first folder name is the project name
                String [] pathElements = relativePathOfChange.split(Pattern.quote(SEP));
                // Note: the folder name is in array position 1 because of the leading SEP (e.g. as in: /project_folder/cluster_name/...)
                String modifiedProjectPath = e4moocFilePath + SEP + pathElements[1];
                
                // we generate/update/remove the project model for the changed project
                generateAndStoreProjectModel(modifiedProjectPath);
                
                
                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readable
                    }
                }
            }
 
            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
 
                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }
    
    
	/**
	 * This method is called when the service is load by the web server.
	 * It reads all the Eiffel projects stored on the server.
	 */
	public void init() {
		
		// the folders within the root folder are the Eiffel projects; folder names equal project names
		File rootFolder = new File(e4moocFilePath);		
		File[] projectFolders = rootFolder.listFiles((FileFilter) FileFilterUtils.directoryFileFilter());
		for(File f: projectFolders) {
			
			generateAndStoreProjectModel(f.getPath());
		}
	}

	/**
	 * Generates or updates a project model and stores it in the server state.
	 * If the server state contains a project model for the given path but the
	 * folder of the given path does no longer exist, it will be also removed from
	 * the server state.
	 * @param f the file that is the project folder
	 */
	@SuppressWarnings("unchecked")
	private void generateAndStoreProjectModel(String projectFilePath) {
		
		// a (possible empty) list contained the names of the files that should not be
		// shown to the user and thus not become part of the project model
		List<String> blackList = new LinkedList<String>();
		
		// generate a file for the projectFilePath
		File f = new File(projectFilePath);
		
		// is the project file path valid?
		if(!f.exists()) {
			// the project folder does not exists. So either the given
			// projectFilePath is wrong or the project existed once but
			// was deleted now. We check in the server state inventory
			// if it has such a project and if yes, we delete it
			ServerState.getState().removeProjectModel(f.getName());
			
		} else if(f.isDirectory()) { // we only handle folders as all projects are stored in folders at E4MOOC
			
			ProjectModel pm = null;
			
			try {
			
				// generate a project model for the current project
				pm = new ProjectModel(f.getName());
				
				// check if the project folder contains a black.list file
				Collection<File> blackListFiles = FileUtils.listFiles(f, new String [] {"list"}, true);
				for(File blackListFile: blackListFiles) {
					if(blackListFile.getName().equals("black.list")) {
						try {
							// get all the lines in the file
							List<String> linesInFile = FileUtils.readLines(blackListFile);
							// add each line to the black list
							blackList.addAll(linesInFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				// get all the Eiffel files (i.e. the files ending with ".e")
				Collection<File> eiffelFiles = FileUtils.listFiles(f, new String[] {"sq"}, true);
				for(File ef: eiffelFiles) {
					
					// check that the current file is not on the black list
					if(!blackList.contains(ef.getName())) {
					
						String fileContent = "Server reports: there was a problem while reading the file content.";
						
						try {
							fileContent = FileUtils.readFileToString(ef);
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							
							// get the relative path of the Eiffel file. We need this path info when replacing the original
							// Eiffel file with the one that was modified by the user
							String relativeFilePath = getRelativePath(ef.getPath(), f.getPath(), SEP);
			
							// add the file and it's content the the project model
							pm.addFileName(ef.getName(), relativeFilePath);
							pm.addFile(ef.getName(), fileContent);
						}
					}
				}
				
				// get the ecf file of this project and store it in the project model
				// note: we should only have a single ecf file. If there are multiple, the project model will only keep the last one.
				Collection<File> ecfFiles = FileUtils.listFiles(f, new String[] {"ecf"}, true);
				for(File ecfFile: ecfFiles) {
					pm.setEcfFile(getRelativePath(ecfFile.getPath(), f.getParent(), SEP));
				}
				
				ServerState.getState().addProjectModel(pm);
			
			}catch(Exception e) {
				LOGGER.log(Level.SEVERE, "Exception while trying to add a project model for: " + 
											projectFilePath + "\nNo project model will be added.");
				System.err.println(e.getMessage());
				e.printStackTrace();
			}				
		}
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
    private String getRelativePath(String targetPath, String basePath, String pathSeparator) {

        // Normalize the paths
        String normalizedTargetPath = FilenameUtils.normalizeNoEndSeparator(targetPath);
        String normalizedBasePath = FilenameUtils.normalizeNoEndSeparator(basePath);

        // Undo the changes to the separators made by normalization
        if (pathSeparator.equals("/")) {
            normalizedTargetPath = FilenameUtils.separatorsToUnix(normalizedTargetPath);
            normalizedBasePath = FilenameUtils.separatorsToUnix(normalizedBasePath);

        } else if (pathSeparator.equals("\\")) {
            normalizedTargetPath = FilenameUtils.separatorsToWindows(normalizedTargetPath);
            normalizedBasePath = FilenameUtils.separatorsToWindows(normalizedBasePath);

        } else {
           LOGGER.log(Level.SEVERE, "Unrecognised dir separator '" + pathSeparator + "'");
        }

        String[] base = normalizedBasePath.split(Pattern.quote(pathSeparator));
        String[] target = normalizedTargetPath.split(Pattern.quote(pathSeparator));

        // First get all the common elements. Store them as a string,
        // and also count how many of them there are.
        StringBuffer common = new StringBuffer();

        int commonIndex = 0;
        while (commonIndex < target.length && commonIndex < base.length
                && target[commonIndex].equals(base[commonIndex])) {
            common.append(target[commonIndex] + pathSeparator);
            commonIndex++;
        }

        if (commonIndex == 0) {
            // No single common path element. This most
            // likely indicates differing drive letters, like C: and D:.
            // These paths cannot be relativized.
            LOGGER.log(Level.SEVERE, "No common path element found for '" + normalizedTargetPath + "' and '" + normalizedBasePath + "'");
        }   

        // The number of directories we have to backtrack depends on whether the base is a file or a dir
        // For example, the relative path from
        //
        // /foo/bar/baz/gg/ff to /foo/bar/baz
        // 
        // ".." if ff is a file
        // "../.." if ff is a directory
        //
        // The following is a heuristic to figure out if the base refers to a file or dir. It's not perfect, because
        // the resource referred to by this path may not actually exist, but it's the best I can do
        boolean baseIsFile = true;

        File baseResource = new File(normalizedBasePath);

        if (baseResource.exists()) {
            baseIsFile = baseResource.isFile();

        } else if (basePath.endsWith(pathSeparator)) {
            baseIsFile = false;
        }

        StringBuffer relative = new StringBuffer();

        if (base.length != commonIndex) {
            int numDirsUp = baseIsFile ? base.length - commonIndex - 1 : base.length - commonIndex;

            for (int i = 0; i < numDirsUp; i++) {
                relative.append(".." + pathSeparator);
            }
        }
        relative.append(normalizedTargetPath.substring(common.length()));
        return relative.toString();
    }

	@Override
	public void run() {
        // read in all the projects for the first time around
		init();
        
		
        try {
        	LOGGER.log(Level.INFO, "Scanning " + e4moocFilePath);
            registerAll(Paths.get(e4moocFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // enable trace after initial registration
        this.trace = true; 
		processEvents();
	}   
}
