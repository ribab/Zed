/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;


public class Character extends Object {
    
    private static final int FRAME_STATE_DOWN = 0;
    private static final int FRAME_STATE_UP = 1;
    private static final int FRAME_STATE_LEFT = 2;
    private static final int FRAME_STATE_RIGHT = 4;
    private static final int FRAME_STATE_DOWN_WALK = 5;
    private static final int FRAME_STATE_UP_WALK = 6;
    private static final int FRAME_STATE_LEFT_WALK = 7;
    private static final int FRAME_STATE_RIGHT_WALK = 8;
    
    int Health;
    float Speed; // tiles per second
    int X_Movement;
    int Y_Movement;
    
    long last_move; // time of last update
    
    int AI_State;
    
    public Character(int x_position, int y_position,
            String[][] frame, int frames_count,
            int[] frame_count, int current_frames,
            boolean top_visible, boolean bot_visible,
            int health, float speed,
            int x_movement, int y_movement) {
        super(x_position, y_position, frame, frames_count,
                frame_count, bot_visible);
        
        Health = health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
        
        AI_State = 0;
    }

    public Character() {
        String[][] frame_top = new String[4][5];
        String[][] frame_bot = new String[4][5];
        Init(0, 0, frame_bot, frame_top, false, false,
                0, 0, 0, 0);
        Health = 0;
        Speed = 0;
        X_Movement = 0;
        last_move = System.nanoTime();
    }
    
    // frames cannot have array size less than 4x5
    public void Init(int x_position, int y_position,
            String[][] frame_bot, String[][] frame_top, 
            boolean top_visible, boolean bot_visible,
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
            frame_bot, frame_top,
            frames_count, frame_count,
            top_visible, bot_visible);
        
        Health = health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }
    public void Init(int x_position, int y_position,
            String[][] frame_bot,
            int current_frames, boolean top_visible,
            boolean bot_visible,
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
            frame_count,
            top_visible, bot_visible);
        
        Health = health;
        Speed = speed;
        X_Movement = x_movement;
        Y_Movement = y_movement;
        
        last_move = System.nanoTime();
    }
    
    public void Update(boolean collided){
        
        if (!collided)
        {
            Update_Position();
        }
        Artificial_Intelligence(collided);
    }
    
    public void Update_Frame_State(){
        
        if (Y_Movement == 1)
            Frame_State = FRAME_STATE_DOWN_WALK;
        
        else if (Y_Movement == -1)
            Frame_State = FRAME_STATE_UP_WALK;
        
        else
        {
            if (X_Movement == 1)
                Frame_State = FRAME_STATE_RIGHT_WALK;
            
            else if (Y_Movement == -1)
                Frame_State = FRAME_STATE_LEFT_WALK;
            
            else
            {
                if (Frame_State == FRAME_STATE_UP_WALK)
                    Frame_State = FRAME_STATE_UP;
                else if (Frame_State == FRAME_STATE_LEFT_WALK)
                    Frame_State = FRAME_STATE_LEFT;
                else if (Frame_State == FRAME_STATE_DOWN_WALK)
                    Frame_State = FRAME_STATE_DOWN;
                else if (Frame_State == FRAME_STATE_RIGHT_WALK)
                    Frame_State = FRAME_STATE_RIGHT;
            }
        }
    }
    
    public void Update_Position(){
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
    
    private void Artificial_Intelligence(boolean collided){
        
        // TODO: Make AI Code
    }
}
