package zed;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class File_Manager {
	Scanner Current_Level;
	public short[] Scan_LVL(File level, int size) throws FileNotFoundException{
		Current_Level = new Scanner(level);
		short tile[];
		tile = new short[size];
		int i = 0;
		while(Current_Level.hasNext()){
			tile[i] = Current_Level.nextShort();
			i++;
		}
		return tile;
	}
	public void Close_LVL(){
		Current_Level.close();
	}
	public File_Manager(){
		Current_Level = null;
	}
}
