package zed;

/*
 * Objectives consist of:
 * Killing a number of a certain NPC
 * Exploring a certain map
 * Obtaining a number of a certain item
 */

public class Objective {

	// Variables that make up an objective
	private boolean Completed; // Tells whether the objective was completed
	private int Cur; // Tells how many monsters have been killed to complete objective
	private int PrevCount; // Tells how many monsters there were previously
	private int Type; // Tells the type of monster to kill
	private int Max; // Tells the max number of monsters to kill
	private int Level; // Tells the level on which to kill the monsters
	private String Message; // The message to display when the objective is completed
	private long MessageTimeMilli; // How long to display the message
	private int MessageX; // X position of the message
	private int MessageY; // Y position of the message
	private int Points; // How many points is awarded
	private boolean Neccesary; // Is the obejective necessary for completing the game
	
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
	
	// Completes the objective
	void Complete()
	{
		Completed = true;
	}
	
	// Gives the objective how many objects of its type there are currently
	void Give_Object_Count(int number_of_objects)
	{
		PrevCount = number_of_objects;
	}
	
	// Returns whether the objective has been completed
	boolean isCompleted() {
		
		return Completed;
	}
	
	// Returns whether the objective is necessary for completing the game
	boolean isNeccesary() {
		
		return Neccesary;
	}
	
	// Returns how much of the obective has been completed between 0.0f and 1.0f
	float percentCompleted() {
		
		if (Max != 0)
			return Cur/Max;
		else
			return (Completed?1.0f:0.0f);
	}
	
	// Returns the type of monster to kill
	int getType() {
		
		return Type;
	}
	
	// Update the objective and gives it the current level and how many left of objective's type
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
	
	// Returns the message to display when completing the objective
	String getMessage(){
		
		return Message;
	}
	
	// Returns the time to display the message
	long getMessageTimeMilli(){
		
		return MessageTimeMilli;
	}
	
	// Returns the x position of the message
	int getMessageX(){
		
		return MessageX;
	}
	
	// Returns the y position of the message
	int getMessageY(){
		
		return MessageY;
	}
	
	// Returns the points awarded upon completion of the objective
	int getPoints(){
		
		return Points;
	}
	
	// Returns the level to complete the objective on
	int getLevel(){
		
		return Level;
	}
}
