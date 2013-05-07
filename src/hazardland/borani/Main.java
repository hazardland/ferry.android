package hazardland.borani;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import hazardland.borani.main.Align;
import hazardland.borani.main.Background;
import hazardland.borani.main.Boat;
import hazardland.borani.main.Clouds;
import hazardland.borani.main.Forest;
import hazardland.borani.main.Hay;
import hazardland.borani.main.Lose;
import hazardland.borani.main.Message;
import hazardland.borani.main.River;
import hazardland.borani.main.Rope;
import hazardland.borani.main.Sheep;
import hazardland.borani.main.Welcome;
import hazardland.borani.main.Wheel;
import hazardland.borani.main.Win;
import hazardland.borani.main.Wolf;
import hazardland.lib.game.Point;
import hazardland.lib.game.Scene;
import hazardland.lib.game.Shape;
import hazardland.lib.game.job.Music;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

public class Main extends Scene
{
	public static Align[] aligns = new Align[]{
		new Align(new Point(90,20), new Point(115,129), new Point(109,194)),
		new Align(new Point(85,4), new Point(91,207), new Point(160,106)),
		new Align(new Point(55,182), new Point(185,94), new Point(102,37))
		};
	public Align align;
	public Wolf wolf;
	public Sheep sheep;
	public Hay hay;
	public Wheel wheel1;
	public Wheel wheel2;
	public Boat boat;
	public Shape left = new Shape(0, 0, 250, 350);
	public Shape right = new Shape(827, 0, 200, 400);
	public Shape loader;
	public Win win;
	public Lose lose;
	public static Random random = new Random();
	public Message boatFull;
	protected void onCreate (Bundle state)
	{
		config.sensor = false;
		config.width = 1024;
		config.height = 576;
		
		left.hide();
		right.hide();
		
		create (state);
		name = "main";
		finish  = true;
		loader = new Shape(0, 0, 56, 61);
		align = aligns[random.nextInt (aligns.length-1)];
	}
	
	@Override
	public void open (GL10 gl)
	{
		decode (R.drawable.loader);
		loader = new Shape(screen.width/2-56/2, screen.height/2-61/2, 56, 61);
	}
	
	public void load (GL10 gl)
	{
		loader.draw (gl, scale, image(R.drawable.loader));
		loader.corner -= 2;
	}
	
	public void load ()
	{
		music (R.raw.ckali);
		music (R.raw.chitebi);
		music (Music.VOLUME, R.raw.ckali, 0.5f); //0.5f
		music (Music.VOLUME, R.raw.chitebi, 2.1f); //2.1f
		
		music (R.raw.mgeli_dachera);
		music (R.raw.cxvari_dachera);
		
		music (R.raw.borani);
		
		music (R.raw.chams);
		
		decode (R.drawable.foni);
		decode (R.drawable.grublebi);
		decode (R.drawable.tke);
		decode (R.drawable.mdinare_1);
		decode (R.drawable.mdinare_2);
		decode (R.drawable.mdinare_3);
		decode (R.drawable.mdinare_4);
		decode (R.drawable.mgeli_dgas);
		decode (R.drawable.mgeli_dachera);
		decode (R.drawable.mgeli_garbis);
		decode (R.drawable.mgeli_chams);
		decode (R.drawable.cxvari_dgas);
		decode (R.drawable.cxvari_dachera);
		decode (R.drawable.cxvari_garbis);
		decode (R.drawable.cxvari_chams);
		decode (R.drawable.tiva_dgas);
		decode (R.drawable.joxebi);
		decode (R.drawable.borbali);
		decode (R.drawable.navi);
		decode (R.drawable.welcome);
		decode (R.drawable.win);
		decode (R.drawable.lose);
		decode (R.drawable.navi_savsea);
		
		hold ();
		
		new Background (this);
		new River (this);
		new Rope (this);
		wheel1 = new Wheel(this, 245, 259);
		wheel2 = new Wheel(this, 790, 259);
		boat = new Boat (this);
		new Clouds (this, 0);
		new Clouds (this, 1);
		new Forest (this);
		wolf = new Wolf(this);
		sheep = new Sheep(this);
		hay = new Hay(this);
		new Welcome (this);
		win = new Win (this);
		lose = new Lose (this);
		boatFull = new Message(this, R.drawable.navi_savsea);

		world.start ();

	}
	
	public void draw (GL10 gl)
	{
		super.draw (gl);
		left.draw(gl, scale);
		right.draw(gl, scale);
//		square.draw (gl, 0, scale, left.x(), left.y(), left.width(), left.height(), 0, 1, 1, 1, 0.2f);
//		square.draw (gl, 0, scale, right.x(), right.y(), right.width(), right.height(), 0, 1, 1, 1, 0.2f);
	}
	
	public void restart ()
	{
		swap ("hazardland.borani.Main");
	}
	
	public void lose ()
	{
		lose.show();		
	}
	
	public void win ()
	{
		if (boat.side()==Boat.RIGHT && right.inside(wolf) && right.inside(hay) && right.inside(sheep))
		{
			win.show();
		}		
	}
	
	public void vibrate ()
	{
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(100);		
	}
}