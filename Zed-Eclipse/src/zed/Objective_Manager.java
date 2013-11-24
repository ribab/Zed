package zed;

public class Objective_Manager {
	
	Objective[] objectives;
	String[] messages;

	public Objective_Manager() {
		objectives = new Objective[1];
		
		objectives[0] = new Objective(0);
	}
	
	public Objective Update(int level, GObject[] npclist){
		
		for (int i = 0; i < objectives.length; i++)
		{
			if (!objectives[i].isCompleted())
			{
				int type = objectives[i].getType();
				if (type >= 0)
				{
					int count = 0;
					for (int j = 0; j < npclist.length; j++)
					{
						if (GObject.Get_Type() == objectives[i].getType())
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
