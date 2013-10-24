
package zed;

import org.newdawn.slick.SpriteSheet;

/*
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class Character extends Object {
    
    // animations are defined for Character
    private static final int FRAME_STATE_DOWN = 0;
    private static final int FRAME_STATE_UP = 1;
    private static final int FRAME_STATE_LEFT = 2;
    private static final int FRAME_STATE_RIGHT = 4;
    private static final int FRAME_STATE_DOWN_WALK = 5;
    private static final int FRAME_STATE_UP_WALK = 6;
    private static final int FRAME_STATE_LEFT_WALK = 7;
    private static final int FRAME_STATE_RIGHT_WALK = 8;
    
    int Health; // current health for Character
    int Max_Health; // maximum health for Character
    float Speed; // tiles per second
    int X_Movement; // {-1, 0, 1} tells movement direction in x-axis
    int Y_Movement; // {-1, 0, 1} tells movement direction in y-axis
    
    long last_move; // time in nanoseconds of last movement
    
    int AI_State; // For use in Artificial_Intelligence function
    
    public Character(int tile_x, int tile_y, boolean visible,
            int sprite_shift, int tilesize, // how far sprite is shifted and size in pixels
            SpriteSheet sprites, int[] spritesheet_index, int[] animation_length,
            int current_animation,
            int health, float speed,
            int x_movement, int y_movement) {
        
        // Constructs the "Object" part of Character
        super(tile_x, tile_y, visible, sprite_shift, tilesize, sprites,
                spritesheet_index, animation_length, current_animation);
        
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
        String[][] frame_top = new String[4][5];
        String[][] frame_bot = new String[4][5];
        
        Init(0, 0, false,
            0, 0, // how far sprite is shifted and size in pixels
            null, null, null,
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
            int sprite_shift, int tilesize, // how far sprite is shifted and size in pixels
            SpriteSheet sprites, int[] spritesheet_index, int[] animation_length,
            int current_animation,
            int health, float speed,
            int x_movement, int y_movement){
        
        int frames_count = 5;
        int[] frame_count = new int[5];
        frame_count[0] = 1;
        frame_count[1] = 4;
        frame_count[2] = 4;
        frame_count[3] = 4;
        frame_count[4] = 4;
        
        Init(tile_x, tile_y, visible,
            sprite_shift, tilesize, // how far sprite is shifted and size in pixels
            sprites, spritesheet_index, animation_length,
            current_animation);
        
        Health = health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }
    /*
    // Initialization for 16x16 Character
    public void Init(int x_position, int y_position,
            String[][] frame_bot,
            int current_frames, boolean top_visible,
            boolean bot_visible,
            int health, float speed, int x_movement,
            int y_movement){
        
        int frames_count = 5;
        int[] frame_count = new int[5];
        frame_count[0] = 1;
        frame_count[1] = 4;
        frame_count[2] = 4;
        frame_count[3] = 4;
        frame_count[4] = 4;
        
        Init(x_position, y_position,
            frame_bot, frames_count,
            frame_count,
            top_visible, bot_visible);
        
        Health = health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }
*/
    
    // Updates the Character
    public void Update(Object collision_objects[]){
        
        Update_Frame_State();
        
        boolean collided = Collision(collision_objects);
        
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
            Current_Animation = Animation_List[FRAME_STATE_DOWN_WALK];
        }
        
        else if (Y_Movement == -1) {
            Current_Animation = Animation_List[FRAME_STATE_UP_WALK];
        }
        
        else
        {
            if (X_Movement == 1) {
                Current_Animation = Animation_List[FRAME_STATE_RIGHT_WALK];
            }
            
            else if (Y_Movement == -1) {
                Current_Animation = Animation_List[FRAME_STATE_LEFT_WALK];
            }
            
            else
            {
                if (Current_Animation == Animation_List[FRAME_STATE_UP_WALK]) {
                    Current_Animation = Animation_List[FRAME_STATE_UP];
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_LEFT_WALK]) {
                    Current_Animation = Animation_List[FRAME_STATE_LEFT];
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_DOWN_WALK]) {
                    Current_Animation = Animation_List[FRAME_STATE_DOWN];
                }
                else if (Current_Animation == Animation_List[FRAME_STATE_RIGHT_WALK]) {
                    Current_Animation = Animation_List[FRAME_STATE_RIGHT];
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
        
        Health--;
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
