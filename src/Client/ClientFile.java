package Client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Enables files to be opened closed, read, and written to.
 * Encapsulates the contents of the file.
 * @author Jacob Knight
 *
 */
@SuppressWarnings("serial")
public class ClientFile extends File
{
	private Boolean open;
	private ArrayList<String> records;
	
	public ClientFile(String pathName) 
	{
		super(System.getProperty("user.home") + File.separator + "DistributedFiles" + File.separator + pathName);
		open = false;
		records = new ArrayList<String>();
	}
	
	public Boolean createFile()
	{
		Boolean success = false;
		if (!this.exists())
		{
			try 
			{
				this.getParentFile().mkdir();
				this.createNewFile();
				success = true;
			} catch (IOException e) 
			{
				e.printStackTrace();
			}			
		}	
		return success;
	}
	
	public Boolean isOpen()
	{
		return open;
	}
	
	public Boolean openFile()
	{
		if (open == false)
		{
			if (this.exists() && !this.isDirectory())
			{
				DataInput reader = new DataInput(this.getPath());
				reader.read(records);
				open = true;
			}
		}
		if (open == false)
			System.err.println("Open File Error :: No such file");
		return open;
	}
	
	public Boolean closeFile()
	{
		Boolean success = false;
		if (open == true && this.exists() && !this.isDirectory())
		{
			this.delete();
			this.createFile();
			DataOutput writer = new DataOutput(this.getPath());
			for (int pos = 0; pos < records.size(); pos++)
				writer.write(records.get(pos));
			
			writer.close();
			open = false;
			success = true;
		}
		return success;
	}
	
	public String read(int pos)
	{
		if (pos < records.size() && pos > -1)
			return records.get(pos);
		else if (pos < 0)
		{
			System.err.println("Read File Error :: Position less than 0");
			return null;
		}
		else
		{
			System.err.println("Read File Error :: No such record");
			return null;
		}
	}
	
	public Boolean write(int pos, String value)
	{
		Boolean success = false;

		if (pos < records.size() && pos > -1)
		{
			records.set(pos, value);
			success = true;
		}
		else if (pos >= records.size())
		{
			System.out.println("Write position not found, appending value to the end of the record.");
			records.add(value);
			success = true;
		}
		else
			System.err.println("Write File Error :: Unable to write to the specified position");
		
		return success;
	}
}