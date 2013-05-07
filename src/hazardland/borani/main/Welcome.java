package hazardland.borani.main;

import hazardland.borani.R;
import hazardland.lib.game.Scene;

public class Welcome extends Window 
{

	public Welcome(Scene scene) 
	{
		super (scene, R.drawable.welcome);
		show ();
	}
	
	public void click ()
	{
		hide ();
	}

}
