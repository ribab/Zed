package zed;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class File_Manager {
	Scanner Current_Level;
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
	}
}
