
package zed;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class GCharacter extends GObject {
    
    // index locations for each walking state within Animation_List are defined for Character
	// Animation_List might be created before or after initialization
    int FRAME_STATE_UP = 0;
    int FRAME_STATE_LEFT = 1;
    int FRAME_STATE_DOWN = 2;
    int FRAME_STATE_RIGHT = 3;
    int FRAME_STATE_UP_WALK = 4;
    int FRAME_STATE_LEFT_WALK = 5;
    int FRAME_STATE_DOWN_WALK = 6;
    int FRAME_STATE_RIGHT_WALK = 7;
    
    final int INVINCIBILITY_TIME = 1000;
    int STUN_TIME = 200;
    
    int Health; // current health for Character
    int Max_Health; // maximum health for Character
    float Speed; // tiles per second
    int X_Movement; // {-1, 0, 1} tells movement direction in x-axis
    int Y_Movement; // {-1, 0, 1} tells movement direction in y-axis
    
    long x_last_move; // time in nanoseconds of last movement
    long y_last_move;
    long last_damage; // time in milliseconds of last time was damaged
    
    int AI_State; // For use in Artificial_Intelligence function
    long Last_AI_State_Change; // For use in Artificial_Intelligence function
    long AI_State_Change_Time;
    Random rnd = new Random();
    
    Sound Hurt_Sound = null;
    
    // Constructor for character that makes use of SpriteSheet to construct its
    // Animation array
    public GCharacter(
    		int tile_x, // initial x position of the character w.r.t. the game tiles
    		int tile_y, // initial y position of the character w.r.t. the game tiles
    		int width, // initial width in pixels
    		int height, // initial height in pixels
    		boolean visible, // initialize whether the character is visible
    		boolean solid, // initializes whether its solid
    		boolean damage, // initialize whether it damages the player
            int[] sprite_shift_x, // by how many pixels each animation is shifted in x direction
            int[] sprite_shift_y, // by how many pixels each animation is shifted in y direction
            int tilesize, // Give the character how large each tile is
            SpriteSheet sprites, // Give the character the spritesheet file for fetching its animation frames
            int[] spritesheet_index, // Give the character the indexes for the rows of the SpriteSheet to fetch each animation from
                                     // index are defined {up,left,down,right,upwalk,leftwalk,downwalk,rightwalk}
            int[] animation_length, // Give the character the length of each animation
            boolean[] looping, // tell which animations are looping
            int current_animation, // intialize which animation to start out with
            int health, // intialize the character's max_health and health
            float speed, // Give the character its speed in tiles per second 
            int x_movement, // Give the character its initial x_movement value (-1, 0, 1)
            int y_movement, // Give the character its initial y_movement value (-1, 0, 1)
            int type
            ) throws SlickException {
        
        // Constructs the "Object" part of Character
        super(tile_x, tile_y, width, height, visible, solid, damage,
        		sprite_shift_x, sprite_shift_y, tilesize, sprites,
                spritesheet_index, animation_length, looping, current_animation);
        
        // Initialze health
        Health = health;
        Max_Health = health;
        
        // Initialize movement parameters
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        // Initialize movement
        x_last_move = System.nanoTime();
        y_last_move = System.nanoTime();
        // Initialize damage timer
        last_damage = System.currentTimeMillis();
        
        // Initialize Artificial Intelligence
        AI_State = 0;
        Last_AI_State_Change = System.currentTimeMillis();
        AI_State_Change_Time = 200;
    }
    
    // Constructor for Character that takes an already defined Animation array
    public GCharacter(
    		int tile_x, // initial x position of the character w.r.t. the game tiles
    		int tile_y, // initial y position of the character w.r.t. the game tiles
    		int width, // initial width in pixels
    		int height, // initial height in pixels
    		boolean visible, // initialize whether the character is visible
    		boolean solid, // initialize solidity for collision
    		boolean damage, // initialize whether it damages the player
            int[] sprite_shift_x, // by how many pixels each animation is shifted in x direction
            int[] sprite_shift_y, // by how many pixels each animation is shifted in y direction
            int tilesize, // Give the character how large each tile is
    		Animation[] animation_list, // Give the character the animations it will be using
            int current_animation, // initialize which animation to start out with
            int health, // initialize the character's max_health and health
            float speed, // Give the character its speed in tiles per second 
            int x_movement, // Give the character its initial x_movement value (-1, 0, 1)
            int y_movement, // Give the character its initial y_movement value (-1, 0, 1)
            int type
    		) throws SlickException{
    	
    	super(tile_x, tile_y, width, height, visible, solid, damage, sprite_shift_x, sprite_shift_y,
    			tilesize, animation_list, current_animation);
    	
        // Initialze health
        Health = health;
        Max_Health = health;
        
        // Initialize movement parameters
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        // Initialize movement
        x_last_move = System.nanoTime();
        y_last_move = System.nanoTime();
        // Initialize damage timer
        last_damage = System.currentTimeMillis();
        
        // Initialize Artificial Intelligence
        AI_State = 0;
        Last_AI_State_Change = System.currentTimeMillis();
    }

    // Default Constructor sets everything to equal either 0 or null
    public GCharacter() throws SlickException {
        x_last_move = System.nanoTime();
        y_last_move = System.nanoTime();
        last_damage = System.currentTimeMillis();
    }
    
    // frames cannot have array size less than 4x5 because these
    // are the frames that Character is using.
    
    // Initialization given SpriteSheet
    public void Init(
    		int tile_x, // initial x position of the character w.r.t. the game tiles
    		int tile_y, // initial y position of the character w.r.t. the game tiles
    		int width, // initial width in pixels
    		int height, // initial height in pixels
    		boolean visible, // initialize whether the character is visible
    		boolean solid, // initialize solidity for collision
    		boolean damage, // initialize whether it damages player
            int[] sprite_shift_x, // by how many pixels each animation is shifted in x direction
            int[] sprite_shift_y, // by how many pixels each animation is shifted in y direction
            int tilesize, // Give the character how large each tile is
            SpriteSheet sprites, // Give the character the spritesheet file for fetching its animation frames
            int[] spritesheet_index, // Give the character the indexes for the rows of the SpriteSheet to fetch each animation from
                                     // index are defined {up,left,down,right,upwalk,leftwalk,downwalk,rightwalk}
            int[] animation_length, // Give the character the length of each animation
            boolean[] looping, // tell which animations are looping
            int current_animation, // intialize which animation to start out with
            int health, // intialize the character's health
            int max_health, // intialize the character's max_health
            float speed, // Give the character its speed in tiles per second 
            int x_movement, // Give the character its initial x_movement value (-1, 0, 1)
            int y_movement // Give the character its initial y_movement value (-1, 0, 1)
            ) throws SlickException{
        
    	// Initialize object part of character
        Init(tile_x, tile_y, width, height, visible, solid, damage,
        		sprite_shift_x, sprite_shift_y, tilesize, sprites,
                spritesheet_index, animation_length, looping, current_animation);
        
        // initialize character part of character
        Health = health;
        Max_Health = max_health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;

        x_last_move = System.nanoTime();
        y_last_move = System.nanoTime();
    }
    
    public void Init(
    		int tile_x, // initial x position of the character w.r.t. the game tiles
    		int tile_y, // initial y position of the character w.r.t. the game tiles
    		int width, // initial width in pixels
    		int height, // initial height in pixels
    		boolean visible, // initialize whether the character is visible
    		boolean solid, // initialize solidity for collision
    		boolean damage, // initialize whether it damages the player
            int[] sprite_shift_x, // by how many pixels each animation is shifted in x direction
            int[] sprite_shift_y, // by how many pixels each animation is shifted in y direction
            int tilesize, // Give the character how large each tile is
    		Animation[] animation_list, // Give the character the animations it will be using
            int current_animation, // initialize which animation to start out with
            int health, // initialize the character's max_health and health
            float speed, // Give the character its speed in tiles per second 
            int x_movement, // Give the character its initial x_movement value (-1, 0, 1)
            int y_movement // Give the character its initial y_movement value (-1, 0, 1)
            ) throws SlickException{
    
	    super.Init(tile_x, tile_y, width, height, visible, solid, damage, 
	    		sprite_shift_x, sprite_shift_y,
				tilesize, animation_list, current_animation);
		
	    // Initialze health
	    Health = health;
	    Max_Health = health;
	    
	    // Initialize movement parameters
	    Speed = speed;
	    X_Movement = x_movement;
	    Y_Movement = y_movement;
	    
	    // Initialize movement
        x_last_move = System.nanoTime();
        y_last_move = System.nanoTime();
	    // Initialize damage timer
	    last_damage = System.currentTimeMillis();
	    
	    // Initialize Artificial Intelligence
	    AI_State = 0;
	    Last_AI_State_Change = System.currentTimeMillis();
    }
    
    // Updates the Character's position based on artificial intelligence and collision
    public void Update(GObject[] collision_objects, GCharacter[] npcs, Player_Character player){
    	
    	if (Health > 0)
    	{
	        Update_Frame_State();
	        GObject[] cplayer = {player};
	        boolean x_col_obj = (X_Collision(collision_objects) != null); // tell whether character has collided with an object
	        boolean x_col_npc = (X_Collision(npcs) != null); // tell whether character has collided with another collidable npc
	        boolean x_col_bnd = (X_Out_Of_Bounds());
	        boolean x_col_plr = (X_Collision(cplayer) != null);
	        boolean x_col = x_col_obj | x_col_npc | x_col_bnd | x_col_plr;
	        boolean y_col_obj = (Y_Collision(collision_objects) != null); // tell whether character has collided with an object
	        boolean y_col_npc = (Y_Collision(npcs) != null); // tell whether character has collided with another collidable npc
	        boolean y_col_bnd = (Y_Out_Of_Bounds());
	        boolean y_col_plr = (Y_Collision(cplayer) != null);
	        boolean y_col = y_col_obj | y_col_npc | y_col_bnd | y_col_plr;
	        boolean col = x_col | y_col;
	        
	        if ((x_col_plr | y_col_plr) && !(x_col_obj | y_col_obj) && Damage)
	        {
	        	player.Decriment_Health();
	        }
	        if (System.currentTimeMillis() > last_damage + STUN_TIME
	        		&& (!X_Out_Of_Bounds() || !Y_Out_Of_Bounds()))
	        {
	        	if (!x_col)
	        		Update_X_Position(); // can move if there is something to collide with
	        	if (!y_col)
	        		Update_Y_Position();
	        }
	        if (X_Collision(player.Get_Sword_Pos_X(), player.Get_Sword_Pos_Y())
	           |Y_Collision(player.Get_Sword_Pos_X(), player.Get_Sword_Pos_Y()))
	        {
	        	Decriment_Health(); // kills this character with a sword
	        }
	        
	        Artificial_Intelligence(col, player); // Proceed with AI code to update the character's movement values
    	}
    	else
    	{
    		Solid = false;
    		Visible = false;
    		Damage = false;
    	}
    }
    
    // can tell if GCharacter will collide with another object or not
    // and returns the object GCharacter collides with
    GObject X_Collision(GObject collision_objects[]){
        
    	if (collision_objects != null)
    	{
	        for (int i = 0; i < collision_objects.length; i++)
	        {
		        if (collision_objects[i] != null && collision_objects[i] != this && collision_objects[i].Solid)
		        {
		        	if (X_Collision(collision_objects[i]))
		        		return collision_objects[i];
		        }
	        }
    	}
        return null; // didn't collide
    }
    // can tell if GCharacter will collide with another object or not
    // and returns the object GCharacter collides with
    GObject Y_Collision(GObject collision_objects[]){
        
    	if (collision_objects != null)
    	{
	        for (int i = 0; i < collision_objects.length; i++)
	        {
		        if (collision_objects[i] != null && collision_objects[i] != this && collision_objects[i].Solid)
		        {
		        	if (Y_Collision(collision_objects[i]))
		        		return collision_objects[i];
		        }
	        }
    	}
        return null; // didn't collide
    }
    
    // can tell if pixel is within GCharacter or not in x direction of movement
    boolean X_Collision(int x, int y){
    	
    	if (x >= X_Position + X_Movement
    			&& x < X_Position + X_Movement + Width
    			&& y >= Y_Position && y < Y_Position + Height)
    	{
    		return true;
    	}
    	return false;
    }
    // can tell if pixel is within GCharacter or not in y direction of movement
    boolean Y_Collision(int x, int y){
    	
    	if (x >= X_Position && x < X_Position + Width
    			&& y >= Y_Position + Y_Movement
    			&& y < Y_Position + Y_Movement + Height)
    	{
    		return true;
    	}
    	return false;
    }
    
    boolean X_Collision(GObject col){
    	
    	if (X_Movement > 0)
    	{
    		if (X_Collision(col.Get_X_Position(), col.Get_Y_Position()))
    			return true;
    		if (X_Collision(col.Get_X_Position(), col.Get_Y_Position() + col.Width - 1))
    			return true;
    		if (col.Pixel_Contained(X_Position + Width, Y_Position))
    			return true;
    		if (col.Pixel_Contained(X_Position + Width, Y_Position + Height - 1))
    			return true;
    	}
    	if (X_Movement < 0)
    	{
    		if (X_Collision(col.Get_X_Position() + col.Width - 1, col.Get_Y_Position()))
    			return true;
    		if (X_Collision(col.Get_X_Position() + col.Width - 1, col.Get_Y_Position() + col.Height - 1))
    			return true;
    		if (col.Pixel_Contained(X_Position - 1, Y_Position))
    			return true;
    		if (col.Pixel_Contained(X_Position - 1, Y_Position + Height - 1))
    			return true;
    	}
    	return false;
    }
    boolean Y_Collision(GObject col){
    	
    	if (Y_Movement > 0)
    	{
    		if (Y_Collision(col.Get_X_Position(), col.Get_Y_Position()))
    			return true;
    		if (Y_Collision(col.Get_X_Position() + col.Width - 1, col.Get_Y_Position()))
    			return true;
    		if (col.Pixel_Contained(X_Position, Y_Position + Height))
    			return true;
    		if (col.Pixel_Contained(X_Position + Width - 1, Y_Position + Height))
    			return true;
    	}
    	if (Y_Movement < 0)
    	{
    		if (Y_Collision(col.Get_X_Position(), col.Get_Y_Position() + col.Height - 1))
    			return true;
    		if (Y_Collision(col.Get_X_Position() + col.Width - 1, col.Get_Y_Position() + col.Height - 1))
    			return true;
    		if (col.Pixel_Contained(X_Position, Y_Position - 1))
    			return true;
    		if (col.Pixel_Contained(X_Position + Width - 1, Y_Position - 1))
    			return true;
    	}
    	return false;
    }
    
    // check to see if GCharacter goes out of screen in x direction
    boolean X_Out_Of_Bounds(){

        if (X_Position + Width + X_Movement > Tilesize*20 || X_Position + X_Movement < 0)
        {
        	return true; // can't go out of bounds
        }
        return false;
    }
    // check to see if GCharacter goes out of screen in y direction
    boolean Y_Out_Of_Bounds(){

        if (Y_Position + Width + Y_Movement > Tilesize*15 || Y_Position + Y_Movement < 0)
        {
        	return true; // can't go out of bounds
        }
        return false;
    }    
    
    // updates the current animation being played based on current movement
    void Update_Frame_State(){
        
    	// if walking up or down
        if (Y_Movement == 1) {
            Change_Animation(FRAME_STATE_DOWN_WALK); // walk down
        }
        
        else if (Y_Movement == -1) {
        	Change_Animation(FRAME_STATE_UP_WALK); // walk up
        }
        
        else // if not walking up or down
        {
            if (X_Movement == 1) {
                Change_Animation(FRAME_STATE_RIGHT_WALK); // walk right
            }
            
            else if (X_Movement == -1) {
            	Change_Animation(FRAME_STATE_LEFT_WALK); // walk left
            }
            
            else // if not walking
            {
                if (Current_Animation == Animation_List[FRAME_STATE_UP_WALK]) {
                	Change_Animation(FRAME_STATE_UP); // face up
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_LEFT_WALK]) {
                    Change_Animation(FRAME_STATE_LEFT); // face left
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_DOWN_WALK]) {
                    Change_Animation(FRAME_STATE_DOWN); // face down
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_RIGHT_WALK]) {
                    Change_Animation(FRAME_STATE_RIGHT); // face right
                }
            }
        }
    }
    
    // Updates current x position based on movement
    // (do not call this function without checking for collision first)
    void Update_X_Position(){
        
        // Move Horizontally
        if (X_Movement != 0)
        {
            if (System.nanoTime() >= x_last_move
                + (long)(1000000000.0/(Speed*0.70710678118*Tilesize))) // wait until right time
            {
                X_Position += X_Movement; // update X_Position
            
                x_last_move = System.nanoTime(); // record the time of last movement
            }
        }
    }
    
    // Updates current y position based on movement
    // (do not call this function without checking for collision first)
    void Update_Y_Position(){
        
        // Move Horizontally
        if (Y_Movement != 0)
        {
            if (System.nanoTime() >= y_last_move
                + (long)(1000000000.0/(Speed*0.70710678118*Tilesize))) // wait until right time
            {
                Y_Position += Y_Movement; // update X_Position
            
                y_last_move = System.nanoTime(); // record the time of last movement
            }
        }
    }
    
    // Get the current health of the character
    public int Get_Health(){
        
        return Health;
    }
    
    // Decriment the current health
    public void Decriment_Health(){
        
    	if (System.currentTimeMillis() > last_damage + INVINCIBILITY_TIME)
    	{
	    	if (Health > 0) // no decrimenting past 0
	    	{
	    		Health--; // decriment health
	    		if (Hurt_Sound != null) {Hurt_Sound.play();}
	    	}
	    	else
	    	{
	    		Health = 0; // safeguard in case health is below 0
	    	}
	    	last_damage = System.currentTimeMillis();
    	}
    }
    
    // decrease health in chunks to save performance
    // if damage dealt needs more decriments
    public void Decrease_Health(int health_dec){
    	
    	if (System.currentTimeMillis() > last_damage + INVINCIBILITY_TIME)
    	{
	    	if (Health - health_dec < 0) // don't decrease below 0
	    	{
	    		Health = 0; // safeguard in case decreased below 0
	    	}
	    	else
	    	{
	    		Health -= health_dec; // decrease health
	    		if (Hurt_Sound != null) {Hurt_Sound.play();}
	    	}
	    	last_damage = System.currentTimeMillis();
    	}
    }
    
    // Reset character's health back to full health
    public void Reset_Health(){
    	
    	Health = Max_Health;
    	last_damage = System.currentTimeMillis();
    }
    
    // Incrase the character's health by a certain amount
    public void Increase_Health(int health_inc){
    	
    	if (Health + health_inc >= Max_Health) // don't increase more than maximum
    	{
    		Health = Max_Health; // safeguard to ensure health doesn't exceed maximum
    	}
    	else
    	{
    		Health = Health + health_inc; // increase health
    	}
    }
    
    // Get the maximum health of the character
    public int Get_Max_Health(){
        
        return Max_Health;
    }
    
    // Get the speed (in tiles per second) of the character
    public float Get_Speed(){
        
        return Speed;
    }
    
    // Get the current movement on the x axis of the character
    // can be {-1, 0, 1}
    public int Get_X_Movement(){
        
        return X_Movement;
    }
    // Get the current movement on the y axis of the character
    // can be {-1, 0, 1}
    public int Get_Y_Movement(){
        
        return Y_Movement;
    }
    
    public void Set_Movement(int x_movement, int y_movement){
    	
    	X_Movement = x_movement;
    	Y_Movement = y_movement;
    }
    
    // The default artificial intelligence code for the character
    // that updates the character's movement values
    public void Artificial_Intelligence(
    		boolean collision, // so the character knows if it collided with something
    		Player_Character player){ 
        
    	if (System.currentTimeMillis() > Last_AI_State_Change + AI_State_Change_Time)
    	{
    		if (rnd.nextBoolean())
    		{
    			Y_Movement = 0;
		    	if (rnd.nextBoolean())
		    	{
		    		X_Movement = 1;
		    	}
		    	else
		    	{
		    		X_Movement = -1;
		    	}
    		}
    		else
    		{
    			X_Movement = 0;
		    	if (rnd.nextBoolean())
		    	{
		    		Y_Movement = 1;
		    	}
		    	else
		    	{
		    		Y_Movement = -1;
		    	}
    		}
	    	Last_AI_State_Change = System.currentTimeMillis();
    	}
    }
    
    public void Render(int zoom,
            int current_tile_x, int current_tile_y, // position of tiles
            GameContainer gc, Graphics g){
    	
    	super.Render(zoom, current_tile_x, current_tile_y, gc, g);
    }
    
    public int Get_Type(){
    	
    	return -1;
    }
    
	public static void main(){

		// Conduct Initialization test
		
		// Conduct X_Collision test with input=null
		
		// Conduct Y_Collision test with input=null
		
		// Conduct X_Collision test with input=obj where obj is far away
		
		// Conduct Y_Collision test with input=obj where obj is far away
		
		// Conduct X_Collision test with input=obj where obj is one tile away
		
		// Conduct Y_Collision test with input=obj where obj is one tile away
		
		// Conduct X_Collision test where input is pixel within GCharacter
		
		// Conduct Y_Collision test where input is pixel within GCharacter
		
		// Conduct X_Collision test where input is pixel outside of GCharacter
		
		// Conduct Y_Collision test where input is pixel outside of GCharacter
		
		// Conduct X_Collision test with input={obj1,obj2} where both are far away
		
		// Conduct Y_Collision test with input={obj1,obj2} where both are far away
		
		// Conduct X_Collision test with input={obj1,obj2} where one is one tile away
		
		// Conduct Y_Collision test with input={obj1,obj2} where one is one tile away
		
		// Conduct X_Collision test with input={obj1,obj2} where both are one tile away

		// Conduct Y_Collision test with input={obj1,obj2} where both are one tile away
		
		// Conduct out of bounds test if out of bounds
		
		// Conduct out of bounds test if not out of bounds
		
		// Conduct Update_X_Position test and Update_Y_Position test for all movement values
		
		// Conduct Get_Health test
		
		// Conduct Decriment_Health test
		
		// Conduct Decrease_Health test
		
		// Conduct Reset_Health test
		
		// Conduct Increase_Health test
		
		// Conduct Get_Max_Health test
		
		// Conduct Get_Speed test
		
		// Conduct Get_X_Movement test for X_Movement=-1,0,1
		
		// Conduct Get_Y_Movement test for Y_Movement=-1,0,1
		
		// Conduct Set_Movement test for combinations of movement values
		
		// Conduct Move test
		
		// Conduct Get_X_Position test
		
		// Conduct Get_Y_Position test
		
		// Conduct Get_Width test
		
		// Conduct Get_Height test
		
		// Conduct Pixel_Contained test
		
		// Conduct Alligned_With_Tiles test for when alligned with tiles and when not
		
		// Conduct Change_Animation test
		
		// Conduct Get_Frame_State test
		
		// Conduct Is_Damage test
		
	}
}
