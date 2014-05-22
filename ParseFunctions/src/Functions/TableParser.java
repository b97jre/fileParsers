/**
 * 
 */
package Functions;

import general.ExtendedReader;
import general.ExtendedWriter;
import general.Functions;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author johanreimegard
 *
 */
public class TableParser {

	public static void extractColumns(Hashtable<String, String> T,String inDir, ArrayList<String> fileNames,String outFile){
		try{
			int column =  Functions.getInt(T, "-col",0);
			String seperator = Functions.getValue(T, "-sep","\t"); 

			ExtendedWriter EW = ExtendedWriter.getFileWriter(outFile);
			ExtendedReader[] ERs = new ExtendedReader[fileNames.size()];
			for(int i = 0; i < fileNames.size();i++){
				ERs[i] = ExtendedReader.getFileReader(fileNames.get(i));
			}
			while(ERs[0].more()){
				for(int i = 0; i < fileNames.size()-1;i++){
					String[] cols = ERs[i].readLine().split(seperator);
					EW.print(cols[column]+seperator);
				}
				String[] cols = ERs[fileNames.size()-1].readLine().split(seperator);
				EW.println(cols[column]);
			}
			for(int i = 0; i < fileNames.size();i++){
				ERs[i].close();	
			}
			EW.flush();
			EW.close();
		}
		catch(Exception E){E.printStackTrace();}
	}

	public static boolean extractColumnsParameters(Hashtable<String, String> T){
		boolean allPresent = true;
		if(!T.containsKey("-h")) allPresent = false;
		if(!T.containsKey("-help")) allPresent = false;
		if(!T.containsKey("-col")) allPresent = false;
		if(!T.containsKey("-sep")) allPresent = false;
		if(allPresent) return true;
		else{
			System.out.println("Specific flags for EXTRACTCOLUMN");
			System.out.println();
			System.out.println(Functions.fixedLength("-col <int>", 30)+"Column to extract");
			System.out.println(Functions.fixedLength("-sep <String>", 30)+"How columns are seperated");
			System.out.println();
		}
		return false;

	}

}

