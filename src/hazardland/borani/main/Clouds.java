package hazardland.borani.main;

import hazardland.borani.R;
import hazardland.lib.game.Entity;
import hazardland.lib.game.Scene;
import hazardland.lib.game.job.Move;

public class Clouds extends Entity 
{
	public Clouds(Scene scene, int order) 
	{
		super(scene, 0, 490, R.drawable.grublebi);
		position.x (position.x()+size.width*order);
		job (new Move(Move.X, -size.width, size.width, -0.4f, 0, false, 0));
	}

}
