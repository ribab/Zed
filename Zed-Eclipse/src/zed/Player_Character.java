
package zed;

// Slick for input
import org.newdawn.slick.Input;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Richard Barella Jr
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class Player_Character extends Character {
    
	//NOT SURE WE'RE EVER GOING TO USE THESE
	/**
    private static final int FRAME_STATE_UP = 0;
    private static final int FRAME_STATE_LEFT = 1;
    private static final int FRAME_STATE_DOWN = 2;
    private static final int FRAME_STATE_RIGHT = 3;
    private static final int FRAME_STATE_UP_WALK = 4;
    private static final int FRAME_STATE_LEFT_WALK = 5;
    private static final int FRAME_STATE_DOWN_WALK = 6;
    private static final int FRAME_STATE_RIGHT_WALK = 7;
    **/
    

    public Player_Character() {
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
    
    public Player_Character(int tile_x, int tile_y, boolean visible,
            int sprite_shift, int tilesize, // how far sprite is shifted and size in pixels
            SpriteSheet sprites, int[] spritesheet_index, int[] animation_length,
            int current_animation,
            int health, float speed,
            int x_movement, int y_movement){
        
        super(tile_x, tile_y, visible,
            sprite_shift, tilesize, // how far sprite is shifted and size in pixels
            sprites, spritesheet_index, animation_length,
            current_animation,
            health, speed,
            x_movement, y_movement);
    }

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
    }
    
    public void New_Movement(int new_x_mov, int new_y_mov){
        
        X_Movement = new_x_mov;
        Y_Movement = new_y_mov;
    }
}
