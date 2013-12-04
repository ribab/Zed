package zed;

import java.io.File;
import java.io.FileNotFoundException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Victory extends BasicGameState {

	Image victory_background;
	
	
	public Victory(){}
	
	public Victory(int state){}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		victory_background = new Image("images/zedlow.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		victory_background.draw(0,0, 640, 480);
		g.setColor(Color.blue);
		g.scale(1.0f, 1.0f);
		g.drawString("YOU HAVE WON!!!: PRESS ENTER TO GO BACK TO MENU AND SEE HIGH SCORES", 15, 350);
		
		Zed temp = new Zed();
		g.drawString("YOUR SCORE: ", 25, 200);
		g.drawString("" + temp.test.objectives.percentageCompleted(), 25, 220);
		
	}

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
