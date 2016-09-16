package hazardland.ferry.main;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import hazardland.ferry.Main;
import hazardland.ferry.R;
import hazardland.lib.game.Entity;
import hazardland.lib.game.Input;
import hazardland.lib.game.Job;
import hazardland.lib.game.Scale;
import hazardland.lib.game.Scene;
import hazardland.lib.game.Shape;
import hazardland.lib.game.job.Custom;
import hazardland.lib.game.job.Move;
import hazardland.lib.game.job.Music;

public class Boat extends Entity 
{
	public static final int RIGHT = 1;
	public static final int LEFT = 2;
	public static final int MIDDLE = 3;
	public static final int LIMIT = 50;
	public int edge = LEFT;
	public Main scene;
	public Entity passanger;
	private boolean moves = false;
	public Boat (Scene scene) 
	{
		super (scene, 310, 91, R.drawable.navi);
		touch = true;
		lock = new Shape(position.x(), position.y(), 300, 2);
		job (new Move(Move.CORNER, -0.5f, 0.5f, 0.04f, 0, true, 0));
		//shape.show ();
		shape.shape(10, 20, size.width-20, size.height-10);		
		this.scene = (Main) scene;
	}

	public boolean stop (Input input)
	{
		if (super.stop (input))
		{
			if (input.vector.x.speed!=0)
			{
				move (input.vector.x.speed*4f);
			}
			return true;
		}
		return false;
	}
	
	public void custom (String condition)
	{
		if (condition=="crash")
		{
			if (side()==LEFT)
			{
				crash (0.5f, lock.x(), lock.x()+3);
			}
			else
			{
				crash (-0.5f, lock.width()-3, lock.width());
			}
		}
	}
	
	public void wheel (float speed)
	{		
		scene.wheel1.rotate (speed);
		scene.wheel2.rotate (speed);		
	}

	public void crash (float speed, float from, float to)
	{
		kill (Music.PLAY);
		moves = false;
		scene.wolf.eat ();
		scene.sheep.eat ();
		job (new Wheels(speed)) 
		.follow(new Move (Move.X, from, to, speed, 1, false, 0))
		.follow(new Wheels(-speed/4))
		.follow(new Move(Move.X, from, to, -speed/4, 1, false, 0))
		.follow(new Wheels(0));
	}	
	
	public void move (float speed)
	{
		debug ("boat speed is "+Math.abs(speed));
		if (Math.abs(speed)>0.3)
		{
			sound ();
		}
		else
		{
			kill (Music.PLAY);
		}
		moves = true;
		job (new Move (Move.X, lock.x(), lock.width(), speed, 1, false, 0)).follow(new Custom("crash"));
		wheel (speed);
	}
	
	public boolean moves ()
	{
		return moves;
	}
	
	public int side ()
	{
		if (position.x()<=lock.x()+5)
		{
			debug ("boat left");
			return LEFT;
		}
		else if (position.x()>=lock.width()-5)
		{
			debug ("boat right");
			return RIGHT;
		}
		else
		{
			debug ("boat middle "+lock.x()+" "+position.x()+" "+(lock.width()));
			return MIDDLE;
		}
	}
	
	public void debug (String message)
	{
		System.out.println("character: "+message);
	}

	public float x (float from, float to)
	{
		if (passanger!=null)
		{
			passanger.position.x (passanger.position.x()+to-from);
		}
		return to;
	}

	public float corner (float from, float to)
	{
		if (passanger!=null)
		{
			passanger.position.corner (to);
		}
		return to;
	}
	
	public class Wheels extends Job
	{
		public static final int WHEEL = 11;
		public float speed;
		public Wheels(float speed) 
		{
			super (WHEEL);
			this.speed = speed;
		}
		@Override
		public boolean next ()
		{
			((Boat)entity).wheel(speed);
			return false;
		}
	}
	
	@Override
	public void draw (GL10 gl, Scale scale)
	{
		super.draw (gl, scale);
		//lock.draw(gl, scale);
	}
	
	public void sound ()
	{
		if (job(Music.PLAY)!=null)
		{
			kill(Music.PLAY);
		}
		job (new Music(Music.PLAY, R.raw.borani, 1));
	}
}
