package hazardland.borani.main;

import hazardland.borani.Main;
import hazardland.lib.game.Entity;
import hazardland.lib.game.Point;
import hazardland.lib.game.Scene;
import hazardland.lib.game.job.Custom;
import hazardland.lib.game.job.Move;

public class Character extends Entity {

	public Main scene;

	public Character(Scene scene, float x, float y, float width, float height) 
	{
		super(scene, x, y, width, height);
		this.scene = (Main) scene;		
		//shape.show();
	}


	@Override
	public boolean drag(Point from, Point to, Point speed, Point slow)
	{
		scene.world.front (this);
		if (scene.boat.passanger==this && scene.boat.moves())
		{
			stop ();
			return false;
		}
		else
		{
			position ((int)to.x, (int)to.y);
			return true;
		}
	}

	@Override
	public boolean stop(Point from, Point to, Point speed, Point slow) 
	{
		from.x = (int) from.x;
		from.y = (int) from.y;
		to.x = (int) to.x;
		to.y = (int) to.y;
		if (scene.boat.moves() || (scene.boat.passanger!=null && sit()))
		{
			debug ("boat not avalible");
			if (scene.boat.passanger==this)
			{
				
			}
			else
			{
				scene.vibrate ();
				scene.boatFull.show (this);				
				move (from, 30f);
			}
		}
		else if (scene.boat.passanger==this)
		{
			if (scene.boat.side()==Boat.LEFT && scene.left.inside(this))
			{
				debug ("removing from boat to left");
				scene.boat.passanger = null;
				position (to.x, to.y, scene.left);
			}
			else if (scene.boat.side()==Boat.RIGHT && scene.right.inside(this))
			{
				debug ("removing from boat to right");
				scene.boat.passanger = null;
				position (to.x, to.y, scene.right);
				scene.win ();
			}
			else
			{
				debug ("wrong land placement from boat");
				scene.vibrate();
				move (from, 30f);
			}
		}
		else if (!scene.boat.moves() && sit() && ((scene.boat.side()==Boat.LEFT && scene.left.inside(this,from) || (scene.boat.side()==Boat.RIGHT && scene.right.inside(this,from)))))
		{
			debug ("becaming boat passanger");
			scene.boat.passanger = this;
		}
		else
		{
			if (scene.left.inside(this,from))
		    {
				if (sit() || scene.right.inside(this))
				{
					move (from, 30f, new Custom("lock.left"));
				}
				else
				{
					debug ("position: landing left from left");
					position (to.x, to.y, scene.left);
				}
		    }
			else if (scene.right.inside(this,from))
			{
				if (sit() || scene.left.inside(this))
				{
					move (from, 30f, new Custom("lock.right"));
				}
				else
				{
					debug ("position: landing right from right");
					position (to.x, to.y, scene.right);
				}
			}
			else
			{
				scene.vibrate();
				debug ("going back");
				if (from.y<scene.screen.width/2)
				{
					move (from, 30f, new Custom("lock.left"));
				}
				else
				{
					move (from, 30f, new Custom("lock.right"));
				}
			}
		}
		return true;
	}

	public boolean sit() 
	{
		if (scene.boat.inside(this))
		{
			debug ("yes sit");
			return true;
		}
		debug ("no sit");
		return false;
	}

	public void debug(String message) 
	{
		System.out.println("character: "+message);
	}
	
	public void custom (String condition)
	{
		if (condition=="lock.left")
		{
			//position (position.x(),position.y(), scene.left);
		}
		else if (condition=="lock.right")
		{
			//position (position.x(),position.y(), scene.right);
		}
	}
	
}