/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author ribab
 */
public class Zed {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        org.newdawn.slick.BasicGame game = new org.newdawn.slick.BasicGame("zed") {
            Image link_down_bot;
            Image link_down_top;
            
            Level_Manager test;

            @Override
            public void init(GameContainer gc) throws SlickException {
                link_down_bot = new Image("images/link-down-bot.png",
                        false, Image.FILTER_NEAREST);
                link_down_top = new Image("images/link-down-top.png",
                        false, Image.FILTER_NEAREST);
                
                test = new Level_Manager("levels/test.lvl");
                // TODO: code initialization
            }

            @Override
            public void update(GameContainer gc, int i) throws SlickException {
                // TODO: code update
            }

            @Override
            public void render(GameContainer gc, Graphics g) throws SlickException {
                
                test.display(gc, g);
                
                g.drawImage(link_down_bot, 50, 100);
                g.drawImage(link_down_top, 50, 84);
                
                g.drawImage(link_down_bot, 100, 100,
                        100 + link_down_bot.getWidth()*2,
                        100 + link_down_bot.getHeight()*2,
                        0, 0,
                        link_down_bot.getWidth(),
                        link_down_bot.getHeight());
                g.drawImage(link_down_top,
                        100,
                        100 - 16*2,
                        100 + 16*2,
                        100,
                        0, 0,
                        16, 16);
                
                // TODO: code render
                
            }
        };
        AppGameContainer container;
        try {
            container = new AppGameContainer(game);
            container.start();
        } catch (SlickException ex) {
            ex.printStackTrace();
            Logger.getLogger(Zed.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO code application logic here
    }
}
