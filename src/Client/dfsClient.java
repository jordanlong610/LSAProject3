package Client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import DistributedFileSystem.*;

public class dfsClient
{

	static DFS DFSImpl;
	
	
	/**
	 * Basic run method
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);
			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is
			// part of the Interoperable naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			// resolve the Object Reference in Naming
			String name = "FileSystem";
			DFSImpl = DFSHelper.narrow(ncRef.resolve_str(name));

			
			
			
			
			//Establish connection to server
			System.out.println("Obtained a handle on server object: " + DFSImpl);
			
			
			//Test reading files from local server
			System.out.println(DFSImpl.readFile("document1", "0"));
			System.out.println(DFSImpl.readFile("document2", "0"));
			System.out.println(DFSImpl.readFile("important_tax_info", "0"));

			

			
			
			
			
			
			
			// shut down the server
			//fileSystemImpl.shutdown();
			
		} catch (Exception e)
		{
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
