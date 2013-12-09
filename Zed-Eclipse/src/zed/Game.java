package zed;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame {
	public static final String gamename = "zed";
	public static final int menu = 0;
	public static final int zed = 1;
	public static final int gameover = 2;
	public static final int victory = 3;
	
	// Construct the game
	public Game() {
		super("Zed");
		this.addState(new Menu(menu));
		this.addState(new Zed(zed));
		this.addState(new GameOver(gameover));
		this.addState(new Victory(victory));
	}
	
	// Construct the game with a different name
	public Game(String gamename) {
		super(gamename);
		this.addState(new Menu(menu));
		this.addState(new Zed(zed));
		this.addState(new GameOver(gameover));
		this.addState(new Victory(victory));
	}
	
	// Initialize the state to start in
	public void initStatesList(GameContainer gc) throws SlickException {
		this.enterState(menu);
	}
	
	// Main entry point for the game process
	public static void main(String[] args){
		AppGameContainer appgc;
		try{appgc = new AppGameContainer(new Game(gamename));
		//gc.setDisplayMode(640, 360, false);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
			Logger.getLogger(Zed.class.getName()).log(Level.SEVERE,null, e);			
		}
		
	}
}
