package hazardland.borani.main;

import hazardland.borani.Main;
import hazardland.lib.game.Entity;
import hazardland.lib.game.Scene;

public class Window extends Entity 
{
	Main scene;

	public Window (Scene scene, int image) 
	{
		super(scene, scene.screen.width/2-scene.width(image)/2, scene.screen.height/2-scene.height(image)/2, image);
		this.scene = (Main) scene;
		touch = true;
		hide ();
		shape.x = -position.x();
		shape.y = -position.y();
		shape.width = scene.screen.width;
		shape.height = scene.screen.height;
	}
}
