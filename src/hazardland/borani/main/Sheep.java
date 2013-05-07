package hazardland.borani.main;

import hazardland.borani.R;
import hazardland.lib.game.Frame;
import hazardland.lib.game.Point;
import hazardland.lib.game.Scene;
import hazardland.lib.game.Sprite;
import hazardland.lib.game.job.Custom;
import hazardland.lib.game.job.Image;
import hazardland.lib.game.job.Move;
import hazardland.lib.game.job.Music;
import hazardland.lib.game.job.Sleep;

public class Sheep extends Character
{
	public final static Point EAT = new Point(-96, +57);
	//public Main scene;
	public Sheep(Scene scene) 
	{
		super (scene, 115, 129, scene.width(R.drawable.cxvari_dachera)/2, scene.height(R.drawable.cxvari_dachera)/2);

		sprites.put ("cxvari_dachera", new Sprite(0, 0, size.width, size.height, Frame.generate(scene, R.drawable.cxvari_dachera, 2, 2, 4, 0, 0, size.width, size.height)));
		sprites.put ("cxvari_garbis", new Sprite(0, 0, size.width, size.height, Frame.generate(scene, R.drawable.cxvari_garbis, 2, 2, 3, 0, 0, size.width, size.height)));
		sprites.put ("cxvari_dgas", new Sprite(0, 0, size.width, size.height, Frame.generate(scene, R.drawable.cxvari_dgas, 3, 2, 5, 0, 0, size.width, size.height)));
		sprites.put ("cxvari_chams", new Sprite(-8, -size.height, 193, 192, Frame.generate(scene, R.drawable.cxvari_chams, 4, 5, 18, 0, 0, 193, 192)));
		
		shape.shape(10, 10, size.width-20, size.height);		
		
		//sprite("cxvari_garbis").stop();
		sprite("cxvari_garbis").skip = 2;

		sprite ("cxvari_chams").stop();
		sprite ("cxvari_chams").limit = 1;
		sprite ("cxvari_chams").reverse = false;
		
		sprite("cxvari_dachera").stop();
		sprite("cxvari_dachera").reverse = false;
		sprite("cxvari_dachera").limit = 1;
		touch = true;
		
		
		sprite("cxvari_dgas").play();
		sprite("cxvari_dgas").skip = 1;
		sprite("cxvari_dgas").reverse = false;
		sprite("cxvari_dgas").skip (0,100);

		position.x (this.scene.align.sheep.x);
		position.y (this.scene.align.sheep.y);		
	}
	
	@Override
	public boolean drag (Point from, Point to, Point speed, Point slow)
	{
		if ((from.x!=to.x || from.y!=to.y) && !scene.boat.moves())
		{
			sprite ("cxvari_garbis");
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
				sprite("cxvari_dachera").frame = 1;
				job (new Music(Music.PLAY, R.raw.cxvari_dachera)).follow(new Image(Image.CHANGE, "cxvari_dgas"));
			}			
		}
		else
		{
			sprite ("cxvari_dgas");
		}
		return super.stop(from, to, speed, slow);
	}
	
	public void eat ()
	{
		boolean eat = false;
		if (scene.hay.visible())
		{
			if (scene.left.inside(this))
			{
				if (scene.left.inside(scene.hay) && !scene.left.inside(scene.wolf) && scene.boat.side()!=Boat.LEFT)
				{
					eat = true;
				}
			}
			else if (scene.right.inside(this))
			{
				if (scene.right.inside(scene.hay) && !scene.right.inside(scene.wolf) && scene.boat.side()!=Boat.RIGHT)
				{
					eat = true;
				}			
			}
		}
		if (eat)
		{
			scene.world.front (this);			
			sprite("cxvari_dachera").frame = 1;
			job (new Music(Music.PLAY, R.raw.cxvari_dachera)).follow(new Image(Image.FIRST, "cxvari_dachera")).follow(new Custom("jump"));
			
		}
	}

	private void swallow ()
	{
		scene.hay.hide();
		move (scene.hay.position.x()-30, position.y(), 30f);		
		sprite ("cxvari_chams").play();
		touch = false;
		job(new Music(Music.PLAY, R.raw.chams)).follow(new Move(Move.SIZE, 1f, 1.03f, 0.01f, 1, false, 0)).follow(new Sleep(3000)).follow(new Custom("restart"));;
	}

	public void custom (String condition)
	{
		super.custom (condition);		
		if (condition=="jump")
		{
			if (scene.hay.position.x()+Hay.EAT.x!=position.x() || scene.hay.position.y()+Hay.EAT.y!=position.y())
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
			Point up = new Point(scene.hay.position.x()+Hay.EAT.x, scene.hay.position.y()+Hay.EAT.y);
			move (up, 50f, new Custom("swallow"));
			sprite("cxvari_dachera").frame=0;
		}
		else if (condition=="swallow")
		{
			swallow ();
		}
		else if (condition=="restart")
		{
			scene.lose();
		}		
	}
}
