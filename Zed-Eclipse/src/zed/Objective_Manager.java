package zed;

public class Objective_Manager {
	
	// (time to complete)/TIME_FUDGE is time's negative effect on score
	
	private static int TIME_FUDGE = 10;
	private static int BONUS_START = 100000;
	
	Objective[] objectives; // Holds the objectives to complete
	
	Objective complete_the_game;
	
	int Point_Count; // Counts the points the player earned by completing objectives
	int Final_Bonus = BONUS_START;
	
	private long Time_Start; // holds the starting time for the game

	// Constructs the objectives
	public Objective_Manager() {
		objectives = new Objective[12];
		Point_Count = 0;
		
		// COMPLETION OF THE GAME OBJECTIVE
		complete_the_game = new Objective(
				-1, "CONGRATULATIONS\nYou have completed the game\nFeel free to explore the world more",
				200, 200, -1, 0, true);
		
		// REQUIRED OBJECTIVES
		objectives[0] = new Objective(0, "You have landed on an island inhabited by an evil dragon.\nYour task is to kill this dragon.",
				50, 430, -1, 1000, true);
		objectives[1] = new Objective(1, "", 0, 0, -1, 1000, true);
		objectives[2] = new Objective(3, "", 0, 0, -1, 1000, true);
		objectives[3] = new Objective(4, "", 0, 0, -1, 1000, true);
		objectives[4] = new Objective(5, "", 0, 0, -1, 1000, true);
		objectives[5] = new Objective(6, "", 0, 0, -1, 1000, true);
		objectives[6] = new Objective(7, "", 0, 0, -1, 1000, true);
		objectives[7] = new Objective(12, "", 0, 0, -1, 1000, true);
		objectives[8] = new Objective(13, "You hear a dragon in the distance...",
				50, 430, 5000, 1000, true);
		objectives[9] = new Objective(14, "The dragon noise is getting louder...",
				50, 430, 5000, 1000, true);
		objectives[10] = new Objective(15, "You see the dragon.\nDefeat it!",
				200, 200, 5000, 1000, true);
		objectives[11] = new Objective(Dragon.Type, 1, 0, "You have defeated the dragon!",
				300, 430, 10000, 4000, true);
		
		// OPTIONAL OBJECTIVES
		// TODO: add optional objectives using same function for objectives[11]
		
		Time_Start = System.currentTimeMillis();
	}
	
	// Updates the objectives
	public Objective Update(int level, GObject[] npclist){
		
		if (percentageCompleted() == 1.0f)
		{
			Final_Bonus = getBonus();
			complete_the_game.Complete();
			return complete_the_game;
		}
		else
		{
			// For all objectives
			for (int i = 0; i < objectives.length; i++)
			{
				// Make sure objective is on current level and isn't completed
				if (level == objectives[i].getLevel() && !objectives[i].isCompleted())
				{
					int objectivetype = objectives[i].getType();
					if (objectivetype >= 0) // objective requires destroying objects
					{
						int count = 0;
						for (int j = 0; j < npclist.length; j++)
						{
							int npctype = npclist[j].Get_Type();
							if (npclist[j].Visible && npctype == objectives[i].getType())
								count++;
						}
						objectives[i].Update(level, count);
					}
					else // objective requires visiting a level
					{
						objectives[i].Update(level, 0);
					}
					if (objectives[i].isCompleted())
					{
						Point_Count += objectives[i].getPoints();
						return objectives[i];
					}
				}
			}
		}
		return null;
	}
	
	// Returns the score.
	// This should be called once the game is over to record the value
	// on the high-score list
	public int getScore(){
		
		return Point_Count + getBonus();
	}
	
	public int getPoints(){
		
		return Point_Count;
	}
	
	public int getBonus(){

		if (Final_Bonus != BONUS_START)
		{
			return Final_Bonus;
		}
		long end_time = System.currentTimeMillis();
		
		if ((BONUS_START - (int)(end_time - Time_Start)/TIME_FUDGE) < 0)
			return 0;
		return (BONUS_START - (int)(end_time - Time_Start)/TIME_FUDGE);
	}
	
	// Returns the percentage of necessary objectives that have been completed
	public float percentageCompleted(){
		
		int max_count = 0;
		int count = 0;
		for (int i = 0; i < objectives.length; i++)
		{
			if (objectives[i].isNeccesary())
			{
				max_count += objectives[i].getPoints();
				if (objectives[i].isCompleted())
					count += objectives[i].getPoints();
			}
		}
		return (count*1.0f)/(max_count*1.0f);
	}
}
