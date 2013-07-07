/**
 * 
 */
package ch.ethz.e4mooc.server.util;

import java.io.File;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
 * @author hce
 *
 */
public class TmpDirCleaner extends Thread {
	
	public TmpDirCleaner() {
	}
	
	@Override
	public void run() {
		runCleanUpLoop();
	}
	
	private void runCleanUpLoop() {
		
		while(true) {
			
			// get all the folder older than 15 minutes
			Set<String> foldersToDelete = ServerState.getState().getSetOfAllTmpFoldersOlderThan(1000L * 60 * 15);
			
			// delete all the folders
			for(String folderName: foldersToDelete) {
				File f  = new File(folderName);
				// we actually delete the parent file which is the tmp folder "session_id + _ + timestamp"
				FileUtils.deleteQuietly(f.getParentFile());
			}
			
			// go to sleep (14 minutes)
			try {
				Thread.sleep(1000 * 60 * 14);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
}
