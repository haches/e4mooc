/**
 * 
 */
package ch.ethz.e4mooc.server.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.ethz.e4mooc.server.ExecutionServiceImpl;

/**
 * @author hce
 *
 */
public class ExecutionServiceImplTest extends ExecutionServiceImpl {
	
	@Test
	public void extractTestResultsTest() {
		
		String testString1 = "<!--@test=32;5;7;-->";
		String [] splitted1 = {"32", "5", "7"};
		assertArrayEquals("Test 1:" , splitted1, extractTestResults(testString1));
		
		String testString2 = "<!--@test=100;50;50;-->";
		String [] splitted2 = {"100", "50", "50"};
		assertArrayEquals("Test 2:" , splitted2, extractTestResults(testString2));
		
		//check what happens with dangling spaces
		String testString3 = "<!--@test= 100; 50; 50; -->";
		String [] splitted3 = {"100", "50", "50"};
		assertArrayEquals("Test 2:" , splitted3, extractTestResults(testString3));		
		
	}

}
