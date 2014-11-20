package Main;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Hashtable;

import Functions.HTSeqCountParser;
import Functions.TableParser;


import general.Functions;
import general.IOTools;

public class Main {

	/**
	 * @param args
	 */

	String inDir;
	String suffix;
	String outFile;

	public enum Programs {
		EXTRACTCOLUMN,
		HTSEQCOUNT,
		HELP
	}	

	
	public static void main(String[] args) {
		int length = args.length;
		for (int i = 0; i < length; i++){
			args[i] = args[i].trim();
			System.out.print(args[i]+" ");
		}
		System.out.println();
		Hashtable<String,String> T = Functions.parseCommandLine(args);
		Main newRun = new Main();
		newRun.run(T);
	}


		public void run(Hashtable<String, String> T) {
			boolean allPresent = true;
			if (T.containsKey("-i")){
				inDir = Functions.getValue(T, "-i");
				//System.out.println(inDir);
				inDir = new File(inDir).getAbsolutePath();
				//System.out.println(inDir);
			}else
				allPresent = false;

			if (T.containsKey("-suffix"))
				suffix = Functions.getValue(T, "-suffix");
			else if(allPresent && IOTools.isDir(inDir)){
				allPresent = false;
			}

			if (T.containsKey("-o"))
				outFile = Functions.getValue(T, "-o");
			else
				allPresent = false;
			if (!T.containsKey("-program")) allPresent = false;
			
			if (allPresent){
				if (IOTools.isDir(inDir)){
					ArrayList<String> fileNames = IOTools.getSequenceFilesRecursive(inDir, suffix);
					runProgram(T,inDir, outFile,fileNames);
				}
				else if (IOTools.fileExists(inDir)) {
					ArrayList<String> fileNames = new ArrayList<String>();
					fileNames.add(inDir);
					File file = new File(inDir);
					String folder = file.getParent();
					String fileName = file.getName();
					System.out.println("folder: " + folder);
					System.out.println("fileName: " + fileName);
					runProgram(T, folder, folder,fileNames);
				}
			}else{
				System.out.println("\n\nAborting run because of missing arguments.");
				help(T);
			}
		}


		public void runProgram(Hashtable<String, String> T,
				String inDir, String outFile,ArrayList <String> fileNames) {

				String programName = Functions.getValue(T, "-program",Functions.getValue(T, "-p","help"));
				Programs program = Programs.valueOf(programName.toUpperCase());
				switch (program) {
				case EXTRACTCOLUMN:
					if(TableParser.extractColumnsParameters(T))
						TableParser.extractColumns(T, inDir,fileNames,outFile);
					break;
				case HTSEQCOUNT:
					if(HTSeqCountParser.extractColumnsParameters(T))
						HTSeqCountParser.extractColumns(T, inDir,fileNames,outFile);
					break;
				default: help(T);	

				}	
		}



		public void help(Hashtable<String, String> T){

			System.out.println("Required flags:");
			System.out.println(Functions.fixedLength("-program <program>",30)+"Available programs are listed below.(-program blast)");
			System.out.println(Functions.fixedLength("-i <file|dir>", 30)+"Indirectory or infile ");
			System.out.println(Functions.fixedLength("-o <file>", 30)+"Outfile (-o outFile)");
			System.out.println();
			System.out.println("Required values if working on directory:");
			System.out.println(Functions.fixedLength("-suffix <Suffix>", 30)+" suffix of files");

			System.out.println("Available programs are:");
			System.out.println("");
			for (Programs info : EnumSet.allOf(Programs.class)) {
				System.out.println(info);
			}
			System.out.println();
			System.out.println("Fill in program name to get program specific flags");
		}


}
