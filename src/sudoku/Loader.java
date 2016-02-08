package sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class Loader {
	public static List<String> getAllSdURLFromFolder(String folderURL)
	{
		return listFilesForFolder(folderURL);
	}
	
	public static Board loadInitialBoard(String fileName) throws Exception
	{
		BufferedReader br;
    	
		br = new BufferedReader(new FileReader(fileName));
		int[][] mat = new int[9][9];
		for (int j=0;j<9;j++)
		{
			String line = br.readLine();
			String[] nums = line.split(" ");
			for (int i=0;i<9;i++)
			{
				mat[j][i] = Integer.parseInt(nums[i]);
			}
		}
		br.close();

    	return new Board(mat);
		
	}
	
    private static void listFilesForFolder(final File folder, List<String> URLs) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,URLs);
	        } else {
	        	URLs.add(fileEntry.getAbsolutePath());
	        }
	    }
	}
    
    private static List<String> listFilesForFolder(String folderName) {
    	List<String> list = new LinkedList<String>();
    	listFilesForFolder(new File(folderName),list);
    	return list;
	}
    
}
