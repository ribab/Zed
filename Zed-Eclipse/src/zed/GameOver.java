package zed;

import java.io.File;
import java.io.FileNotFoundException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

// Gameover screen displays when the game ends
public class GameOver extends BasicGameState{
	
	Image over_background; // Background to display
	private int score = -1; // Score to display
	
	// Constructors just call BasicGameState constructors
	public GameOver(){}
	public GameOver(int state){}

	// Initialize the background
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		over_background = new Image("images/gameoverlow.png");
	}

	// Render the gameover screen
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		over_background.draw(0,0, 640, 480);
		g.setColor(Color.blue);
		g.scale(1.0f, 1.0f);
		g.drawString("YOU HAVE DIED: PRESS SPACEBAR TO RETURN TO MENU", 115, 350);
		
		// Initialize and draw the gameover score
		if (score == -1)
			score = (new Zed()).test.objectives.getPoints();
		g.drawString("YOUR SCORE: ", 25, 380);
		g.drawString("" + score, 25, 393);
		
	}

	// Accept input for the gameover screen
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		
		if (input.isKeyDown(Input.KEY_SPACE)){
				
				sbg.getState(1).init(gc, sbg);
				sbg.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 2;
	}

}