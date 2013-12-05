package zed;

import java.io.File;
import java.io.FileNotFoundException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameOver extends BasicGameState{
	
	
	Image over_background;
	private int score = -1;
	
	public GameOver(){}
	
	public GameOver(int state){}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		over_background = new Image("images/gameoverlow.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		over_background.draw(0,0, 640, 480);
		g.setColor(Color.blue);
		g.scale(1.0f, 1.0f);
		g.drawString("YOU HAVE DIED: PRESS SPACEBAR TO RETURN TO MENU", 115, 350);
		
		if (score == -1)
			score = (new Zed()).test.objectives.getPoints();
		g.drawString("YOUR SCORE: ", 25, 380);
		g.drawString("" + score, 25, 393);
		
	}

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