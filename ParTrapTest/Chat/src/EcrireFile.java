import java.io.*;


public class EcrireFile{
	static public void ecrireDans(String message, String file,boolean mode){
		try{

			File fichier = new File(file);
 
			if (! fichier.exists())
			{
			  fichier.createNewFile();
			}

			PrintWriter p2 = new PrintWriter(new FileWriter(file,mode));
			p2.println(message);
			p2.close();
		} catch (Exception e)  {
	  		System.err.println("Error reading: " + e);
		}
	}
}