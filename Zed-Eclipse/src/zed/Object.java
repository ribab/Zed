
package zed;

// Java for exceptions
import java.util.logging.Level;
import java.util.logging.Logger;

// Slick for drawing to the screen
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Richard Barella Jr
 * @author Adam Bennett
 * @author Ryan Slyter
 */

// ============
// Object class
// ============
// The most basic out of classes that hold information on in-game
// objects and can also print itself to the screen.
public class Object {
    
    private static final int ANIMATION_SPEED = 10;
    
    // Holds the pixel-location of top-left of Object
    int X_Position;
    int Y_Position;
    
    // Holds the image files for the object.
    Image[][] Frames_List_Bot; // Image files for the bottom-half of the character.
    Image[][] Frames_List_Top; // Image files for the top-half of the character.
    // The separation is for draw-order so an object can stand in front of another object.
    
    // Holds the number of animations and the number of image files used in each animation.
    int State_Count;
    int Frame_Count[];
    
    // Holds the visibility of Object
    boolean Top_Visible; // top visibility
    boolean Bot_Visible; // bottom visibility
    
    // Locates the current image being displayed
    int Frame_State; // which animation to play
    int Frame_Top; // which frame in animation to display for top
    int Frame_Bot; // which frame in animation to display for bottom
        
    // Constructor for just bottom frame
    public Object(int x_position, int y_position,
            String bot_frame[][], int frames_count,
            int frame_count[],
            boolean bot_visible){
        
        Init(x_position, y_position,
            bot_frame, frames_count,
            frame_count,
            false, bot_visible);
        
        Frame_State = 0;
        Frame_Top = 0;
        Frame_Bot = 0;
    }
    
    // Constructor for bottom frame and top frame
    public Object(int x_position, int y_position,
            String bot_frame[][], String top_frame[][],
            int frames_count, int frame_count[],
            boolean top_visible, boolean bot_visible){
        
        Init(x_position, y_position,
            bot_frame, top_frame,
            frames_count, frame_count,
            top_visible, bot_visible);
    }
    
    // Default constructor
    public Object(){
        
        int[] temp = new int[1];
        temp[0] = 0;
        
        Init(0, 0, null, 0, temp, false, false);
    }
    
    // Init for only bottom frame
    public void Init(int x_position, int y_position,
            String frame[][], int frames_count,
            int frame_count[],
            boolean top_visible, boolean bot_visible){
        
        X_Position = x_position;
        Y_Position = y_position;
        for (int i = 0; i < frames_count; i++)
        {
            for (int j = 0; j < frame_count[i]; j++)
            {
                try {
                    Frames_List_Bot[i][j] = new Image(frame[i][j],
                            false, Image.FILTER_NEAREST);
                } catch (SlickException ex) {
                    Logger.getLogger(Object.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        Top_Visible = top_visible;
        Bot_Visible = bot_visible;
        
        Frame_State = 0;
        Frame_Top = 0;
        Frame_Bot = 0;
    }
    
    // Init for both top frame and bot frame
    public void Init(int x_position, int y_position,
            String frame[][], String frame_top[][], int frames_count,
            int frame_count[],
            boolean top_visible, boolean bot_visible){
        
        // call init for bot frame
        Init(x_position, y_position,
            frame, frames_count,
            frame_count,
            top_visible, bot_visible);
        
        // add in top frame
        for (int i = 0; i < frames_count; i++)
        {
            for (int j = 0; j < frame_count[i]; j++)
            {
                try {
                    Frames_List_Top[i][j] = new Image(frame_top[i][j],
                            false, Image.FILTER_NEAREST);
                } catch (SlickException ex) {
                    Logger.getLogger(Object.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    // Change the position of Object
    public void Move(int new_x_pos, int new_y_pos){
        
        X_Position = new_x_pos;
        Y_Position = new_y_pos;
    }
    
    // Get the object's current x position
    public int Get_X_Position(){
        
        return X_Position;
    }
    
    // Get the object's current y position
    public int Get_Y_Position(){
        
        return Y_Position;
    }
    
    // Can tell if the object is alligned with the tiles displayed
    // on the screen
    public boolean Alligned_With_Tiles(int zoom)
    {
        return (X_Position % zoom == 0 &&
                Y_Position % zoom == 0);
    }
    
    // Renders the current bottom image
    // must give zoom value, and location of top-left of tiles
    public void Render_Bot(int zoom,
            int current_tile_x, int current_tile_y,
            GameContainer gc, Graphics g){
        
        if (Frame_Top >= Frame_Count[Frame_State])
        {
            Frame_Top = 0; // Reset the current frame being displayed
        }
        // render current frame
        if (Bot_Visible)
        {
            if (Frame_Bot < Frame_Count[Frame_State] && Frame_Bot >= 0)
            {
                g.drawImage(Frames_List_Bot[Frame_State][Frame_Bot],
                        X_Position + current_tile_x,
                        Y_Position + current_tile_y,
                        X_Position + current_tile_x + 16*zoom,
                        Y_Position + current_tile_y + 16*zoom,
                        0, 0,
                        16, 16);
            }
        }
        Frame_Bot++; // Incriment the current frame being displayed
    }
    
    // renders the current top image
    // must give zoom value and locaiton of top-left of tiles
    public void Render_Top(int zoom, 
            int current_tile_x, int current_tile_y,
            GameContainer gc, Graphics g){
        
        if (Frame_Top >= Frame_Count[Frame_State])
        {
            Frame_Top = 0; // Reset the current frame being displayed
        }
        // render corrent frame
        if (Top_Visible)
        {
            if (Frame_Top < Frame_Count[Frame_State]
                    && Frame_Top >= 0)
            {
                g.drawImage(Frames_List_Top[Frame_State][Frame_Top],
                        X_Position + current_tile_x,
                        Y_Position + current_tile_y,
                        X_Position + current_tile_x + 16*zoom,
                        Y_Position + current_tile_y + 16*zoom,
                        0, 0,
                        16, 16);
            }
        }
        Frame_Top++; // Incriment the current frame being displayed
    }
    
    // Play a different animation for the object
    public void Change_Frame_State(int new_state){
        
        if (new_state >= 0 && new_state < State_Count)
        {
            Frame_State = new_state;
            Frame_Bot = 0;
            Frame_Top = 0;
        }
    }
    
    // Get the number for the current animation being played
    public int Get_Frame_State(){
        
        return Frame_State;
    }
}