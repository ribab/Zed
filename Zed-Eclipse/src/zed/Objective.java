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
	
	private int Points;
	
	private boolean Neccesary;
	
	// constructor for destroying GObjects objective
	public Objective(int type, int max, int level, String message, int messagex, int messagey, int messagetimemilli, int points, boolean neccesary) {
		
		Points = points;
		Neccesary = neccesary;
		
		Message = message;
		MessageX = messagex;
		MessageY = messagey;
		MessageTimeMilli = messagetimemilli;
		PrevCount = -1;
		
		Completed = false;
		Cur = 0;
		Type = type;
		Max = max;
		Level = level;
	}
	
	// constructor for visiting level objective
	public Objective(int level, String message, int messagex, int messagey, long messagetimemilli, int points, boolean neccesary){
		
		Points = points;
		Neccesary = neccesary;
		
		Message = message;
		MessageX = messagex;
		MessageY = messagey;
		MessageTimeMilli = messagetimemilli;
		PrevCount = -1;
		
		Completed = false;
		Cur = 0;
		Type = -1;
		Max = 0;
		Level = level;
	}
	
	// Specific Constructor
	public Objective(int type, int max, int level, int cur, boolean completed, String message, int messagex, int messagey, long messagetimemilli, int points, boolean neccesary) {
		
		Points = points;
		Neccesary = neccesary;
		
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
	
	void Complete()
	{
		Completed = true;
	}
	
	void Give_Object_Count(int number_of_objects)
	{
		PrevCount = number_of_objects;
	}
	
	boolean isCompleted() {
		
		return Completed;
	}
	
	boolean isNeccesary() {
		
		return Neccesary;
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
			if (PrevCount < 0)
			{
				PrevCount = count;
			}
			Cur += (PrevCount - count);
			PrevCount = count;
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
	
	int getPoints(){
		
		return Points;
	}
	
	int getLevel(){
		
		return Level;
	}
}
