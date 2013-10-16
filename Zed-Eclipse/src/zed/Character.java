/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zed;


public class Character extends Object {
    
    
    int Health;
    int Speed;
    int Movement;
    // Movement can equal these values
    private static final int STILL = 0;
    private static final int UP = 1;
    private static final int LEFT = 2;
    private static final int DOWN = 3;
    private static final int RIGHT = 4;

    public Character(int x_position, int y_position,
            String[][] frame, int frames_count,
            int[] frame_count, int current_frames,
            boolean visible) {
        super(x_position, y_position, frame, frames_count, frame_count, current_frames, visible);
    }

    public Character() {
        String[][] frame_top = new String[4][5];
        String[][] frame_bot = new String[4][5];
        Init(0, 0, frame_bot, frame_top, 0, false,
                0, 0, 0);
        Health = 0;
        Speed = 0;
        Movement = 0;
    }
    
    // frames cannot have array size less than 4x5
    public void Init(int x_position, int y_position,
            String[][] frame_bot, String[][] frame_top, 
            int current_frames, boolean visible,
            int health, int speed, int movement){
        
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
        Movement = movement;
    }
    
}
