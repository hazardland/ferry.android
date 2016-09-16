package hazardland.ferry.main;

import hazardland.lib.game.Entity;
import hazardland.lib.game.Job;
import hazardland.lib.game.Scene;
import hazardland.lib.game.job.Sleep;

public class Message extends Entity 
{

	public Message(Scene scene, int image) 
	{
		super (scene, 0, 0, image);
		hide ();
	}
	
	public void show (Entity target)
	{
		position.x(target.position.x()+target.size.width/2-size.width/2);
		position.y(target.position.y()+target.size.height+30);
		job (new Job(Job.SHOW)).follow(new Sleep(2000)).follow(new Job(Job.HIDE));
	}


}
