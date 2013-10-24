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

// Slick for exception handling
import org.newdawn.slick.SlickException;

/**
 *
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
            
            // Test-level
            Level_Manager test;

            // Game Initialization
            @Override
            public void init(GameContainer gc) throws SlickException {
                
                // Initialize test-images
                link_down_bot = new Image("images/link-down-bot.png",
                        false, Image.FILTER_NEAREST);
                link_down_top = new Image("images/link-down-top.png",
                        false, Image.FILTER_NEAREST);
                
                // Initialize test-level
                test = new Level_Manager("levels/test.lvl");
            }

            // Game Updates
            @Override
            public void update(GameContainer gc, int delta) throws SlickException {
                // TODO: call character input functions
            }

            // Game Rendering
            @Override
            public void render(GameContainer gc, Graphics g) throws SlickException {
                
                test.display(gc, g);
                
                g.drawImage(link_down_bot, 50, 100);
                g.drawImage(link_down_top, 50, 84);
                
                g.drawImage(link_down_bot,
                        -20,
                        100,
                        -20 + link_down_bot.getWidth()*2,
                        100 + link_down_bot.getHeight()*2,
                        0, 0,
                        link_down_bot.getWidth(),
                        link_down_bot.getHeight());
                g.drawImage(link_down_top,
                        -20,
                        100 - 16*2,
                        -20 + 16*2,
                        100,
                        0, 0,
                        16, 16);
                
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
