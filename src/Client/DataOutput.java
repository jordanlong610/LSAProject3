package Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Creates output files and appends to them
 * @author Jacob Knight
 */
public class DataOutput 
{
	FileWriter fw = null;
	BufferedWriter bw = null;
	
	/**
	 * Creates an output file or opens an existing one.
	 * @param outputFile - the path of the desired output file
	 */
	public DataOutput(String outputFile)
	{		
		try
		{
			File file = new File(outputFile);
			
			if(!file.exists())
				file.createNewFile();
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a new entry to the output file.
	 * @param data - the value of the new entry
	 */
	public void write(String data)
	{
		try 
		{
			bw.write(data);
			bw.newLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the file writers.
	 * This should always be done when file output is finished.
	 */
	public void close()
	{
		try
		{
			bw.close();
			fw.close();
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}