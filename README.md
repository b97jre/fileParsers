fileParsers
===========

Small programs for easy extraction of information from files.

This is a package that I have created to parse multiple files easy and collect data in one file. This is one way of multiple probably more easy ways to do it. 

The jar file that is found in the bin file contains all that is needed for the program to work it think :)

The program searches recursively in the folder structure so make sure that the in file points to the final file. 
To start the htseq count program write. 


To parse HTSeq files 
====================
general:
<code>
java -jar /Path/to/jarFile/FileParser.jar -program HTSEQCOUNT -i path/to/folder/that/contains/the/files/ -suffix fileEnd -o OutFileName
</code>

Example:
<code>
java -jar /glob/johanr/bin/FileParser.jar -program HTSEQCOUNT -i counts -suffix counts -o AllCountFiles.tab.txt

<code>


To parse any file 
=================
