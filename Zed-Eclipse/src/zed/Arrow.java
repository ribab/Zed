package zed;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

public class Arrow extends GCharacter {
	
	static int Type = 10;
	
	private int spawnx;
	private int spawny;
	
	// Rat default constructor does nothing
	public Arrow() throws SlickException{
	}
	
	// Rat constructor
	public Arrow(int tile_x, int tile_y, SpriteSheet sprites) throws SlickException {

		Tilesize = 16;
		spawnx = tile_x*Tilesize;
		spawny = tile_y*Tilesize;
		
		Animation[] animations = new Animation[4];
		
		// Define index of first animation on spritesheet
		int i0 = 0;
		// Define how each animation is shifted relative to position
		int[] sprite_shift_x = {0, 0, -8, -8};
		int[] sprite_shift_y = {0, -8, -8, 0};
    	
		// Initialize walking and standing animations
        for (int i = 0; i < 4; i++)
        {
        	STUN_TIME = 1000; // Set the amount of time stunned if
        	                  // hit by player's sword

        	Image[] arrowimg = {new Image("images/Arrow-LoZ-Sprite.png")};
        	if (i == 0)      arrowimg[0].setRotation(0);
        	else if (i == 1) arrowimg[0].setRotation(270);
        	else if (i == 2) arrowimg[0].setRotation(180);
        	else if (i == 3) arrowimg[0].setRotation(90);
        	
	        animations[i] = new Animation(arrowimg, 200);
	        animations[i].setLooping(true); // intialize whether animation loops
	        animations[i].setPingPong(true); // initialize whether animation ping-pongs between last and first frame
        }
        
        // Define which animations are for which states
        FRAME_STATE_UP = 0; FRAME_STATE_DOWN = 2;
        FRAME_STATE_LEFT = 1; FRAME_STATE_RIGHT = 3;
        FRAME_STATE_UP_WALK = 0; FRAME_STATE_DOWN_WALK = 2;
        FRAME_STATE_LEFT_WALK = 1; FRAME_STATE_RIGHT_WALK = 3;
		
        // Initialize zombie based on constructor
		super.Init(
				tile_x, tile_y, // Tile to put in
				16, 16,
				true, false, 1, // Visible? Solid? Damage?
				sprite_shift_x, sprite_shift_y, // shift for each animation
				16, // Size of a tile
				animations, 0, // Initialize animations and current animation
				1, // Health value
				20.0f, // Speed value in tiles per second
				0, 0); // Initial movement values
		
		AI_State_Change_Time = 500; // Time to change state for AI
		
		Hurt_Sound = new Sound("soundtrack/effects/sword_hit_flesh.wav");
		
		if (rnd.nextBoolean())
		{
			X_Movement = (rnd.nextBoolean()?2:-2);
			Y_Movement = 0;
		}
		else
		{
			X_Movement = 0;
			Y_Movement = (rnd.nextBoolean()?2:-2);
		}
	}
	
	// override update to respawn arrow
	public void Update(GObject[] collision_objects, GCharacter[] npcs, Player_Character player)
	{
		if (Get_Health() <= 0)
		{
			Increase_Health(1);
			Move(spawnx, spawny);
		}
		super.Update(collision_objects, npcs, player);
	}
	
	// Override GCharacter's Artificial Intelligence function
	// Knows if this character has collided and knows player's position
	public void Artificial_Intelligence(boolean collision, Player_Character player)
	{
		if (collision)
		{
			this.Decriment_Health();
		}
	}
	
	public int Get_Type(){
		
		return Type;
	}
}
