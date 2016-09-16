package hazardland.ferry.main;

import hazardland.ferry.R;
import hazardland.lib.game.Scene;

public class Win extends Window 
{
	public Win(Scene scene) 
	{
		super (scene, R.drawable.win);
	}
	
	public void click ()
	{
		hide ();
		scene.restart();
	}

}
