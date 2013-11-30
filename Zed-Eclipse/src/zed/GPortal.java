/**
 * 
 */
package zed;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/**
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class GPortal extends GObject {

	private int Dest_Level; // number that represents the level to load
	                        // if this is the same as the current level,
	                        // level shouldn't re-load but should only update
	                        // the player's position.
	private int Dest_X; // x position of destination tile
	private int Dest_Y; // y position of destination tile
	
	/**
	 * Default constructor for GPortal
	 */
	public GPortal() {
		super();
		Dest_Level = 0;
		Dest_X = 0;
		Dest_Y = 0;
		
		Visible = false;
		Solid = true;
	}
	
	/**
	 * Constructor for GPortal
	 */
	public GPortal(int tile_x, int tile_y, int tilesize,
			int dest_level, int dest_x, int dest_y) {
		
		// Initialize animation to default
		//Animation[] no_animation = new Animation[0];
		
		// Construct the GObject part
		super();
		super.Init(tile_x, tile_y, 16, 16, false, true, 0, null, null, tilesize, null, 0);
		
		Dest_Level = dest_level;
		Dest_X = dest_x;
		Dest_Y = dest_y;
	}
	
	// Returns the number that represents the level that the portal ports to
	public int Get_Dest_Level(){
		
		return Dest_Level;
	}
	
	// Returns the x position of the tile that player should port to
	public int Get_Dest_X_Tile(){
		
		return Dest_X;
	}
	
	// Returns the x position of the pixel that player should port to
	public int Get_Dest_X_Pixel(){
		
		return Dest_X*16;
	}
	
	// Returns the y position of the tile that player should port to
	public int Get_Dest_Y_Tile(){
		
		return Dest_Y;
	}
	
	// Returns the x position of the pixel that player should port to
	public int Get_Dest_Y_Pixel(){
		
		return Dest_Y*16;
	}
	
	// Changes the destination level of the portal
	public void Set_Dest_Level(int new_dest_level){
		
		Dest_Level = new_dest_level;
	}
	
	// Changes the x destination of where player ports to
	public void Set_Dest_X_Tile(int new_dest_x){
		
		Dest_X = new_dest_x;
	}
	
	// Changes the y destination of where player ports to
	public void Set_Dest_Y_Tile(int new_dest_y){
		
		Dest_Y = new_dest_y;
	}
	
	public int Get_Type(){
		
		return -1;
	}
}