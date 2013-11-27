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
	public short[][] Scan_LVL(File level, int types) throws FileNotFoundException{
		Current_Level = new Scanner(level);//open file
		short tile[][];
		tile = new short[types][];//allocate rows
		int i = 0;
		int j = -1;
		while(Current_Level.hasNext() && j<types){//exit if number of types has been meet or EOF
			if(Current_Level.hasNext("-")){//changes to the next array of objects
				while(!Current_Level.hasNextShort())Current_Level.next();//skips over non-number characters
				j++;
				i = 0;
				tile[j] = new short[Current_Level.nextShort()];//allocate array in jth row
			}
			while(!Current_Level.hasNextShort())Current_Level.next();//skips over non-number characters
			tile[j][i] = Current_Level.nextShort();//read in strings of numbers
			i++;
		}
		Current_Level.close();//close file
		return tile;//return 2d array
	}
	
	public File_Manager(){//default constructor
		Current_Level = null;
		Save = null;
	}
	
	//takes destination file, 2d array and row count (not being used currently)
	public void Save_Info(File dest, int info[][], short rows) throws FileNotFoundException{
		Save = new PrintStream(dest);//open file
		int length;
		for(int i = 0; i < rows; i++){
			length = info[i].length;
			Save.print("- ");
			Save.println(length);
			for(int j = 0; j < length; j++){
				Save.print(info[i][j]);
			}
			Save.println();
		}
		Save.close();
	}
	

}

class FMtest{
	public static void main(String[] args) throws FileNotFoundException{//test for File_Manager
		File level = new File("levels/0.lvl");
		short Tile_List[][] = null;
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

	}
}