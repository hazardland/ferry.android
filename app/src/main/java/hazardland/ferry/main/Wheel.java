package hazardland.ferry.main;

import hazardland.ferry.R;
import hazardland.lib.game.Entity;
import hazardland.lib.game.Scene;
import hazardland.lib.game.job.Move;

public class Wheel extends Entity
{
	public final static int LEFT = 1;
	public final static int RIGHT = 2;
	public final static int STOP = 3;
	public Wheel (Scene scene, float x, float y) 
	{
		super(scene, x, y, R.drawable.borbali);
//		sprite("default").center.x = 19;
//		sprite("default").center.y = 20;
	}
	public void rotate (float speed)
	{
		if (speed>0)
		{
			System.out.println("speed right is "+speed);			
			job (new Move(Move.CORNER, 1, 360, 5*speed, 0, false, 0));
		}
		else if (speed<0)
		{
			System.out.println("speed left is "+speed);
			job (new Move(Move.CORNER, 1, 360, 5*speed, 0, false, 0));
		}		
		else
		{
			kill (Move.CORNER);
		}
	}
}
