package zed;

import java.io.File;
import java.io.FileNotFoundException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

// The main menu screen for resuming a game, starting a new game, or quitting 
public class Menu extends BasicGameState {
	Image button;
	Image menu_background;
	Music music;
	
	// Calls BasicGameState constructors
	public Menu(){}
	public Menu(int state){}

	// Initialize images and music for menu
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		button = new Image("images/pressedbutton.png");
		menu_background = new Image("images/conceptfinallow.png");

        music = new Music("soundtrack/kawfy/braintwoquart.wav");
        music.loop();
	}

	// Render the menu
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		menu_background.draw(0,0, 640, 480);	
	
		button.draw(220, 260);
		button.draw(360, 260);
		button.draw(289, 260);
		g.drawString("res", 224, 267);
		g.drawString("exit", 364, 267);
		g.drawString("new", 297, 267);
		
		// New code: attempt to open scoreboard file and display top 3 scores
		// also change text color so that scores can be easily seen
		g.setColor(Color.orange);
		g.drawString("High Scores: ", 25, 150);
		File scorefile = new File("scoreboard.score");
		File_Manager scores = new File_Manager();
		
		try {
			int[] high_scores = scores.Scan_LVL(scorefile, 1)[0];
			if (high_scores.length < 3){
				int pos = 175;
				for (int i = 0; i < high_scores.length; i++){
					g.drawString("" + high_scores[high_scores.length - 1 - i],  20,  pos);
					pos+= 25;
				}
			}
			else{
				g.drawString("" + high_scores[high_scores.length - 1], 20, 175);
				g.drawString("" + high_scores[high_scores.length - 2], 20, 200);
				g.drawString("" + high_scores[high_scores.length - 3], 20, 225);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	// Accept input on the menu screen
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		//XXX put int he logic for when a button is pressed
		// I have the code written down on paper, just need to
		// figure out coords and type it in
		
		int posX = gc.getInput().getMouseX(); //detects user loading a saved game
		int posY = gc.getInput().getMouseY();
		if (((posX > 220) && (posX < 220 + 47))
				&& ((posY > 260) && (posY < 260 + 39))){
			if (gc.getInput().isMousePressed(0)){
				sbg.enterState(1);
			}
		}
		
		if (((posX > 289) && (posX < 289 + 47)) //detects user starting a new game
				&& ((posY > 260) && (posY < 260 + 39))){
			if (gc.getInput().isMousePressed(0)){
				sbg.getState(1).init(gc, sbg);
				sbg.enterState(1);
			}
		}
		
		if (((posX > 360) && (posX < 360 + 47)) && ((posY > 260) && (posY < 260 + 39))){ //detects user exiting the game
			if (Mouse.isButtonDown(0)){
				gc.exit();
			}
		}
	}

	@Override
	public int getID() {
		return 0;
	}
}
