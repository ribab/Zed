/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;

// Java for exception handling
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



// Slick for creating game
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;



// Slick for exception handling
import org.newdawn.slick.SlickException;

/**
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */
// State for playing the game
public class Zed extends BasicGameState {
	public static Level_Manager test;

	public Zed(){}
	
	public Zed(int state){}
            
            
            // Game Initialization
            @Override
            public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
               
				try {
					test = new Level_Manager();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            // Game Updates
            @Override
            public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
            	 boolean left = false; //these arent currently being used be needed
                 boolean right = false;
                 boolean up = false;
                 boolean down = false;
                 gc.setVSync(true); // makes it so computer doesn't heat up
                 //gc.setTargetFrameRate(120);
                 
                 Input input = gc.getInput(); // get the current input
                 
                 if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
                 {
                     up = true;
                 }
                 if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
                 {
                     left = true;
                 }
                 if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
                 {
                     down = true;
                 }
                 if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
                 {
                     right = true;
                 }
                 if (input.isKeyDown(Input.KEY_SPACE))
                 {
                	 test.player.Start_Sword_Attack();
                 }
                 else
                 {
                	 test.player.Sheath_Sword();
                 }
                 
                 //new code: going back to the menu from in-game
                 if (input.isKeyDown(Input.KEY_ESCAPE))
                 {
                	 sbg.enterState(0);
                 }
                 
                 test.move_player((right? 1:0) - (left? 1:0),
                         (down? 1:0) - (up? 1:0));
                 
                 try {
                	 test.update();
                	 if (test.player.Health <= 0){
                		 sbg.enterState(2); 
                	 }
                	 else if (test.objectives.percentageCompleted() == 1.0f){
                		 sbg.enterState(3);
                	 }
                 } catch (FileNotFoundException e) {
                	 // TODO Auto-generated catch block
                	 e.printStackTrace();
                 }
            }
            
            // Game Rendering
            @Override
            public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
                
                test.display(gc, g);
                /*
                g.drawImage(link_down_bot, x_pos, y_pos);
                g.drawImage(link_down_top, x_pos, y_pos-16);
                */
                
                // TODO: code render
                
            }
        
       public int getID(){
    	   return 1;
       }
       
       public Level_Manager getLevel(){
    	   
    	   return test;
       }
       
}
