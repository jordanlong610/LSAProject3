package Server;

import org.omg.CosNaming.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import DistributedFileSystem.*;


class DFSImpl extends DFSPOA {
	  private ORB orb;

	  public void setORB(ORB orb_val) {
	    orb = orb_val; 
	  }
	    
//	  // implement sayHello() method
//	  public String sayHello() {
//	    return "\nHello world !!\n";
//	  }
//	    
//	  // implement shutdown() method
//	  public void shutdown() {
//	    orb.shutdown(false);
//	  }

	  
	public String openFile(String fileName)
	{
		System.out.println("Opening File");
		return null;
	}

	public String readFile(String fileName, String recordNum)
	{
		try
		{
			Scanner s = new Scanner(new File("localStorage/"+fileName+".txt"));
			StringBuffer contents = new StringBuffer("");
			while (s.hasNext())
			{
				contents.append(s.nextLine() + "\n");
			}

			s.close();
			return contents.toString();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String closeFile(String fileName)
	{
		System.out.println("Closing File");
		return null;
	}

	public String getFileList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getOpenFiles()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getFile(String fileName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String lockFile(String fileName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String unlockFile(String fileName)
	{
		// TODO Auto-generated method stub
		return null;
	}
	}



/**
 * Distributed File System Server
 * @author Jordan Long
 *
 */
public class dfsServer
{

	
	public static void main(String args[])
	{
		try
		{
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			DFSImpl dfsSystemImpl = new DFSImpl();
			dfsSystemImpl.setORB(orb);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(dfsSystemImpl);
			DFS href = DFSHelper.narrow(ref);

			// get the root naming context
			// NameService invokes the name service
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt which is part of the Interoperable
			// Naming Service (INS) specification.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// bind the Object Reference in Naming
			String name = "DistributedFileSystem";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);

			System.out.println("DFS Server ready and waiting ...");

			// wait for invocations from clients
			orb.run();
		}

		catch (Exception e)
		{
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}

		System.out.println("DFS Server Exiting ...");

	}
	
	
	
	
	
}
