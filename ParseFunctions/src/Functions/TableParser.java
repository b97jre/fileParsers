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
			if(fileNames.size() >0){
				ExtendedWriter EW = ExtendedWriter.getFileWriter(inDir+"/"+outFile);
				ExtendedReader[] ERs = new ExtendedReader[fileNames.size()];
				for(int i = 0; i < fileNames.size();i++){
					System.out.println(fileNames.get(i));
					ERs[i] = ExtendedReader.getFileReader(inDir+"/"+fileNames.get(i));
				}
				for(int i = 0; i < fileNames.size()-1;i++){
					EW.print(fileNames.get(i)+seperator);
				}
				EW.println(fileNames.get(fileNames.size()-1));

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
			else{
				System.out.println("No files was found with current flags");
			}
		}
		catch(Exception E){E.printStackTrace();}
	}

	public static boolean extractColumnsParameters(Hashtable<String, String> T){
		boolean allPresent = true;
		if(T.containsKey("-h")) allPresent = false;
		if(T.containsKey("-help")) allPresent = false;
		if(!T.containsKey("-col")) allPresent = false;
		if(allPresent) return true;
		else{
			System.out.println("Specific flags for EXTRACTCOLUMN");
			System.out.println();
			System.out.println(Functions.fixedLength("-col <int>", 30)+"Column to extract");
			System.out.println(Functions.fixedLength("-sep <String>", 30)+"How columns are seperated. Default tab");
			System.out.println();
		}
		return false;

	}

}

