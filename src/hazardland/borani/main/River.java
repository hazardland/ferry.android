package hazardland.borani.main;

import hazardland.lib.game.Entity;
import hazardland.lib.game.Scene;
import hazardland.lib.game.Sprite;
import hazardland.lib.game.job.Music;
import hazardland.borani.R;

public class River extends Entity 
{

	public River(Scene scene) 
	{
		super(scene, 65, 0, 814, 485);
		sprites.put("default", new Sprite(size.width, size.height));
		sprite().skip = 2;
		sprite().add(scene.image(R.drawable.mdinare_1)).add(scene.image(R.drawable.mdinare_2)).add(scene.image(R.drawable.mdinare_3)).add(scene.image(R.drawable.mdinare_4));
		job (new Music(Music.PLAY, R.raw.ckali, 1));
	}

}
