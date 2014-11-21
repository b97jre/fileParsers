/**
 * 
 */
package Functions;

import general.ExtendedReader;
import general.ExtendedWriter;
import general.Functions;
import general.IOTools;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author johanreimegard
 *
 */
public class HTSeqCountParser {

	public static void extractColumns(Hashtable<String, String> T,String inDir, ArrayList<String> fileNames,String outFile){
		
		String commonPrefix = IOTools.longestCommonPrefix(fileNames);
		System.out.println("Common prefix "+commonPrefix);
		
		String commonSuffix = IOTools.longestCommonSuffix(fileNames);
		System.out.println("Common suffix "+commonSuffix);
		
		ArrayList<String> sampleNames = new ArrayList<String>();
		for(int j = 0; j < fileNames.size(); j++){
			String sampleName = Functions.getFileWithoutSuffix(fileNames.get(j), commonSuffix);
			sampleName = Functions.getFileWithoutPrefix(sampleName, commonPrefix);
			sampleName = IOTools.fixFileName(sampleName);
			sampleNames.add(sampleName);
			System.out.println(sampleNames);
		}
		
		try{
			int column =  1;
			String seperator = "\t"; 
			if(fileNames.size() >0){
				ExtendedWriter EW = ExtendedWriter.getFileWriter(inDir+"/"+outFile);
				ExtendedReader[] ERs = new ExtendedReader[fileNames.size()];
				for(int i = 0; i < fileNames.size();i++){
					ERs[i] = ExtendedReader.getFileReader(fileNames.get(i));
				}
				
				// print the filenames
				for(int i = 0; i < sampleNames.size()-1;i++){
					EW.print(sampleNames.get(i)+seperator);
				}
				EW.println(sampleNames.get(sampleNames.size()-1));

				while(ERs[0].more()){
					
					EW.print(ERs[0].readLine()+seperator);
					for(int i = 1; i < fileNames.size()-1;i++){
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
		if(allPresent) return true;
		else{
			System.out.println("Specific flags for HTSseqCount");
			System.out.println();
			System.out.println();
		}
		return false;

	}

}

