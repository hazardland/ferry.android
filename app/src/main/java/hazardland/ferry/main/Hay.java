package hazardland.ferry.main;

import hazardland.ferry.Main;
import hazardland.ferry.R;
import hazardland.lib.game.Entity;
import hazardland.lib.game.Frame;
import hazardland.lib.game.Job;
import hazardland.lib.game.Point;
import hazardland.lib.game.Scene;
import hazardland.lib.game.Sprite;
import hazardland.lib.game.job.Image;
import hazardland.lib.game.job.Music;
import hazardland.lib.game.job.Sleep;

public class Hay extends Character 
{
	public final static Point EAT = new Point(-67, +79);
	//public Main scene;
	public Hay (Scene scene) 
	{
		super (scene, 109, 194, scene.width(R.drawable.tiva_dgas)/2, scene.height(R.drawable.tiva_dgas)/2);
		
		sprites.put ("tiva_gahelili", new Sprite(0, 0, size.width, size.height));
		sprite("tiva_gahelili")
		.add(scene, R.drawable.tiva_dgas, 0, 0, size.width, size.height)
		.add(scene, R.drawable.tiva_dgas, size.width, 0, size.width, size.height);

		sprites.put ("tiva_dahujuli", new Sprite(0, 0, size.width, size.height));
		sprite("tiva_dahujuli")
		.add(scene, R.drawable.tiva_dgas, size.width, size.height, size.width, size.height)
		.add(scene, R.drawable.tiva_dgas, 0, size.height, size.width, size.height);
		
		
		sprite ("tiva_dahujuli").stop();
		sprite ("tiva_dahujuli").skip = 20;
		sprite ("tiva_dahujuli").limit = 1;
		
		
		sprite("tiva_gahelili").last();
		sprite("tiva_gahelili").skip = 20;
		sprite("tiva_gahelili").reverse = false;

		shape.shape(20, 20, size.width-40, size.height-40);

		touch = true;
		
		position.x (this.scene.align.hay.x);
		position.y (this.scene.align.hay.y);		
	}
	
	@Override
	public void press ()
	{
		kill (Image.PLAY);
		kill (Job.SLEEP);
		sprite("tiva_dahujuli").first();
		sprite("tiva_dahujuli").stop();
	}	

	@Override
	public void click ()
	{
		job(new Sleep(1000)).follow(new Image(Image.LAST, "tiva_dahujuli")).follow(new Sleep(1000)).follow(new Image(Image.PLAY, "tiva_gahelili"));
	}	
}
