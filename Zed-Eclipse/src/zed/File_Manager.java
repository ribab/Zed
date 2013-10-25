package zed;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class File_Manager {
	Scanner Current_Level;
	public int Scan_LVL(File level) throws FileNotFoundException{
		Current_Level = new Scanner(level);
		short tile;
		Current_Level.useDelimiter(" ");
		tile = Short.parseShort(Current_Level.next());
		return tile;
	}
	public void Close_LVL(){
		Current_Level.close();
	}
	public File_Manager(){
		Current_Level = null;
	}
}
