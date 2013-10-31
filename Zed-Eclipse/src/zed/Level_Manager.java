/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;

import java.io.File;
import java.io.FileNotFoundException;

// Java for exception handling
import java.util.ArrayList;


// Slick for drawing to screen and input
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */

// TODO: Edit Level Manager

public class Level_Manager {   

    private static final int TILE_SIZE = 16;
    
    private SpriteSheet tileset; // data for tiles
    private SpriteSheet character_sprites; // data for character sprites
    ArrayList<Object> objectlist; //this is going to be the array that the Level_Manager instance uses to hold all the objects
    
    Image Full_Heart; //full heart image
    Image Empty_Heart; //empty heart image
    final int maxHealth; //players maximum hp
    boolean[] lifeBar; //array representing which HUD images are full hearts or empty hearts
    
    int width; // Number of tile columns
    int height; // Number of tile rows
    int xpos; // x position of top-left of tiles
    int ypos; // y position of top-left of tiles
    int scale; // By how many times is the pixels larger?
    int[][] bot_tile_x; // tileset x index of bot_tile[x][y]
    int[][] bot_tile_y; // tileset y index of bot_tile[x][y]
    //int[][] top_tile_x; // tileset x index of top_tile[x][y]
    //int[][] top_tile_y; // tileset y index of top_tile[x][y]
    File_Manager Files;
    Player_Character player; // data for player character
    
    // Default instantiation for Level_Manager
    public Level_Manager() throws SlickException {
        tileset = new SpriteSheet("images/tileset.png", 16, 16);
        character_sprites = new SpriteSheet("images/spritesheet.png", 16, 32);
        Files = new File_Manager();

        // ============================
        // initialize player info START
        boolean f = false;
        boolean t = true;
        int[] player_spritesheet_index_y = {0,  1,  2,  3,  0,  1,  2,  3};
        int[] player_spritesheet_index_x = {1,  1,  1,  1,  0,  0,  0,  0};
        int[] player_animation_length  = {1,  1,  1,  1,  3,  3,  3,  3};
        int[] player_sprite_shift_x    = {0,  0,  0,  0,  0,  0,  0,  0,  16, 16, 16, 16};
        int[] player_sprite_shift_y    = {16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16};
        boolean[] player_looping       = {f,  f,  f,  f,  t,  t,  t,  t};
        int player_animation_speed = 200;
        int player_attack_animation_speed = 50;
        
        Animation[] player_animation_list = new Animation[12];
        for (int i = 0; i < 8; i++)
        {
	        player_animation_list[i] = new Animation(character_sprites,
	                player_spritesheet_index_x[i],                                  player_spritesheet_index_y[i], // first sprite
	                player_spritesheet_index_x[i] + player_animation_length[i] - 1, player_spritesheet_index_y[i], // last sprite
	                false, // horizontalScan true?
	                player_animation_speed, true /*autoupdate?*/);
	        player_animation_list[i].setLooping(player_looping[i]);
	        player_animation_list[i].setPingPong(true);
        }
        for (int i = 0; i < 4 ; i++)
        {
        	Image[] frames = new Image[3];
        	for (int j = 0; j < 3; j++)
        	{
        		frames[j] = character_sprites.getSubImage(j*48, 4*32 + i*48, 48, 48);
        	}
        	player_animation_list[i + 8] = new Animation(frames, player_attack_animation_speed, true);
        	player_animation_list[i + 8].setLooping(false);
        	player_animation_list[i + 8].setPingPong(true);
        }
        
        player = new Player_Character(0, 0, true,
            player_sprite_shift_x, player_sprite_shift_y, 16, // how far sprite is shifted and size in pixels
            player_animation_list, 0,
            5, 5.0f,
            0, 0);
        // Initialize Player Info END
        // ==========================
        
        //start of initializing heart images and life bar for HUD display
        Full_Heart = new Image("images/fullheart.png", false, Image.FILTER_NEAREST);
        Empty_Heart = new Image("images/emptyheart.png", false, Image.FILTER_NEAREST);
        Full_Heart.setAlpha(0.5f);
        Empty_Heart.setAlpha(0.5f);
        maxHealth = player.Get_Health(); //change from "final" if '+ heart containers' added to the game as a feature!
        lifeBar = new boolean[maxHealth];
        for (int i = 0; i < maxHealth; i++){
        	lifeBar[i] = true;
        }
        
        //initialize level
        width = 20;
        height = 15;
        xpos = 0;
        ypos = 0;
        scale = 2;
        bot_tile_x = new int[height][width];
        bot_tile_y = new int[height][width];
        File Default_Level = new File("levels/test.lvl");
        short Tile_List[];
        int Field_Size = width*height*2;
        Tile_List = new short[Field_Size];
        try {
			Tile_List = Files.Scan_LVL(Default_Level, Field_Size);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Files.Close_LVL();
        //end initialize level
        int k = 0;
        for (int i = 0; i < height; i++ ){
        	for(int j = 0; j < width; j++){
				bot_tile_x[i][j] = Tile_List[k];
				bot_tile_y[i][j] = Tile_List[++k];
				k++;
        	}
        }
        Files.Close_LVL();
        //top_tile_x = null;
        //top_tile_y = null;
    }
    
    /*
    // Instantiate Level_Manager with file
    public Level_Manager(String filepath){ // TODO: design lvl file and then make this to parse that file
        
        BufferedReader br = null;
        player = new Player_Character(initialization parameters);
        width = 0;
        height = 0;
        xpos = 0;
        ypos = 0;
        scale = 0;
        objectlist = new ArrayList();
        
        try
        {
            String cur_line;
            char[] file_chars = new char[256];
            String[] file_files = new String[256];
            int file_num = 0;
            int cur_y = 0;
            
            br = new BufferedReader(new FileReader(filepath));
            
            while ((cur_line = br.readLine()) != null)
            {
                // TODO: parse text file to load a new level
            }
        }
        catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    */
    
    // Edit class variables
    public void setwidth(int newwidth){
        
        width = newwidth;
    }
    public void setheight(int newheight){
        
        height = newheight;
    }
    public void setxpos(int newxpos){
        
        xpos = newxpos;
    }
    public void setypos(int newypos){
        
        ypos = newypos;
    }
    public void setscale(int newscale){
        
        scale = newscale;
    }
    public void settile(Image newtile, int x, int y){
        
        tileset.equals(newtile);
    }
    
    //method update_HUD takes boolean array representing player_character's health,
    //the fixed maximum health of said characters, the hero itself to get its current health,
    //and the full and empty hearts so that they can be drawn on the screen; the method
    //is meant to be called every time the game updates so as to check if the player
    //has been hurt/killed or not so that the HUD in the top left can reflect that
    public void update_HUD(boolean[] lifebar, int maxhealth, Player_Character hero, Image full, Image empty, Graphics g){
    	int current_Health = hero.Get_Health();
    	if (maxhealth == current_Health){
    		for (int i = 0; i < maxhealth; i++){
    			lifebar[i] = true; //supports getting full-health potions this way
    		}
    	}
    	else if (current_Health == 0){
    		for (int i = 0; i < maxhealth; i++){
    			lifebar[i] = false;
    			/**
    			 * code here to handle telling level manager that game is over
    			 * possibly setting a flag so empty hearts can be drawn first
    			 */
    		}
    	}
    	else{
    		for (int i = 0; i <=current_Health; i++){
    			lifebar[i] = true;
    		}
    		for (int i = current_Health+1; i < maxhealth; i++){
    			lifebar[i] = false;
    		}
    	}
    		//I'm *guessing* here (for the sake of performance) that drawing the objects
    		//to the screen is more costly than having more for-loops
    		for (int i = 0; i < maxhealth; i++){
    			//if (lifebar[i] = true)
    			if (i < player.Get_Health()) // TODO: above code has errors so replaced lifebar with player.Get_Health()
    			{
    				g.drawImage(
    						full, 				// image
    						i*full.getWidth()*scale, 	// x pos
    						0, 					// y pos
    						(i + 1)*full.getWidth()*scale, 	// x2 pos
    						full.getHeight()*scale, 	// y2 pos
    						0, 0,			 	// ???
    						full.getWidth(), 	// width
    						full.getHeight()); 	// height
    			}
    			else
    			{
    				g.drawImage(
    						empty,
    						i*empty.getWidth()*scale,
    						0,
    						(i + 1)*empty.getWidth()*scale, 
    						empty.getHeight()*scale,
    						0, 0,
    						empty.getWidth(),
    						empty.getHeight());
    			}
    		}
    	
    	return;
    }
    
    public void display(GameContainer gc, Graphics g){
        
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                g.drawImage(tileset.getSubImage(bot_tile_x[i][j], bot_tile_y[i][j]),
                        xpos + j*TILE_SIZE*scale,
                        ypos + i*TILE_SIZE*scale,
                        xpos + j*TILE_SIZE*scale + TILE_SIZE*scale,
                        ypos + i*TILE_SIZE*scale + TILE_SIZE*scale,
                        0, 0,
                        TILE_SIZE, TILE_SIZE);
            }
        }
        
        player.Render(scale, xpos, ypos, gc, g);
        
        update_HUD(lifeBar, maxHealth, player, Full_Heart, Empty_Heart, g);
    }
    
    public void update()
    {
        player.Update(null);
        
        if (player.Get_Sword_Pos_X() > 0 && player.Get_Sword_Pos_X() < 16*player.Get_Health()
        		&& player.Get_Sword_Pos_Y() > 0 && player.Get_Sword_Pos_Y() < 16)
        {
        	player.Decriment_Health();
        }
    }
    
    public void move_player(int new_x_mov, int new_y_mov)
    {
        player.New_Movement(new_x_mov, new_y_mov);
    }
}
