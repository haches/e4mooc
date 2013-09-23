package ch.ethz.e4mooc.server;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import ch.ethz.e4mooc.server.util.DirectoryWatcher;
import ch.ethz.e4mooc.server.util.ServerProperties;
import ch.ethz.e4mooc.server.util.TmpDirCleaner;

/**
 * This class is loaded when starting the server. It will
 * delete all folders that might exist in the E4MOOC_TMP folder.
 * Furthermore, it has a continuous loop that removes all temporary
 * folders which are older than 10 minutes.
 *
 * @author hce
 *
 */

public class ContextListener implements ServletContextListener{

	private final static Logger LOGGER = Logger.getLogger(ch.ethz.e4mooc.server.ContextListener.class.getName());
	
	private final String e4moocTmpFolderPath = ServerProperties.E4MOOC_TMP_FOLDER;
	
	/** thread to watch changes in the E4MOOC project directory */
	private Thread watchE4MoocThread;
	/** thread to clean up the E4MOOC tmp directory */
	private TmpDirCleaner tmpDirCleaner;
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		LOGGER.log(Level.INFO, "Variable E4MOOC: " + ServerProperties.E4MOOC_PROJECTS_FOLDER);
		LOGGER.log(Level.INFO, "Variable E4MOOC_TMP: " + e4moocTmpFolderPath);
		LOGGER.log(Level.INFO, "Loading ContextListener; deleting tmp folders");
		deleteAllTmpFiles();
		
		// start the thread that watches the E4Mooc directory for changes
		try {
			watchE4MoocThread = new DirectoryWatcher();
			watchE4MoocThread.setDaemon(true);
			watchE4MoocThread.start();
		} catch (IOException e) {
			System.err.println("Exeception with starting the project directory watcher");
			LOGGER.log(Level.SEVERE, "Exeception with starting the project directory watcher");
			e.printStackTrace();
		}
		
		// start the thread that deletes the tmp folders
		tmpDirCleaner = new TmpDirCleaner();
		tmpDirCleaner.setDaemon(true);
		tmpDirCleaner.start();		
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		LOGGER.log(Level.INFO, "Unloading ContextListener; deleting tmp folders");
		deleteAllTmpFiles();
	}
	
	
	/**
	 * Deletes all the folder in the E4MOOC_TMP folder.
	 */
	private void deleteAllTmpFiles() {
		// get all the tmp folders that might exist and delete them
		File rootTmpFolder = new File(e4moocTmpFolderPath);		
		File[] tmpFolders = rootTmpFolder.listFiles((FileFilter) FileFilterUtils.directoryFileFilter());
		for(File f: tmpFolders) {
			LOGGER.log(Level.INFO, "Deleting temporary folder: " + f.getPath());
			FileUtils.deleteQuietly(f);
		}		
	}
}
