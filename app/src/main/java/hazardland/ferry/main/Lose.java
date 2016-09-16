package hazardland.ferry.main;

import hazardland.ferry.R;
import hazardland.lib.game.Scene;

public class Lose extends Window 
{
	public Lose(Scene scene) 
	{
		super (scene, R.drawable.lose);
	}
	
	public void click ()
	{
		hide ();
		scene.restart();
	}

}
