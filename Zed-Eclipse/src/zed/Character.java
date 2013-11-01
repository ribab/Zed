
package zed;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/*
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class Character extends Object {
    
    // index locations for each walking state within Animation_List are defined for Character
    private static final int FRAME_STATE_UP = 0;
    private static final int FRAME_STATE_LEFT = 1;
    private static final int FRAME_STATE_DOWN = 2;
    private static final int FRAME_STATE_RIGHT = 3;
    private static final int FRAME_STATE_UP_WALK = 4;
    private static final int FRAME_STATE_LEFT_WALK = 5;
    private static final int FRAME_STATE_DOWN_WALK = 6;
    private static final int FRAME_STATE_RIGHT_WALK = 7;
    
    int Health; // current health for Character
    int Max_Health; // maximum health for Character
    float Speed; // tiles per second
    int X_Movement; // {-1, 0, 1} tells movement direction in x-axis
    int Y_Movement; // {-1, 0, 1} tells movement direction in y-axis
    
    long last_move; // time in nanoseconds of last movement
    
    int AI_State; // For use in Artificial_Intelligence function
    
    // Constructor for character that makes use of SpriteSheet to construct its
    // Animation array
    public Character(
    		int tile_x, // initial x position of the character w.r.t. the game tiles
    		int tile_y, // initial y position of the character w.r.t. the game tiles
    		boolean visible, // initialize wheter the character is visible
            int[] sprite_shift_x, // by how many pixels each animation is shifted in x direction
            int[] sprite_shift_y, // by how many pixels each animation is shifted in y direction
            int tilesize, // Give the character how large each tile is
            SpriteSheet sprites, // Give the character the spritesheet file for fetching its animation frames
            int[] spritesheet_index, // Give the character the indexes for the rows of the SpriteSheet to fetch each animation from
            int[] animation_length, // Give the character the length of each animation
            boolean[] looping, // tell which animations are looping
            int current_animation, // intialize which animation to start out with
            int health, // intialize the character's max_health and health
            float speed, // Give the character its speed in tiles per second 
            int x_movement, // Give the character its initial x_movement value (-1, 0, 1)
            int y_movement // Give the character its initial y_movement value (-1, 0, 1)
            ) {
        
        // Constructs the "Object" part of Character
        super(tile_x, tile_y, visible, sprite_shift_x, sprite_shift_y, tilesize, sprites,
                spritesheet_index, animation_length, looping, current_animation);
        
        // Initialze health
        Health = health;
        Max_Health = health;
        
        // Initialize movement parameters
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        // Initialize movement
        last_move = System.nanoTime();
        
        // Initialize Artificial Intelligence
        AI_State = 0;
    }
    
    // Constructor for Character that takes an already defined Animation array
    public Character(
    		int tile_x, // initial x position of the character w.r.t. the game tiles
    		int tile_y, // initial y position of the character w.r.t. the game tiles
    		boolean visible, // initialize wheter the character is visible
            int[] sprite_shift_x, // by how many pixels each animation is shifted in x direction
            int[] sprite_shift_y, // by how many pixels each animation is shifted in y direction
            int tilesize, // Give the character how large each tile is
    		Animation[] animation_list, // Give the character the animations it will be using
            int current_animation, // intialize which animation to start out with
            int health, // intialize the character's max_health and health
            float speed, // Give the character its speed in tiles per second 
            int x_movement, // Give the character its initial x_movement value (-1, 0, 1)
            int y_movement // Give the character its initial y_movement value (-1, 0, 1)
    		){
    	
    	super(tile_x, tile_y, visible, sprite_shift_x, sprite_shift_y,
    			tilesize, animation_list, current_animation);
    	
        // Initialze health
        Health = health;
        Max_Health = health;
        
        // Initialize movement parameters
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        // Initialize movement
        last_move = System.nanoTime();
        
        // Initialize Artificial Intelligence
        AI_State = 0;
    }

    // Default Constructor sets everything to equal either 0 or null
    public Character() {
    	
        Init(0, 0, false,
            null, null, 0, // how far sprite is shifted and size in pixels
            null, null, null, null,
            0);
        
        Health = 0;
        Max_Health = 0;
        Speed = 0;
        X_Movement = 0;
        last_move = System.nanoTime();
    }
    
    // frames cannot have array size less than 4x5 because these
    // are the frames that Character is using.
    
    // Initialization given SpriteSheet
    public void Init(
    		int tile_x, // initial x position of the character w.r.t. the game tiles
    		int tile_y, // initial y position of the character w.r.t. the game tiles
    		boolean visible, // initialize wheter the character is visible
            int[] sprite_shift_x, // by how many pixels each animation is shifted in x direction
            int[] sprite_shift_y, // by how many pixels each animation is shifted in y direction
            int tilesize, // Give the character how large each tile is
            SpriteSheet sprites, // Give the character the spritesheet file for fetching its animation frames
            int[] spritesheet_index, // Give the character the indexes for the rows of the SpriteSheet to fetch each animation from
            int[] animation_length, // Give the character the length of each animation
            boolean[] looping, // tell which animations are looping
            int current_animation, // intialize which animation to start out with
            int health, // intialize the character's health
            int max_health, // intialize the character's max_health
            float speed, // Give the character its speed in tiles per second 
            int x_movement, // Give the character its initial x_movement value (-1, 0, 1)
            int y_movement // Give the character its initial y_movement value (-1, 0, 1)
            ){
        
        Init(tile_x, tile_y, visible, sprite_shift_x, sprite_shift_y, tilesize, sprites,
                spritesheet_index, animation_length, looping, current_animation);
        
        Health = health;
        Max_Health = max_health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }
    
    // Updates the Character's position based on artificial intelligence and collision
    public void Update(Object collision_objects[]){
    	
        boolean collided;
        
        Update_Frame_State();
        
        if (collision_objects != null) // check if there is something to collide with
        {
            collided = Collision(collision_objects); // tell whether character has collided with an object
        }
        else
        {
            collided = false;
        }
        
        if (!collided)
        {
            Update_Position(); // can move if there is something to collide with
        }
        Artificial_Intelligence(collided); // Proceed with AI code to update the character's movement values
    }
    
    // can tell if Character will collide with another object or not
    boolean Collision(Object collision_objects[]){
        
        // TODO: write collision code
        
        return false;// TODO: return if collided or not
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
    
    // Updates current position based on movement
    // (do not call this function without checking for collision first)
    void Update_Position(){
        
        // Move Diagonally
        if (X_Position != 0 && Y_Position != 0)
        {
            if (System.nanoTime() >= last_move
                + (long)(1000000000.0/(Speed*0.70710678118*16.0))) // wait until right time
            {
                X_Position += X_Movement; // update X_Position
                Y_Position += Y_Movement; // update Y_Position
            
                last_move = System.nanoTime(); // record the time of last movement
            }
        }
        
        // Move vertically or horizontally
        else 
        {
            if (System.nanoTime() >= last_move
                    + (long)(1000000000.0/(Speed*16.0))) // wait until right time
            {
                X_Position += X_Movement; // update X_Position
                Y_Position += Y_Movement; // update Y_Position

                last_move = System.nanoTime(); // record the time of last movement
            }
        }
    }
    
    // Get the current health of the character
    public int Get_Health(){
        
        return Health;
    }
    
    // Decriment the current health
    public void Decriment_Health(){
        
    	if (Health > 0) // no decrimenting past 0
    	{
    		Health--; // decriment health
    	}
    	else
    	{
    		Health = 0; // safeguard in case health is below 0
    	}
    }
    
    // decrease health in chunks to save performance
    // if damage dealt needs more decriments
    public void Decrease_Health(int health_dec){
    	
    	if (Health - health_dec <= 0) // don't decrease below 0
    	{
    		Health = 0; // safeguard in case decreased below 0
    	}
    	else
    	{
    		Health -= health_dec; // decrease health
    	}
    }
    
    // Reset character's health back to full health
    public void Reset_Health(){
    	
    	Health = Max_Health;
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
    
    // The default artificial intelligence code for the character
    // that updates the character's movement values
    private void Artificial_Intelligence(
    		boolean collided // so the character knows if it collided with something
    		){
        
        // TODO: Make AI Code
    }
}
