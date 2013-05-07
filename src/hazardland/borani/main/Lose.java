package hazardland.borani.main;

import hazardland.borani.R;
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
