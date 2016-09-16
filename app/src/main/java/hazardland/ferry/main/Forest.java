package hazardland.ferry.main;

import hazardland.ferry.R;
import hazardland.lib.game.Entity;
import hazardland.lib.game.Scene;
import hazardland.lib.game.job.Music;

public class Forest extends Entity 
{

	public Forest(Scene scene) 
	{
		super(scene, 0, 328, R.drawable.tke);
		job (new Music(Music.PLAY, R.raw.chitebi, 1));		
	}

}
