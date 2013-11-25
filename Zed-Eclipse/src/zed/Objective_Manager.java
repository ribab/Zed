package zed;

public class Objective_Manager {
	
	Objective[] objectives;
	String[] messages;

	public Objective_Manager() {
		objectives = new Objective[2];
		
		objectives[0] = new Objective(0, "You have landed on an island inhabited by an evil dragon.\nYour task is to kill this dragon.",
				50, 430, -1);
		objectives[1] = new Objective(Zombie.Type, 1, 0, "MEGAKILL",
				300, 430, 10000);
	}
	
	public Objective Update(int level, GObject[] npclist){
		
		for (int i = 0; i < objectives.length; i++)
		{
			if (level == objectives[i].getLevel() && !objectives[i].isCompleted())
			{
				int objectivetype = objectives[i].getType();
				if (objectivetype >= 0)
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
				else
				{
					objectives[i].Update(level, 0);
				}
				if (objectives[i].isCompleted())
				{
					return objectives[i];
				}
			}
		}
		return null;
	}
	
	public float percentageCompleted(){
		
		int count = 0;
		for (int i = 0; i < objectives.length; i++)
		{
			if (objectives[i].isCompleted())
			{
				count++;
			}
		}
		return (count*1.0f)/(objectives.length*1.0f);
	}
}
