package zed;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;

public class File_Manager {
	Scanner Current_Level;
	PrintStream Save;
	/*
	 Takes a File item and number of types expected to be returned in the 2d array
	 */
	public int[][] Scan_LVL(File level, int types) throws FileNotFoundException{
		Current_Level = new Scanner(level);//open file
		int tile[][];
		tile = new int[types][];//allocate rows
		int i = 0;
		int j = -1;
		while(Current_Level.hasNext() && j<types){//exit if number of types has been meet or EOF
			if(Current_Level.hasNext("-")){//changes to the next array of objects
				while(!Current_Level.hasNextInt())Current_Level.next();//skips over non-number characters
				j++;
				i = 0;
				tile[j] = new int[Current_Level.nextInt()];//allocate array in jth row
			}
			while(!Current_Level.hasNextInt())Current_Level.next();//skips over non-number characters
			tile[j][i] = Current_Level.nextInt();//read in strings of numbers
			i++;
		}
		Current_Level.close();//close file
		return tile;//return 2d array
	}
	
	public File_Manager(){//default constructor
		Current_Level = null;
		Save = null;
	}
	
	//takes destination file, and array and stores the array in a file.
	public void Save_Info(File dest, int info[]) throws FileNotFoundException{
		Save = new PrintStream(dest);//open file (creates file if it doesn't exist)
		int length;
		length = info.length;//gets the length of a row
		Save.print("- ");// stores a dash to indicate the start of data from a particular row
		Save.println(length);//stores the number of items in the row
		for(int i = 0; i < length; i++){
			Save.print(info[i]);
			Save.println();
		}
		Save.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException{//test for File_Manager
		File level = new File("levels/0.lvl");
		int Tile_List[][] = null;
		File_Manager Files = new File_Manager();
		Tile_List = Files.Scan_LVL(level, 4);
        for (int i = 0; i < 4; i++ ){
        	for(int j = 0; j < Tile_List[i].length; j++){
        		System.out.print(Tile_List[i][j]+ " ");
        		if(j%20 == 19)
                	System.out.println();
        	}
        	System.out.println();
        }
        level = new File("score");
        int List[] = new int[2];
        Files.Save_Info(level, List);
	}
}
