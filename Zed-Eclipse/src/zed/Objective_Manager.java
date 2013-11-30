package zed;

public class Objective_Manager {
	
	Objective[] objectives; // Holds the objectives to complete
	String[] messages; // Holds the messages displayed
	
	int Point_Count; // Counts the points the player earned by completing objectives
	
	private long Time_Start; // holds the starting time for the game

	// Constructs the objectives
	public Objective_Manager() {
		objectives = new Objective[2];
		Point_Count = 0;
		
		objectives[0] = new Objective(0, "You have landed on an island inhabited by an evil dragon.\nYour task is to kill this dragon.",
				50, 430, -1, 1000, true);
		objectives[1] = new Objective(Zombie.Type, 1, 0, "MEGAKILL",
				300, 430, 10000, 100, false);
		
		Time_Start = System.currentTimeMillis();
	}
	
	// Updates the objectives
	public Objective Update(int level, GObject[] npclist){
		
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
					Point_Count++;
					return objectives[i];
				}
			}
		}
		return null;
	}
	
	// Returns the final score. This should be called once the game is over.
	public int getFinalScore(){
		
		long end_time = System.currentTimeMillis();
		
		if ((Point_Count - (int)(Time_Start - end_time)) < 0)
			return 0;
		return (Point_Count - (int)(Time_Start - end_time));
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
