
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
	private static final int FRAME_STATE_DOWN = 0;
    private static final int FRAME_STATE_UP = 1;
    private static final int FRAME_STATE_LEFT = 2;
    private static final int FRAME_STATE_RIGHT = 4;
    private static final int FRAME_STATE_DOWN_WALK = 5;
    private static final int FRAME_STATE_UP_WALK = 6;
    private static final int FRAME_STATE_LEFT_WALK = 7;
    private static final int FRAME_STATE_RIGHT_WALK = 8;
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

    public void Update(Object collision_objects[]){
	Update_Frame_State();
	        
        boolean collided = Collision(collision_objects);
	        
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
