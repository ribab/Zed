
package zed;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/*
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class Character extends Object {
    
    // animations are defined for Character
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
    
    public Character(int tile_x, int tile_y, boolean visible,
            int[] sprite_shift_x, int[] sprite_shift_y, int tilesize, // how far sprite is shifted and size in pixels
            SpriteSheet sprites, int[] spritesheet_index, int[] animation_length, boolean[] looping,
            int current_animation,
            int health, float speed,
            int x_movement, int y_movement) {
        
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
    
    // Initialization with Animations already defined
    public Character(int tile_x, int tile_y, boolean visible,
    		int[] sprite_shift_x, int[] sprite_shift_y, int tilesize,
    		Animation[] animation_list, int current_animation,
    		int health, float speed,
    		int x_movement, int y_movement){
    	
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

    // Default Constructor
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
    
    // Initialization for 16x32 Character
    
    public void Init(int tile_x, int tile_y, boolean visible,
            int[] sprite_shift_x, int[] sprite_shift_y, int tilesize, // how far sprite is shifted and size in pixels
            SpriteSheet sprites, int[] spritesheet_index, int[] animation_length, boolean[] looping,
            int current_animation,
            int health, int max_health, float speed,
            int x_movement, int y_movement){
        
        Init(tile_x, tile_y, visible, sprite_shift_x, sprite_shift_y, tilesize, sprites,
                spritesheet_index, animation_length, looping, current_animation);
        
        Health = health;
        Max_Health = max_health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }
    
    // Updates the Character
    public void Update(Object collision_objects[]){
        boolean collided;
        
        Update_Frame_State();
        
        if (collision_objects != null)
        {
            collided = Collision(collision_objects);
        }
        else
        {
            collided = false;
        }
        
        if (!collided)
        {
            Update_Position();
        }
        Artificial_Intelligence(collided);
    }
    
    // can tell if Character will collide with another object or not.
    boolean Collision(Object collision_objects[]){
        
        // TODO: write collision code
        
        return false;// TODO: return if collided or not
    }
    
    // updates the current animation being played based on current movement
    void Update_Frame_State(){
        
        if (Y_Movement == 1) {
            Change_Animation(FRAME_STATE_DOWN_WALK);
        }
        
        else if (Y_Movement == -1) {
        	Change_Animation(FRAME_STATE_UP_WALK);
        }
        
        else
        {
            if (X_Movement == 1) {
                Change_Animation(FRAME_STATE_RIGHT_WALK);
            }
            
            else if (X_Movement == -1) {
            	Change_Animation(FRAME_STATE_LEFT_WALK);
            }
            
            else
            {
                if (Current_Animation == Animation_List[FRAME_STATE_UP_WALK]) {
                	Change_Animation(FRAME_STATE_UP);
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_LEFT_WALK]) {
                    Change_Animation(FRAME_STATE_LEFT);
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_DOWN_WALK]) {
                    Change_Animation(FRAME_STATE_DOWN);
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_RIGHT_WALK]) {
                    Change_Animation(FRAME_STATE_RIGHT);
                }
            }
        }
    }
    
    // Updates current position based on movement
    void Update_Position(){
        
        // Move Diagonally
        if (X_Position != 0 && Y_Position != 0)
        {
            if (System.nanoTime() >= last_move
                + (long)(1000000000.0/(Speed*0.70710678118*16.0)))
            {
                X_Position += X_Movement; // TODO: check for collision
                Y_Position += Y_Movement;
            
                last_move = System.nanoTime();
            }
        }
        
        // Move vertically or horizontally
        else 
        {
            if (System.nanoTime() >= last_move
                    + (long)(1000000000.0/(Speed*16.0)))
            {
                X_Position += X_Movement; // TODO: check for collision
                Y_Position += Y_Movement;

                last_move = System.nanoTime();
            }
        }
    }
    
    // Get the current health of the character
    public int Get_Health(){
        
        return Health;
    }
    
    // Decriment the current health
    public void Decriment_Health(){
        
    	if (Health > 0)
    	{
    		Health--;
    	}
    	else
    	{
    		Health = 0;
    	}
    }
    
    public void Decrease_Health(int health_dec){
    	
    	if (Health - health_dec <= 0)
    	{
    		Health = 0;
    	}
    	else
    	{
    		Health -= health_dec;
    	}
    }
    
    public void Reset_Health(){
    	
    	Health = Max_Health;
    }
    
    public void Increase_Health(int health_inc){
    	
    	if (Health + health_inc >= Max_Health)
    	{
    		Health = Max_Health;
    	}
    	else
    	{
    		Health = Health + health_inc;
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
    
    private void Artificial_Intelligence(boolean collided){
        
        // TODO: Make AI Code
    }
}
