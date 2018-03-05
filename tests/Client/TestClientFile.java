package Client;

import static org.junit.Assert.*;
import org.junit.Test;

import DistributedFileSystem.*;

/**
 * Tests the functionality of ClientFile
 * @author Jacob Knight
 *
 */
public class TestClientFile 
{
	/**
	 * Tests that a ClientFile can be initialized, has the correct name, and starts off closed
	 */
	@Test
	public void testInitialization() 
	{
		ClientFile file = new ClientFile("testFile.txt");
		assertEquals("testFile.txt", file.getName());
		assertFalse(file.isOpen());
	}
	
	/**
	 * Tests that a file can be created
	 */
	@Test
	public void testCreateFile()
	{
		ClientFile file = new ClientFile("testCreateFile.txt");
		assertTrue(file.createFile());
		file.delete();
	}
	
	/**
	 * Tests that a file can be opened
	 */
	@Test
	public void testOpenFile()
	{
		/**
		 * Tests that a file that doesn't exist produces an error message;
		 */
		ClientFile file = new ClientFile("testOpenFile.txt");
		assertFalse(file.isOpen());
		System.out.println("\ttestOpenFile: The following error is a test: ");
		assertFalse(file.openFile());
		System.out.println();
		
		/**
		 * Tests that a file that exists opens properly
		 */
		file.createFile();
		assertTrue(file.openFile());
		assertTrue(file.isOpen());
		file.delete();
	}
	
	/**
	 * Tests that a file can be closed
	 */
	@Test
	public void testClose()
	{
		ClientFile file = new ClientFile("testCloseFile.txt");
		file.createFile();
		
		file.openFile();
		assertTrue(file.isOpen());
		
		file.closeFile();
		assertFalse(file.isOpen());
		file.delete();
	}
	
	/**
	 * Tests that the records of a file can be read
	 */
	@Test
	public void testRead()
	{
		/**
		 * Originally tested by having a premade file to read from.
		 * Now tested with the write method for convenience
		 */
		ClientFile file = new ClientFile("testReadFile.txt");
		file.createFile();
		file.openFile();
		file.write(0, "first");
		file.write(1, "second");
		file.write(2, "third");
		file.write(3, "fourth");
		file.write(4, "fifth");
		file.closeFile();
		
		/**
		 * Tests that the read method returns the value at the chosen position
		 */
		ClientFile readFile = new ClientFile("testReadFile.txt");
		readFile.openFile();
		
		assertEquals("first", readFile.read(0));
		assertEquals("fourth", readFile.read(3));
		assertNotEquals("fourth", readFile.read(4));
		
		/**
		 * Tests that the read method returns null if there isn't a record at the chosen position
		 * and displays the appropriate error message
		 */
		System.out.println("\ttestRead: The following errors are tests: ");
		assertNull(file.read(100));
		assertNull(file.read(-1));
		readFile.closeFile();
		readFile.delete();
	}
	
	/**
	 * Tests that the records can be written
	 * Note: files must be closed before changes are persisted
	 */
	@Test
	public void testWrite()
	{
		ClientFile file = new ClientFile("TestWriteFile.txt");
		file.createFile();
		file.openFile();
		
		/**
		 * Tests that a write on a new file will append to the record
		 * and display the appropriate console message
		 */
		System.out.println("\ttestWrite: The following messages are tests: ");
		assertTrue(file.write(0, "red"));
		assertEquals("red", file.read(0));
		
		/**
		 * Tests that a write greater than the record size will append to the record
		 * and display the appropriate console message
		 */
		assertTrue(file.write(5, "green"));
		assertEquals("red", file.read(0));
		assertEquals("green", file.read(1));
		
		/**
		 * Tests overwriting a record
		 */
		assertTrue(file.write(0, "blue"));
		assertEquals("blue", file.read(0));
		
		/**
		 * Tests that a position of -1 will display the appropriate error message
		 */
		
		assertFalse(file.write(-1, "grey"));

		file.closeFile();
		file.delete();
	}
}