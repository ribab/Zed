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
    int Frames_Count;
    int Frame_Count[];
    int Current_Frames;
    int Current_Frame;
    boolean Visible;
        
    public Object(int x_position, int y_position,
            String frame[][], int frames_count,
            int frame_count[], int current_frames,
            boolean visible){
        
        Init(x_position, y_position,
            frame, frames_count,
            frame_count, current_frames,
            visible);
    }
    
    public Object(){
        
        int[] temp = new int[1];
        temp[0] = 0;
        
        Init(0, 0, null, 0, temp, 0, false);
    }
    
    public void Init(int x_position, int y_position,
            String frame[][], int frames_count,
            int frame_count[], int current_frames,
            boolean visible){
        
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
        Current_Frames = current_frames;
        Current_Frame = 0;
        Visible = visible;
    }
    
    public void Init(int x_position, int y_position,
            String frame[][], String frame_top[][], int frames_count,
            int frame_count[], int current_frames,
            boolean visible){
        
        Init(x_position, y_position,
            frame, frames_count,
            frame_count, current_frames,
            visible);
        
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
    
    public void Render_Bot(GameContainer gc,
            Graphics g, int zoom){
        
        g.drawImage(Frames_List_Bot[Current_Frames][Current_Frame],
                X_Position,
                Y_Position,
                X_Position + 16*zoom,
                Y_Position + 16*zoom,
                0, 0,
                16, 16);
    }
    
    public void Render_Top(GameContainer gc,
            Graphics g, int zoom){
        
        g.drawImage(Frames_List_Top[Current_Frames][Current_Frame],
                X_Position,
                Y_Position - 16*zoom,
                X_Position + 16*zoom,
                Y_Position,
                0, 0,
                16, 16);
    }
}
