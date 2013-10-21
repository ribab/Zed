/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author ribab
 */
public class Object {
    
    private static final int ANIMATION_SPEED = 10;
    
    int X_Position;
    int Y_Position;
    Image[][] Frames_List_Bot;
    Image[][] Frames_List_Top;
    int State_Count;
    int Frame_Count[];
    boolean Top_Visible;
    boolean Bot_Visible;
    
    int Frame_State;
    int Frame_Top;
    int Frame_Bot;
        
    // Constructor for just bot_frame
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
    
    // Constructor for bot_frame and top_frame
    public Object(int x_position, int y_position,
            String bot_frame[][], String top_frame[][],
            int frames_count, int frame_count[],
            boolean top_visible, boolean bot_visible){
        
        Init(x_position, y_position,
            bot_frame, top_frame,
            frames_count, frame_count,
            top_visible, bot_visible);
        
        Frame_State = 0;
        Frame_Top = 0;
        Frame_Bot = 0;
    }
    
    public Object(){
        
        int[] temp = new int[1];
        temp[0] = 0;
        
        Init(0, 0, null, 0, temp, false, false);
        
        Frame_State = 0;
        Frame_Top = 0;
        Frame_Bot = 0;
    }
    
    // Init for only bot frame
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
    
    public void Move(int new_x_pos, int new_y_pos){
        
        X_Position = new_x_pos;
        Y_Position = new_y_pos;
    }
    
    public int Get_X_Position(){
        
        return X_Position;
    }
    
    public int Get_Y_Position(){
        
        return Y_Position;
    }
    
    public boolean Alligned_With_Tiles(int zoom)
    {
        return (X_Position % zoom == 0 &&
                Y_Position % zoom == 0);
    }
    
    public void Render_Bot(int zoom,
            GameContainer gc, Graphics g){
        
        if (Frame_Top >= Frame_Count[Frame_State])
        {
            Frame_Top = 0;
        }
        if (Bot_Visible)
        {
            if (Frame_Bot < Frame_Count[Frame_State] && Frame_Bot >= 0)
            {
                g.drawImage(Frames_List_Bot[Frame_State][Frame_Bot],
                        X_Position,
                        Y_Position,
                        X_Position + 16*zoom,
                        Y_Position + 16*zoom,
                        0, 0,
                        16, 16);
            }
        }
        Frame_Bot++;
    }
    
    public void Render_Top(int zoom,
            GameContainer gc, Graphics g){
        
        if (Frame_Top >= Frame_Count[Frame_State])
        {
            Frame_Top = 0;
        }
        // render corrent frame
        if (Top_Visible)
        {
            if (Frame_Top < Frame_Count[Frame_State]
                    && Frame_Top >= 0)
            {
                g.drawImage(Frames_List_Top[Frame_State][Frame_Top],
                        X_Position,
                        Y_Position,
                        X_Position + 16*zoom,
                        Y_Position + 16*zoom,
                        0, 0,
                        16, 16);
            }
        }
        Frame_Top++;
    }
    
    public void Change_Frame_State(int new_state){
        
        if (new_state >= 0 && new_state < State_Count)
        {
            Frame_State = new_state;
            Frame_Bot = 0;
            Frame_Top = 0;
        }
    }
    
    public int Get_Frame_State(){
        
        return Frame_State;
    }
}