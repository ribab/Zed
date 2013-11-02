package zed;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class File_Manager {
	Scanner Current_Level;
	public short[][] Scan_LVL(File level, int size, int types) throws FileNotFoundException{
		Current_Level = new Scanner(level);
		short tile[][];
		tile = new short[types][];
		tile[0] = new short[size];
		int i = 0;
		int j = 0;
		while(Current_Level.hasNext()){
			tile[j][i] = Current_Level.nextShort();
			i++;
		}
		Current_Level.close();
		return tile;
	}
	public File_Manager(){
		Current_Level = null;
	}
}
