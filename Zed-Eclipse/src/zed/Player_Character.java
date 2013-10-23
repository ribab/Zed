
package zed;

// Slick for input
import org.newdawn.slick.Input;
import org.newdawn.slick.GameContainer;

/**
 *
 * @author Richard Barella Jr
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class Player_Character extends Character {
    
    private static final int FRAME_STATE_DOWN = 0;
    private static final int FRAME_STATE_UP = 1;
    private static final int FRAME_STATE_LEFT = 2;
    private static final int FRAME_STATE_RIGHT = 4;
    private static final int FRAME_STATE_DOWN_WALK = 5;
    private static final int FRAME_STATE_UP_WALK = 6;
    private static final int FRAME_STATE_LEFT_WALK = 7;
    private static final int FRAME_STATE_RIGHT_WALK = 8;
    
    // Replaces the Update function in Character.java
    // Updates the Player_Character
    public void Update(Object collision_objects[], GameContainer gc){
        
        Input input = gc.getInput(); // get the current input
        
        boolean collided = Collision(collision_objects); // Find out if Player will collide
        
        if (!collided)
        {
            Update_Position(); // Updates the position
        }
        
        // Replace Artificial Intelligence with user input
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        if (input.isKeyDown(input.KEY_UP) || input.isKeyDown(input.KEY_W))
            up = true;
        if (input.isKeyDown(input.KEY_LEFT) || input.isKeyDown(input.KEY_RIGHT))
            left = true;
        if (input.isKeyDown(input.KEY_DOWN) || input.isKeyDown(input.KEY_S))
            down = true;
        if (input.isKeyDown(input.KEY_RIGHT) || input.isKeyDown(input.KEY_D))
            right = true;
        // Y_Movement
        if (up == down)
            Y_Movement = 0;
        else
        {
            if (up == true)
                Y_Movement = -1;
            else // down == true
                Y_Movement = 1;
        }
        // X_Movement
        if (right == left)
            X_Movement = 0;
        else
        {
            if (right == true)
                Y_Movement = 1;
            else
                Y_Movement = -1;
        }
    }
}
