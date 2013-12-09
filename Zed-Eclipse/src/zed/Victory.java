package zed;

import java.io.File;
import java.io.FileNotFoundException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

// Victory screen that displays when the main character wins
public class Victory extends BasicGameState {

	Image victory_background; // Background image to display
	private int score = -1; // Score to display
	
	// Calls BasicGameState's constructors
	public Victory(){}
	public Victory(int state){}

	// Initialize the background image
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		victory_background = new Image("images/zedlow.png");
	}

	// Render the background image and score
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		victory_background.draw(0,0, 640, 480);
		g.setColor(Color.blue);
		g.scale(1.0f, 1.0f);
		g.drawString("YOU HAVE WON!!!: PRESS ENTER TO GO BACK TO MENU AND SEE HIGH SCORES", 15, 350);
		
		if (score == -1)
			score = Zed.test.objectives.getScore();
		g.drawString("YOUR SCORE: ", 25, 380);
		g.drawString("" + score, 25, 393);
		
	}

	// Look for input to get out of victory screen
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		
		if (input.isKeyDown(Input.KEY_ENTER)){
				
				sbg.getState(1).init(gc, sbg);
				sbg.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 3;
	}
	
}
