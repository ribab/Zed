package zed;

public class Objective_Manager {
	
	// (time to complete)/TIME_FUDGE is time's negative effect on score
	
	private static int TIME_FUDGE = 10;
	
	Objective[] objectives; // Holds the objectives to complete
	
	Objective complete_the_game;
	
	short Point_Count; // Counts the points the player earned by completing objectives
	
	private long Time_Start; // holds the starting time for the game

	// Constructs the objectives
	public Objective_Manager() {
		objectives = new Objective[2];
		Point_Count = 0;
		
		complete_the_game = new Objective(
				-1, "CONGRATULATIONS\nYou have completed the game\nFeel free to explore the world more",
				200, 200, -1, (short) 0, true);
		
		objectives[0] = new Objective(0, "You have landed on an island inhabited by an evil dragon.\nYour task is to kill this dragon.",
				50, 430, -1, (short) 1000, true);
		objectives[1] = new Objective(Zombie.Type, 1, 0, "MEGAKILL",
				300, 430, 10000, (short) 10000, true);
		
		Time_Start = System.currentTimeMillis();
	}
	
	// Updates the objectives
	public Objective Update(int level, GObject[] npclist){
		
		if (percentageCompleted() == 1.0f)
		{
			complete_the_game.Complete();
			return complete_the_game;
		}
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
		return null;
	}
	
	// Returns the score.
	// This should be called once the game is over to record the value
	// on the high-score list
	public int getScore(){
		
		long end_time = System.currentTimeMillis();
		
		if ((Point_Count - (int)(end_time - Time_Start)/TIME_FUDGE) < 0)
			return 0;
		return (Point_Count - (int)(end_time - Time_Start)/TIME_FUDGE);
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
