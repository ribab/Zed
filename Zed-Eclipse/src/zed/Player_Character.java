
package zed;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Richard Barella Jr
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class Player_Character extends Character {
    
	private boolean Sword_Drawn;
	
    public Player_Character() {

        Init(0, 0, false,
            null, null, 0, // how far sprite is shifted and size in pixels
            null, null, null, null,
            0);
        
        Health = 0;
        Max_Health = 0;
        Speed = 0;
        X_Movement = 0;
        last_move = System.nanoTime();
        
        Sword_Drawn = false;
    }
    
    // Initialize with SpriteSheet and animation locations defined
    public Player_Character(int tile_x, int tile_y, boolean visible,
            int[] sprite_shift_x, int[] sprite_shift_y, int tilesize, // how far sprite is shifted and size in pixels
            SpriteSheet sprites, int[] spritesheet_index, int[] animation_length, boolean[] looping,
            int current_animation,
            int health, float speed,
            int x_movement, int y_movement){
        
        super(tile_x, tile_y, visible,
            sprite_shift_x, sprite_shift_y, tilesize, // how far sprite is shifted and size in pixels
            sprites, spritesheet_index, animation_length, looping,
            current_animation,
            health, speed,
            x_movement, y_movement);
        
        Sword_Drawn = false;
    }
    
    // Initialize with animations defined
    public Player_Character(int tile_x, int tile_y, boolean visible,
    		int[] sprite_shift_x, int[] sprite_shift_y, int tilesize,
    		Animation[] animation_list, int current_animation,
    		int health, float speed,
    		int x_movement, int y_movement){
    	
    	super(tile_x, tile_y, visible, sprite_shift_x, sprite_shift_y,
    			tilesize, animation_list, current_animation,
    			health, speed, x_movement, y_movement);
    	
    	Sword_Drawn = false;
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
    
    // sets the correct 
    public void Start_Sword_Attack(){
    	
    	if (Current_Animation == Animation_List[0] || Current_Animation == Animation_List[4]) // attack up
    	{
    		Change_Animation(8);
    		Current_Animation.start();
    		Sword_Drawn = true;
    	}
    	else if (Current_Animation == Animation_List[1] || Current_Animation == Animation_List[5]) // Attack left
    	{
    		Change_Animation(9);
    		Current_Animation.start();
    		Sword_Drawn = true;
    	}
    	else if (Current_Animation == Animation_List[2] || Current_Animation == Animation_List[6]) // Attack down
    	{
    		Change_Animation(10);
    		Current_Animation.start();
    		Sword_Drawn = true;
    	}
    	else if (Current_Animation == Animation_List[3] || Current_Animation == Animation_List[7])
    	{
    		Change_Animation(11);
    		Sword_Drawn = true;
    	}
    }
    
    public void End_Sword_Attack(){
    	
    	Sword_Drawn = false;
    	
    	if (Current_Animation == Animation_List[8]) // Stop attacking up
    	{
    		Current_Animation.restart();
    		Change_Animation(0);
    	}
    	else if (Current_Animation == Animation_List[9]) // Stop attacking left
    	{
    		Current_Animation.restart();
    		Change_Animation(1);
    	}
    	else if (Current_Animation == Animation_List[10]) // Stop attacking down
    	{
    		Current_Animation.restart();
    		Change_Animation(2);
    	}
    	else if (Current_Animation == Animation_List[11]) // Stop attacking right
    	{
    		Current_Animation.restart();
    		Change_Animation(3);
    	}
    }
    
    void Update_Frame_State(){
    	
    	if (Sword_Drawn == false)
    	{
    		super.Update_Frame_State();
    	}
    }
    
    // gets the position of the point of the sword.
    // If sword isn't drawn, returns -1
    public int Get_Sword_Pos_X(){
    	
    	if (Sword_Drawn && Current_Animation.getFrame() == 1)
    	{
    		if (Current_Animation == Animation_List[8]) // attack up
    		{
    			return X_Position + 16/2;
    		}
    		else if (Current_Animation == Animation_List[9]) // attack left
    		{
    			return X_Position - 16/2;
    		}
    		else if (Current_Animation == Animation_List[10]) // attack down
    		{
    			return X_Position + 16/2;
    		}
    		else if (Current_Animation == Animation_List[11]) // attack right
    		{
    			return X_Position + 16 + 16/2;
    		}
    	}
    	return -1;
    }
    
    // gets the position of the point of the sword.
    // If the sword isn't drawn, returns -1
    public int Get_Sword_Pos_Y(){
    	
    	if (Sword_Drawn && Current_Animation.getFrame() == 1)
    	{
    		if (Current_Animation == Animation_List[8]) // attack up
    		{
    			return Y_Position - 16/2;
    		}
    		else if (Current_Animation == Animation_List[9]) // attack left
    		{
    			return Y_Position + 16/2;
    		}
    		else if (Current_Animation == Animation_List[10]) // attack down
    		{
    			return Y_Position + 16 + 16/2;
    		}
    		else if (Current_Animation == Animation_List[11]) // attack right
    		{
    			return Y_Position + 16/2;
    		}
    	}
    	return -1;
    }
}
