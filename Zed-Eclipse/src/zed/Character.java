/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;


public class Character extends Object {
    
    
    int Health;
    float Speed; // tiles per second
    int X_Movement;
    int Y_Movement;
    
    long last_move; // time of last update
    
    public Character(int x_position, int y_position,
            String[][] frame, int frames_count,
            int[] frame_count, int current_frames,
            boolean visible, int health, float speed,
            int x_movement, int y_movement) {
        super(x_position, y_position, frame, frames_count, frame_count, current_frames, visible);
        
        Health = health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }

    public Character() {
        String[][] frame_top = new String[4][5];
        String[][] frame_bot = new String[4][5];
        Init(0, 0, frame_bot, frame_top, 0, false,
                0, 0, 0, 0);
        Health = 0;
        Speed = 0;
        X_Movement = 0;
        last_move = System.nanoTime();
    }
    
    // frames cannot have array size less than 4x5
    public void Init(int x_position, int y_position,
            String[][] frame_bot, String[][] frame_top, 
            int current_frames, boolean visible,
            int health, float speed, int x_movement,
            int y_movement){
        
        int frames_count = 5;
        int[] frame_count = new int[5];
        frame_count[0] = 1;
        frame_count[1] = 4;
        frame_count[2] = 4;
        frame_count[3] = 4;
        frame_count[4] = 4;
        
        Init(x_position, y_position,
            frame_bot, frame_top, frames_count,
            frame_count, current_frames,
            visible);
        
        Health = health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }
    public void Init(int x_position, int y_position,
            String[][] frame_bot,
            int current_frames, boolean visible,
            int health, float speed, int x_movement,
            int y_movement){
        
        int frames_count = 5;
        int[] frame_count = new int[5];
        frame_count[0] = 1;
        frame_count[1] = 4;
        frame_count[2] = 4;
        frame_count[3] = 4;
        frame_count[4] = 4;
        
        Init(x_position, y_position,
            frame_bot, frames_count,
            frame_count, current_frames,
            visible);
        
        Health = health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }
    
    public void Update(){
        if (X_Position != 0 && Y_Position != 0)
        {
            if (System.nanoTime() >= last_move
                + (long)(1000000000.0/(Speed*0.70710678118*16.0)))
            {
                X_Position += X_Movement; // TODO: check for collision
                Y_Position += Y_Movement;
            
                last_move = System.nanoTime();
            }
        }
        else 
        {
            if (System.nanoTime() >= last_move
                    + (long)(1000000000.0/(Speed*16.0)))
            {
                X_Position += X_Movement; // TODO: check for collision
                Y_Position += Y_Movement;

                last_move = System.nanoTime();
            }
        }
    }
    
    public int Get_Health(){
        
        return Health;
    }
    public float Get_Speed(){
        
        return Speed;
    }
    public int Get_X_Movement(){
        
        return X_Movement;
    }
    public int Get_Y_Movement(){
        
        return Y_Movement;
    }
    
    public void Artificial_intelligence(){
        
        // TODO: Make AI Code
    }
}
