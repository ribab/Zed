package zed;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState {
	Image exit_button;
	Image play_button;
	
	public Menu(int state){}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		exit_button = new Image("images/pressedbutton.png");
		play_button = new Image("images/button.png");
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawString("Zed Prototype", 100, 50);
		g.drawString("Play Level", 100, 100);
		g.drawString("Exit Game", 100, 200);
		play_button.draw(100, 105);
		exit_button.draw(100, 205);		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		//XXX put int he logic for when a button is pressed
		// I have the code written down on paper, just need to
		// figure out coords and type it in
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		if (((posX > 100) && (posX <100)) && ((posY > 100) && (posY < 100))){
			if (Mouse.isButtonDown(0)){
				sbg.enterState(1);
			}
		}
		
		if (((posX > 100) && (posX <100)) && ((posY > 100) && (posY < 100))){
			if (Mouse.isButtonDown(0)){
				System.exit(0);
			}
		}
		
		
		
	}

	@Override
	public int getID() {
		return 0;
	}
	

	
}
