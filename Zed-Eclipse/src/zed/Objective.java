package zed;

/*
 * Objectives consist of:
 * Killing a number of a certain NPC
 * Exploring a certain map
 * Obtaining a number of a certain item
 */

public class Objective {

	private boolean Completed;
	
	private int Cur;
	
	private int PrevCount;
	
	private int Type;
	private int Max;
	private int Level;
	private String Message;
	private long MessageTimeMilli;
	private int MessageX;
	private int MessageY;
	
	public Objective(int type, int max, int level, String message, int messagex, int messagey, int messagetimemilli) {
		
		Message = message;
		MessageX = messagex;
		MessageY = messagey;
		MessageTimeMilli = messagetimemilli;
		PrevCount = 0;
		
		Completed = false;
		Cur = 0;
		Type = type;
		Max = max;
		Level = level;
	}
	
	public Objective(int level, String message, int messagex, int messagey, long messagetimemilli){
		
		Message = message;
		MessageX = messagex;
		MessageY = messagey;
		MessageTimeMilli = messagetimemilli;
		PrevCount = 0;
		
		Completed = false;
		Cur = 0;
		Type = -1;
		Max = 0;
		Level = level;
	}
	
	public Objective(int type, int max, int level, int cur, boolean completed, String message, int messagex, int messagey, long messagetimemilli) {
		
		Message = message;
		MessageX = messagex;
		MessageY = messagey;
		MessageTimeMilli = messagetimemilli;
		Completed = completed;
		Cur = cur;
		Type = type;
		Max = max;
		Level = level;
	}
	
	void Give_Object_Count(int number_of_objects)
	{
		PrevCount = number_of_objects;
	}
	
	boolean isCompleted() {
		
		return Completed;
	}
	
	float percentCompleted() {
		
		if (Max != 0)
			return Cur/Max;
		else
			return (Completed?1.0f:0.0f);
	}
	
	int getType() {
		
		return Type;
	}
	
	void Update(int level, int count)
	{
		if (level == Level)
		{
			Cur += (PrevCount - count);
			if (Cur >= Max)
			{
				Completed = true;
			}
		}
	}
	
	String getMessage(){
		
		return Message;
	}
	
	long getMessageTimeMilli(){
		
		return MessageTimeMilli;
	}
	
	int getMessageX(){
		
		return MessageX;
	}
	
	int getMessageY(){
		
		return MessageY;
	}
}
