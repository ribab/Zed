package zed;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

public class Health_Flower extends GCharacter {
	
	static int Type = 20;
	
	// Zombie default constructor does nothing
	public Health_Flower() throws SlickException{
	}
	
	// Zombie constructor
	public Health_Flower(int tile_x, int tile_y) throws SlickException {

		SpriteSheet sprites = new SpriteSheet("images/Heart-Flower.png", 16, 16);
		
	    Tilesize = 16;
		Animation[] animations = new Animation[4];
		
		// Define index of first animation on spritesheet
		int i0 = 0;
		// Define relative indexes of animations on spritesheet
		int[] spritesheet_index = {i0, i0, i0, i0};
		// Define the length of each animation
		int[] animation_length = {1, 1, 1, 1};
		// Define whether each animation loops
		boolean[] looping = {false, false, false, false};
		// Define how each animation is shifted relative to position
		int[] sprite_shift_x = {0, 0, 0, 0};
		int[] sprite_shift_y = {0, 0, 0, 0};
		
		// Initialize walking and standing animations
        for (int i = 0; i < 4; i++)
        {
        	STUN_TIME = 1000; // Set the amount of time stunned if
        	                  // hit by player's sword
        	
	        animations[i] = new Animation(
	        		sprites, // spritesheet to use
	        		// location of first sprite in spritesheet
	                0,                       spritesheet_index[i],
	                // location of last sprite in spritesheet
	                animation_length[i] - 1, spritesheet_index[i],
	                false, // horizontalScan value
	                200, // speed value for animation
	                true // autoupdate value
	                );
	        animations[i].setLooping(true); // intialize whether animation loops
	        animations[i].setPingPong(true); // initialize whether animation ping-pongs between last and first frame
        }
        
        // Define which animations are for which states
        FRAME_STATE_UP = 3; FRAME_STATE_DOWN = 0;
        FRAME_STATE_LEFT = 1; FRAME_STATE_RIGHT = 2;
        FRAME_STATE_UP_WALK = 3; FRAME_STATE_DOWN_WALK = 0;
        FRAME_STATE_LEFT_WALK = 1; FRAME_STATE_RIGHT_WALK = 2;
		
        // Initialize zombie based on constructor
		super.Init(
				tile_x, tile_y, // Tile to put in
				16, 16,
				true, false, 1, // Visible? Solid?
				sprite_shift_x, sprite_shift_y, // shift for each animation
				16, // Size of a tile
				animations, 0, // Initialize animations and current animation
				1, // Health value
				1.0f, // Speed value in tiles per second
				0, 0); // Initial movement values
		
		Damage = -1;
		Hurt_Sound = new Sound("soundtrack/effects/sword_hit_flesh.wav");
		
		X_Movement = 0; Y_Movement = 0;
	}
	
	// Override GCharacter's Artificial Intelligence function
	// Knows if this character has collided and knows player's position
	public void Artificial_Intelligence(boolean collision, Player_Character player)
	{
		if (player.X_Collision(this) || player.Y_Collision(this))
		{
			player.Decrease_Health(Damage);
			Health = 0;
		}
	}
	public int Get_Type(){
		
		return Type;
	}
}
