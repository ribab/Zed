/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;

// Java for exception handling
import java.util.logging.Level;
import java.util.logging.Logger;

// Slick for creating game
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;

// Slick for drawing to screen
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;



// Slick for exception handling
import org.newdawn.slick.SlickException;

/**
 * @author Richard Barella Jr.
 * @author Adam Bennett
 * @author Ryan Slyter
 */
public class Zed {
    // Main function
    public static void main(String[] args) {
        
        // create game
        org.newdawn.slick.BasicGame game = new org.newdawn.slick.BasicGame("zed") {
            
            // Test-images
            Image link_down_bot;
            Image link_down_top;
            float x_pos = 50;
            float y_pos = 50;
           
            // Test-level
            Level_Manager test;
            // Game Initialization
            @Override
            public void init(GameContainer gc) throws SlickException {
                /*
                // Initialize test-images
                link_down_bot = new Image("images/link-down-bot.png",
                        false, Image.FILTER_NEAREST);
                link_down_top = new Image("images/link-down-top.png",
                        false, Image.FILTER_NEAREST);
                */
                
                // Initialize test-level
                test = new Level_Manager();
            }

            // Game Updates
            @Override
            public void update(GameContainer gc, int delta) throws SlickException {
            	 boolean left = false; //these arent currently being used be needed
                 boolean right = false;
                 boolean up = false;
                 boolean down = false;
                 gc.setVSync(true);
                 
              	Input input = gc.getInput(); // get the current input
                 
                 if (input.isKeyDown(input.KEY_UP) || input.isKeyDown(input.KEY_W))
                 {
                     up = true;
                     //y_pos-=.05;
                 }
                 if (input.isKeyDown(input.KEY_LEFT) || input.isKeyDown(input.KEY_A))
                 {
                     left = true;
                     //x_pos-=.05;
                 }
                 if (input.isKeyDown(input.KEY_DOWN) || input.isKeyDown(input.KEY_S))
                 {
                     down = true;
                     //y_pos+=.05;
                 }
                 if (input.isKeyDown(input.KEY_RIGHT) || input.isKeyDown(input.KEY_D))
                 {
                     right = true;
                     //x_pos+=.05;
                 }
                 // change the player's movement value
                 test.move_player((right? 1:0) - (left? 1:0),
                         (down? 1:0) - (up? 1:0));
                 
                 test.update();
            }
            
            // Game Rendering
            @Override
            public void render(GameContainer gc, Graphics g) throws SlickException {
                
                test.display(gc, g);
                /*
                g.drawImage(link_down_bot, x_pos, y_pos);
                g.drawImage(link_down_top, x_pos, y_pos-16);
                */
                
                // TODO: code render
                
            }
        };
        
        AppGameContainer container;
        try {
            container = new AppGameContainer(game); // create game instance
            container.start();                      // start game instance
        } catch (SlickException ex) { // catch exceptions
            ex.printStackTrace();
            Logger.getLogger(Zed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
