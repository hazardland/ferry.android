package hazardland.ferry.main;

import hazardland.ferry.Main;
import hazardland.ferry.R;
import hazardland.lib.game.Frame;
import hazardland.lib.game.Point;
import hazardland.lib.game.Scene;
import hazardland.lib.game.Sprite;
import hazardland.lib.game.job.Custom;
import hazardland.lib.game.job.Image;
import hazardland.lib.game.job.Move;
import hazardland.lib.game.job.Music;
import hazardland.lib.game.job.Sleep;

public class Wolf extends Character
{
	public Wolf(Scene scene) 
	{
		super (scene, 90, 20, scene.width(R.drawable.mgeli_dachera)/2, scene.height(R.drawable.mgeli_dachera));
		
		sprites.put ("mgeli_dgas", new Sprite(0, 0, size.width, size.height, Frame.generate(scene, R.drawable.mgeli_dgas, 2, 2, 4, 0, 0, size.width, size.height)));
		sprites.put ("mgeli_dachera", new Sprite(0, 0, size.width, size.height, Frame.generate(scene, R.drawable.mgeli_dachera, 1, 2, 2, 0, 0, size.width, size.height)));
		sprites.put ("mgeli_chams", new Sprite(-0.5f, -size.height+63.9f, 222, 224, Frame.generate(scene, R.drawable.mgeli_chams, 4, 4, 15, 0, 0, 222, 224)));
		sprites.put ("mgeli_garbis", new Sprite(0, 0, size.width, size.height, Frame.generate(scene, R.drawable.mgeli_garbis, 2, 2, 3, 0, 0, size.width, size.height)));		
		
		shape.shape(30, 10, size.width-80, size.height-40);		

		sprite("mgeli_dachera").stop();
		sprite("mgeli_dachera").frame = 1;
		
		//sprite("mgeli_garbis").frame = 1;
		sprite("mgeli_garbis").skip = 2;
		
		
		sprite ("mgeli_chams").stop();
		sprite ("mgeli_chams").limit = 1;
		sprite ("mgeli_chams").reverse = false;
		
		sprite("mgeli_dgas").play();
		sprite("mgeli_dgas").skip = 1;
		sprite("mgeli_dgas").reverse = false;
		
		touch = true;
		
		position.x (this.scene.align.wolf.x);
		position.y (this.scene.align.wolf.y);
		
		//this.scene.boat.passanger = this;
	}
	
	@Override
	public boolean drag (Point from, Point to, Point speed, Point slow)
	{
		if ((from.x!=to.x || from.y!=to.y) && !scene.boat.moves())
		{
			sprite ("mgeli_garbis");
		}
		return super.drag(from, to, speed, slow);
	}
	
	@Override
	public boolean stop (Point from, Point to, Point speed, Point slow)
	{
		if (from.x==to.x && from.y==to.y)
		{
			if (job(Music.PLAY)==null)
			{
				sprite("mgeli_dachera").frame = 1;
				job (new Music(Music.PLAY, R.raw.mgeli_dachera)).follow(new Image(Image.CHANGE, "mgeli_dgas"));
			}
		}
		else
		{
			sprite ("mgeli_dgas");
		}
		return super.stop(from, to, speed, slow);
	}	
	
	public void eat ()
	{
		boolean eat = false;
		if (scene.sheep.visible() && scene.hay.visible())
		{
			if (scene.left.inside(this))
			{
				if (scene.left.inside(scene.sheep) && !scene.left.inside(scene.hay) && scene.boat.side()!=Boat.LEFT)
				{
					eat = true;
				}
			}
			else if (scene.right.inside(this))
			{
				if (scene.right.inside(scene.sheep) && !scene.right.inside(scene.hay) && scene.boat.side()!=Boat.RIGHT)
				{
					eat = true;
				}			
			}
		}
		if (eat)
		{
			scene.world.front (this);				
			sprite("mgeli_dachera").frame = 1;
			job (new Music(Music.PLAY, R.raw.mgeli_dachera)).follow(new Image(Image.CHANGE, "mgeli_dgas")).follow (new Custom("jump"));
		}
	}

	private void swallow ()
	{
		scene.sheep.hide();
		move (scene.sheep.position.x()-30, position.y(), 15f);
		sprite ("mgeli_chams").play();
		touch = false;
		job(new Music(Music.PLAY, R.raw.chams)).follow(new Move(Move.SIZE, 1f, 1.03f, 0.01f, 1, false, 0)).follow(new Sleep(3000)).follow(new Custom("restart"));
	}

	public void custom (String condition)
	{
		super.custom (condition);
		if (condition=="jump")
		{
			if (scene.sheep.position.x()+Sheep.EAT.x!=position.x() || scene.sheep.position.y()+Sheep.EAT.y!=position.y())
			{
				custom ("ready");
			}
			else
			{
				swallow ();
			}
		}
		else if (condition=="ready")
		{
			Point up = new Point(scene.sheep.position.x()+Sheep.EAT.x, scene.sheep.position.y()+Sheep.EAT.y);
			move (up, 50f, new Custom("swallow"));
			sprite("mgeli_dachera").frame=0;
		}
		else if (condition=="swallow")
		{
			swallow ();
		}
		else if (condition=="restart")
		{
			scene.lose ();
		}
	}	
}
